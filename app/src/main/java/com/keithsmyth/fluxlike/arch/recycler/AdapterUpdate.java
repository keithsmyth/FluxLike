package com.keithsmyth.fluxlike.arch.recycler;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterUpdate {
    public final List<AdapterItem> allItems;
    public final DiffUtil.DiffResult diffResult;
    public final int offset;

    public AdapterUpdate(List<AdapterItem> allItems, int offset, DiffUtil.DiffResult diffResult) {
        this.allItems = Collections.unmodifiableList(new ArrayList<>(allItems));
        this.diffResult = diffResult;
        this.offset = offset;
    }
}
