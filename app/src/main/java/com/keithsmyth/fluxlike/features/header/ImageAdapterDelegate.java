package com.keithsmyth.fluxlike.features.header;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.keithsmyth.fluxlike.R;
import com.keithsmyth.fluxlike.arch.recycler.AdapterDelegate;
import com.keithsmyth.fluxlike.arch.recycler.DiffUtilItemComparator;

class ImageAdapterDelegate implements AdapterDelegate<ImageAdapterDelegate.ImageViewHolder, String> {

    static final int LAYOUT = R.layout.adapter_view_header_image;

    @Override
    public int viewType() {
        return LAYOUT;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new ImageViewHolder(inflater.inflate(LAYOUT, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, String url) {
        Glide.with(holder.itemView.getContext())
            .load(url)
            .into(holder.headerImageView);
    }

    @Override
    public DiffUtilItemComparator<String> getDiffUtilItemComparator() {
        return new DiffUtilItemComparator<String>() {
            @Override
            public boolean areItemsTheSame(String oldModel, String newModel) {
                return true; // only 1 of this type
            }

            @Override
            public boolean areContentsTheSame(String oldModel, String newModel) {
                return oldModel.equals(newModel);
            }
        };
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        final ImageView headerImageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            headerImageView = (ImageView) itemView.findViewById(R.id.header_image);
        }
    }
}
