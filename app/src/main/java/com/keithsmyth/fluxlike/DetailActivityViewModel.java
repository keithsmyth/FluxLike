package com.keithsmyth.fluxlike;

import android.arch.lifecycle.ViewModel;

import com.keithsmyth.fluxlike.arch.flux.Action;
import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.data.DataProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

class DetailActivityViewModel extends ViewModel {

    private Disposable getProductDisposable;

    void init(Dispatcher dispatcher) {
        if (getProductDisposable != null) return;

        getProductDisposable = DataProvider.getProduct()
            .observeOn(AndroidSchedulers.mainThread())
            .map(UpdateProductAction::new)
            .cast(Action.class)
            .onErrorReturn(FetchProductErrorAction::new)
            .subscribe(dispatcher::dispatch);
    }

    @Override
    protected void onCleared() {
        if (getProductDisposable != null) getProductDisposable.dispose();
    }
}
