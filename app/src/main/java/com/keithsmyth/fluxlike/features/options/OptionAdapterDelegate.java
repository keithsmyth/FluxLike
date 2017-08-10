package com.keithsmyth.fluxlike.features.options;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keithsmyth.fluxlike.R;
import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.arch.recycler.AdapterDelegate;
import com.keithsmyth.fluxlike.arch.recycler.DiffUtilItemComparator;
import com.keithsmyth.fluxlike.features.options.actions.OptionTappedAction;

class OptionAdapterDelegate implements AdapterDelegate<OptionAdapterDelegate.OptionViewHolder, OptionViewModel> {

    static final int LAYOUT = R.layout.adapter_view_option;

    Dispatcher dispatcher;

    @Override
    public int viewType() {
        return LAYOUT;
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new OptionViewHolder(inflater.inflate(LAYOUT, parent, false));
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, OptionViewModel optionViewModel) {
        holder.optionText.setText(holder.context.getString(R.string.option_text, optionViewModel.name, optionViewModel.price));
        holder.optionText.setSelected(optionViewModel.isSelected);
        holder.itemView.setOnClickListener(v -> dispatcher.dispatch(new OptionTappedAction(optionViewModel.id)));
    }

    @Override
    public DiffUtilItemComparator<OptionViewModel> getDiffUtilItemComparator() {
        return new DiffUtilItemComparator<OptionViewModel>() {
            @Override
            public boolean areItemsTheSame(OptionViewModel oldModel, OptionViewModel newModel) {
                return oldModel.id.equals(newModel.id);
            }

            @Override
            public boolean areContentsTheSame(OptionViewModel oldModel, OptionViewModel newModel) {
                return oldModel.equals(newModel);
            }
        };
    }

    static class OptionViewHolder extends RecyclerView.ViewHolder {

        final TextView optionText;
        final Context context;

        OptionViewHolder(View itemView) {
            super(itemView);
            optionText = (TextView) itemView.findViewById(R.id.option_text);
            context = itemView.getContext();
        }
    }
}
