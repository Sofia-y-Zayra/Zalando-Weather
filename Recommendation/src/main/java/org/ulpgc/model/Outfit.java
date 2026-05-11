package org.ulpgc.model;


public class Outfit {

    private final Product top;
    private final Product pants;
    private final Product jacket;

    public Outfit(Product top, Product pants, Product jacket) {
        this.top = top;
        this.pants = pants;
        this.jacket = jacket;
    }

    public Product getTop() {
        return top;
    }

    public Product getPants() {
        return pants;
    }

    public Product getJacket() {
        return jacket;
    }

    public double totalPrice() {

        double total = 0;

        if (top != null) total += top.getPrice();
        if (pants != null) total += pants.getPrice();
        if (jacket != null) total += jacket.getPrice();

        return total;
    }
}
