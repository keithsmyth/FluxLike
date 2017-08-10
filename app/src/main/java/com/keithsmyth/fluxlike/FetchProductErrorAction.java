package com.keithsmyth.fluxlike;

import com.keithsmyth.fluxlike.arch.flux.Action;

public class FetchProductErrorAction implements Action {

    public static final String ID = FetchProductErrorAction.class.getName();

    public final Throwable throwable;

    public FetchProductErrorAction(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String actionId() {
        return ID;
    }
}
