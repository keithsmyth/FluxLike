package com.keithsmyth.fluxlike.features.header;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.fluxlike.R;
import com.keithsmyth.fluxlike.arch.recycler.AdapterDelegate;
import com.keithsmyth.fluxlike.arch.recycler.DiffUtilItemComparator;

class TitleAdapterDelegate implements AdapterDelegate<TitleAdapterDelegate.TitleViewHolder, String> {

    static final int LAYOUT = R.layout.adapter_view_header_title;

    @Override
    public int viewType() {
        return LAYOUT;
    }

    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new TitleViewHolder(inflater.inflate(LAYOUT, parent, false));
    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, String title) {
        holder.titleText.setText(title);
    }

    @Override
    public DiffUtilItemComparator<String> getDiffUtilItemComparator() {
        return new DiffUtilItemComparator<String>() {
            @Override
            public boolean areItemsTheSame(String oldModel, String newModel) {
                return true;  // only 1 of this type
            }

            @Override
            public boolean areContentsTheSame(String oldModel, String newModel) {
                return oldModel.equals(newModel);
            }
        };
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {

        final TextView titleText;

        TitleViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.title_text);
        }
    }
}
