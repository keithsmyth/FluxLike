package com.keithsmyth.fluxlike.data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Product {
    public final String imageUrl;
    public final String name;
    public final List<Option> options;

    Product(String imageUrl, String name, List<Option> options) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.options = unmodifiableList(new ArrayList<>(options));
    }

    public static class Option {
        public final String id;
        public final String name;
        public final int price;

        Option(String id, String name, int price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
}
