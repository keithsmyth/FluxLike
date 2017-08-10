package com.keithsmyth.fluxlike.features.header;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.keithsmyth.fluxlike.UpdateProductAction;
import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.arch.flux.Store;
import com.keithsmyth.fluxlike.arch.recycler.AdapterItem;
import com.keithsmyth.fluxlike.features.options.actions.OptionSelectedAction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class HeaderStore extends ViewModel implements Store<LiveData<List<AdapterItem>>> {

    private String productName;
    private String productImageUrl;
    private String selectedOptionName;

    private final MutableLiveData<List<AdapterItem>> state = new MutableLiveData<>();

    HeaderStore() {
        state.setValue(Collections.emptyList());
    }

    @Override
    public LiveData<List<AdapterItem>> getState() {
        return state;
    }

    void registerDispatcher(Dispatcher dispatcher) {
        dispatcher.register(UpdateProductAction.ID, this::onProductUpdated);
        dispatcher.register(OptionSelectedAction.ID, this::onOptionSelected);
    }

    private void onProductUpdated(UpdateProductAction action) {
        productName = action.product.name;
        productImageUrl = action.product.imageUrl;
        buildItems();
    }

    private void onOptionSelected(OptionSelectedAction action) {
        selectedOptionName = action.option != null ? action.option.name : null;
        buildItems();
    }

    private void buildItems() {
        state.postValue(Arrays.asList(
            new AdapterItem<>(productImageUrl, ImageAdapterDelegate.LAYOUT),
            new AdapterItem<>(String.format("%1$s %2$s", productName, selectedOptionName != null ? selectedOptionName : ""), TitleAdapterDelegate.LAYOUT)
        ));
    }
}
