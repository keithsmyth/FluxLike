package com.keithsmyth.fluxlike.features.header;

import android.arch.lifecycle.ViewModelProvider;

import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.arch.recycler.AdapterDelegate;
import com.keithsmyth.fluxlike.arch.recycler.FeatureController;

import java.util.Arrays;
import java.util.List;

public class HeaderController implements FeatureController {

    private final ImageAdapterDelegate imageAdapterDelegate = new ImageAdapterDelegate();
    private final TitleAdapterDelegate titleAdapterDelegate = new TitleAdapterDelegate();
    private final HeaderStore store;

    public HeaderController(ViewModelProvider viewModelProvider, Dispatcher dispatcher) {
        store = viewModelProvider.get(HeaderStore.class);
        store.registerDispatcher(dispatcher);
    }

    @Override
    public List<AdapterDelegate> getAdapterDelegates() {
        return Arrays.asList(
            imageAdapterDelegate,
            titleAdapterDelegate
        );
    }

    @Override
    public HeaderStore getStore() {
        return store;
    }
}
