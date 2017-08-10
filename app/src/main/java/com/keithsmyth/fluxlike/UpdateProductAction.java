package com.keithsmyth.fluxlike;

import com.keithsmyth.fluxlike.arch.flux.Action;
import com.keithsmyth.fluxlike.data.Product;

public class UpdateProductAction implements Action {

    public static final String ID = UpdateProductAction.class.getName();

    public final Product product;

    public UpdateProductAction(Product product) {
        this.product = product;
    }

    @Override
    public String actionId() {
        return ID;
    }
}
