package com.keithsmyth.fluxlike.arch.recycler;

public interface DiffUtilItemComparator<MODEL> {

    boolean areItemsTheSame(MODEL oldModel, MODEL newModel);

    boolean areContentsTheSame(MODEL oldModel, MODEL newModel);
}
