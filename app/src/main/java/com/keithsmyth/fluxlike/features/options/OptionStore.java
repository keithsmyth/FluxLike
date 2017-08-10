package com.keithsmyth.fluxlike.features.options;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.keithsmyth.fluxlike.UpdateProductAction;
import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.arch.flux.Store;
import com.keithsmyth.fluxlike.arch.recycler.AdapterItem;
import com.keithsmyth.fluxlike.data.Product;
import com.keithsmyth.fluxlike.features.options.actions.OptionSelectedAction;
import com.keithsmyth.fluxlike.features.options.actions.OptionTappedAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OptionStore extends ViewModel implements Store<LiveData<List<AdapterItem>>> {

    private static final String NO_SELECTION = "";

    private Dispatcher dispatcher;

    private String selectedOptionId = NO_SELECTION;
    private List<Product.Option> options = Collections.emptyList();

    private final Map<String, Product.Option> optionMap = new HashMap<>();
    private final MutableLiveData<List<AdapterItem>> state = new MutableLiveData<>();

    OptionStore() {
        state.setValue(Collections.emptyList());
    }

    @Override
    public LiveData<List<AdapterItem>> getState() {
        return state;
    }

    void registerDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        dispatcher.register(UpdateProductAction.ID, this::onProductUpdated);
        dispatcher.register(OptionTappedAction.ID, this::onOptionTapped);
    }

    private void onProductUpdated(UpdateProductAction action) {
        options = action.product.options;

        optionMap.clear();
        for (Product.Option option : options) {
            optionMap.put(option.id, option);
        }

        buildItems();
    }

    private void onOptionTapped(OptionTappedAction action) {
        selectedOptionId = action.optionId.equals(selectedOptionId) ? NO_SELECTION : action.optionId;
        dispatcher.dispatch(new OptionSelectedAction(optionMap.get(selectedOptionId)));
        buildItems();
    }

    private void buildItems() {
        List<AdapterItem> items = new ArrayList<>(options.size());
        for (Product.Option option : options) {
            OptionViewModel optionViewModel = new OptionViewModel(option.id,
                option.name,
                option.price / 100,
                option.id.equals(selectedOptionId));
            items.add(new AdapterItem<>(optionViewModel, OptionAdapterDelegate.LAYOUT));
        }
        state.postValue(items);
    }
}
