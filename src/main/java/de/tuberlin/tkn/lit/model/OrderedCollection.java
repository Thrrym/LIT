package de.tuberlin.tkn.lit.model;

public class OrderedCollection extends main.java.de.tuberlin.tkn.lit.model.Collection {

    private String[] orderedItems;

    public OrderedCollection(String[] orderedItems, int totalItems, String[] first, String[] last, String[] current) {
        super(totalItems, first, last, current);
        this.orderedItems=orderedItems;
    }
}
