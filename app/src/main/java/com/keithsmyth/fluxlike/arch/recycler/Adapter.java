package com.keithsmyth.fluxlike.arch.recycler;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final SparseArray<AdapterDelegate> adapterDelegates = new SparseArray<>();
    private final List<AdapterItem> adapterItems = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegates.get(viewType).onCreateViewHolder(parent, LayoutInflater.from(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AdapterItem adapterItem = adapterItems.get(position);
        AdapterDelegate adapterDelegate = adapterDelegates.get(adapterItem.viewType);
        // noinspection unchecked
        adapterDelegate.onBindViewHolder(holder, adapterItem.model);
    }

    @Override
    public int getItemCount() {
        return adapterItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return adapterItems.get(position).viewType;
    }

    public void setAdapterDelegates(List<AdapterDelegate> adapterDelegates) {
        this.adapterItems.clear();
        this.adapterDelegates.clear();
        registerAdapterDelegates(adapterDelegates);
        notifyDataSetChanged();
    }

    public void setAdapterItems(List<AdapterItem> adapterItems) {
        this.adapterItems.clear();
        this.adapterItems.addAll(adapterItems);
        notifyDataSetChanged();
    }

    public void processAdapterUpdate(AdapterUpdate adapterUpdate) {
        this.adapterItems.clear();
        this.adapterItems.addAll(adapterUpdate.allItems);
        adapterUpdate.diffResult.dispatchUpdatesTo(new OffsetListUpdateCallback(adapterUpdate.offset));
    }

    private void registerAdapterDelegates(List<AdapterDelegate> adapterDelegates) {
        for (AdapterDelegate adapterDelegate : adapterDelegates) {
            this.adapterDelegates.put(adapterDelegate.viewType(), adapterDelegate);
        }
    }

    private class OffsetListUpdateCallback implements ListUpdateCallback {

        private final int offset;

        OffsetListUpdateCallback(int offset) {
            this.offset = offset;
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position + offset, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position + offset, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition + offset, toPosition + offset);
        }

        @Override
        public void onChanged(int position, int count, Object payload) {
            notifyItemRangeChanged(position + offset, count);
        }
    }
}
