package org.ulpgc.model.utils;

import org.ulpgc.dacd.model.Product;
import java.util.List;

public class Outfit {

    private List<Product> items;

    public Outfit(List<Product> items) {
        this.items = items;
    }

    public double totalPrice() {
        return items.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public List<Product> getItems() {
        return items;
    }
}
