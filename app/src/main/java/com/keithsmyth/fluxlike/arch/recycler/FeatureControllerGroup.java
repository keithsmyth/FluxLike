package com.keithsmyth.fluxlike.arch.recycler;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.single;

public class FeatureControllerGroup {

    private final Map<FeatureController, List<AdapterItem>> featuresCache = new LinkedHashMap<>();
    private final SparseArray<DiffUtilItemComparator> itemComparators = new SparseArray<>();
    private final Adapter adapter;

    public FeatureControllerGroup(Adapter adapter, LifecycleOwner owner, List<FeatureController> features) {
        this.adapter = adapter;
        initFeaturesCache(features);
        registerAdapterDelegates(features, adapter);
        observeFeatureStores(features, owner);
        populateAdapterItems();
    }

    private void initFeaturesCache(List<FeatureController> features) {
        for (FeatureController feature : features) {
            this.featuresCache.put(feature, Collections.emptyList());
        }
    }

    private void registerAdapterDelegates(List<FeatureController> features, Adapter adapter) {
        // collect all delegates
        List<AdapterDelegate> adapterDelegates = new ArrayList<>();
        for (FeatureController feature : features) {
            // noinspection unchecked
            adapterDelegates.addAll(feature.getAdapterDelegates());
        }

        // register with adapter
        adapter.setAdapterDelegates(adapterDelegates);

        // index comparators
        for (AdapterDelegate adapterDelegate : adapterDelegates) {
            itemComparators.put(adapterDelegate.viewType(), adapterDelegate.getDiffUtilItemComparator());
        }
    }

    private void observeFeatureStores(List<FeatureController> features, LifecycleOwner owner) {
        for (FeatureController feature : features) {
            // noinspection unchecked
            feature.getStore().getState().observe(owner, newItems -> dispatchActionItemsToAdapter(feature, newItems));
        }
    }

    private void populateAdapterItems() {
        // initial populate of adapter (in case of rotation)
        List<AdapterItem> allItems = new ArrayList<>();
        for (Map.Entry<FeatureController, List<AdapterItem>> entry : featuresCache.entrySet()) {
            allItems.addAll(entry.getValue());
        }
        adapter.setAdapterItems(allItems);
    }

    private void dispatchActionItemsToAdapter(FeatureController feature, List<AdapterItem> newItems) {
        // update cached features items
        final List<AdapterItem> oldItems = featuresCache.get(feature);
        featuresCache.put(feature, newItems);

        // build new items snapshot, calculate offset for adapter notifications
        final List<AdapterItem> allItems = new ArrayList<>();
        int tempOffset = 0;
        for (Map.Entry<FeatureController, List<AdapterItem>> entry : featuresCache.entrySet()) {
            if (entry.getKey() == feature) {
                tempOffset = allItems.size();
            }
            allItems.addAll(entry.getValue());
        }
        final int offset = tempOffset;

        // calculate diff on single background thread in sequence
        Single.fromCallable(() -> DiffUtil.calculateDiff(new DiffUtilCallbackImpl(oldItems, newItems, itemComparators)))
            .map(diffResult -> new AdapterUpdate(allItems, offset, diffResult))
            .subscribeOn(single())
            .observeOn(mainThread())
            .subscribe(adapter::processAdapterUpdate, this::logError);
    }

    private void logError(Throwable t) {
        Log.e(FeatureControllerGroup.class.getSimpleName(), t.getLocalizedMessage(), t);
    }

    private static class DiffUtilCallbackImpl extends DiffUtil.Callback {

        private final List<AdapterItem> oldItems;
        private final List<AdapterItem> newItems;
        private final SparseArray<DiffUtilItemComparator> itemComparators;

        DiffUtilCallbackImpl(List<AdapterItem> oldItems,
                             List<AdapterItem> newItems,
                             SparseArray<DiffUtilItemComparator> itemComparators) {
            this.oldItems = oldItems;
            this.newItems = newItems;
            this.itemComparators = itemComparators;
        }

        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            AdapterItem oldItem = oldItems.get(oldItemPosition);
            AdapterItem newItem = newItems.get(newItemPosition);
            if (oldItem.viewType != newItem.viewType) return false;

            DiffUtilItemComparator itemComparator = itemComparators.get(oldItem.viewType);
            // noinspection unchecked
            return itemComparator.areItemsTheSame(oldItem.model, newItem.model);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            AdapterItem oldItem = oldItems.get(oldItemPosition);
            AdapterItem newItem = newItems.get(newItemPosition);
            if (oldItem.viewType != newItem.viewType) return false;

            DiffUtilItemComparator itemComparator = itemComparators.get(oldItem.viewType);
            // noinspection unchecked
            return itemComparator.areContentsTheSame(oldItem.model, newItem.model);
        }
    }
}
