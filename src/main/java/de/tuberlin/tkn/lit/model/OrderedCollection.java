package de.tuberlin.tkn.lit.model;

import java.util.List;

public class OrderedCollection extends LitCollection {

    private List<LinkOrObject> orderedItems;

    public OrderedCollection(List<LinkOrObject> orderedItems, int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        super(totalItems, first, last, current);
        this.orderedItems=orderedItems;
    }
}
