package com.keithsmyth.fluxlike.features.options.actions;

import com.keithsmyth.fluxlike.arch.flux.Action;

public class OptionTappedAction implements Action {

    public static final String ID = OptionTappedAction.class.getName();

    public final String optionId;

    public OptionTappedAction(String optionId) {
        this.optionId = optionId;
    }

    @Override
    public String actionId() {
        return ID;
    }
}
