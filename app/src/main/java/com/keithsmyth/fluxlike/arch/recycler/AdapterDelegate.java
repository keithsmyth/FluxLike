package com.keithsmyth.fluxlike.arch.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface AdapterDelegate<HOLDER extends RecyclerView.ViewHolder, MODEL> {

    int viewType();

    HOLDER onCreateViewHolder(ViewGroup parent, LayoutInflater inflater);

    void onBindViewHolder(HOLDER holder, MODEL model);

    DiffUtilItemComparator<MODEL> getDiffUtilItemComparator();
}
