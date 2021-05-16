package de.tuberlin.tkn.lit.model;

public class OrderedCollection extends LitCollection {

    private LitObject[] orderedItems;

    public OrderedCollection(LitObject[] orderedItems, int totalItems, LitObject[] first, LitObject[] last, LitObject[] current) {
        super(totalItems, first, last, current);
        this.orderedItems=orderedItems;
    }
}
