package com.keithsmyth.fluxlike;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.keithsmyth.fluxlike.arch.flux.Action;
import com.keithsmyth.fluxlike.data.DataProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

class DetailActivityViewModel extends ViewModel {

    private MutableLiveData<Action> _actions = new MutableLiveData<>();

    private Disposable productDisposable = DataProvider.getProduct()
            .observeOn(AndroidSchedulers.mainThread())
            .map(UpdateProductAction::new)
            .cast(Action.class)
            .onErrorReturn(FetchProductErrorAction::new)
            .subscribe(_actions::setValue);

    final LiveData<Action> actions = _actions;

    @Override
    protected void onCleared() {
        productDisposable.dispose();
    }
}
