package com.keithsmyth.fluxlike.arch.recycler;

public class AdapterItem<MODEL> {
    public final MODEL model;
    public final int viewType;

    public AdapterItem(MODEL model, int viewType) {
        this.model = model;
        this.viewType = viewType;
    }
}
