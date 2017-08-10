package com.keithsmyth.fluxlike.arch.recycler;

import android.arch.lifecycle.LiveData;

import com.keithsmyth.fluxlike.arch.flux.Store;

import java.util.List;

public interface FeatureController {

    List<AdapterDelegate> getAdapterDelegates();

    Store<LiveData<List<AdapterItem>>> getStore();

}
