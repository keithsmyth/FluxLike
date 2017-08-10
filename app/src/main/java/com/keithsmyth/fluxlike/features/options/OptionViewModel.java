package com.keithsmyth.fluxlike.features.options;

class OptionViewModel {
    final String id;
    final String name;
    final double price;
    final boolean isSelected;

    OptionViewModel(String id, String name, double price, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionViewModel that = (OptionViewModel) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (isSelected != that.isSelected) return false;
        if (!id.equals(that.id)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }
}
