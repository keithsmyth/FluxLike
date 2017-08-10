package com.keithsmyth.fluxlike.arch.flux;

public interface ActionHandler<ACTION extends Action> {

    void handleAction(ACTION action);

}
