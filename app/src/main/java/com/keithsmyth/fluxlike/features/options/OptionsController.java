package com.keithsmyth.fluxlike.features.options;

import android.arch.lifecycle.ViewModelProvider;

import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.arch.recycler.AdapterDelegate;
import com.keithsmyth.fluxlike.arch.recycler.FeatureController;

import java.util.Collections;
import java.util.List;

public class OptionsController implements FeatureController {

    private final OptionAdapterDelegate optionAdapterDelegate = new OptionAdapterDelegate();
    private final OptionStore store;

    public OptionsController(ViewModelProvider viewModelProvider, Dispatcher dispatcher) {
        this.store = viewModelProvider.get(OptionStore.class);
        store.registerDispatcher(dispatcher);
        optionAdapterDelegate.dispatcher = dispatcher;
    }

    @Override
    public List<AdapterDelegate> getAdapterDelegates() {
        return Collections.singletonList(optionAdapterDelegate);
    }

    @Override
    public OptionStore getStore() {
        return store;
    }
}
