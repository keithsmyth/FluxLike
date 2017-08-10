package com.keithsmyth.fluxlike.features.options.actions;

import android.support.annotation.Nullable;

import com.keithsmyth.fluxlike.arch.flux.Action;
import com.keithsmyth.fluxlike.data.Product;

public class OptionSelectedAction implements Action {

    public static final String ID = OptionSelectedAction.class.getName();

    @Nullable public final Product.Option option;

    public OptionSelectedAction(@Nullable Product.Option option) {
        this.option = option;
    }

    @Override
    public String actionId() {
        return ID;
    }
}
