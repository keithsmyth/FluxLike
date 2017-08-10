package com.keithsmyth.fluxlike.data;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class DataProvider {

    private DataProvider() {
    }

    public static Single<Product> getProduct() {
        return Single.fromCallable(generateProductCallable())
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io());
    }

    private static Callable<Product> generateProductCallable() {
        return () -> new Product("http://via.placeholder.com/350x150",
            "Amazing Product!!",
            Arrays.asList(
                new Product.Option("option0", "Option 0", 999),
                new Product.Option("option1", "Option 1", 899),
                new Product.Option("option2", "Option 2", 1115),
                new Product.Option("option3", "Option 3", 2999)
            ));
    }
}
