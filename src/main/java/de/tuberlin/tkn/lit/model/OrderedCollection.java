package de.tuberlin.tkn.lit.model;

import java.util.List;

public class OrderedCollection extends LitCollection {

    private final List<LinkOrObject> orderedItems;

    public OrderedCollection(List<LinkOrObject> orderedItems, int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        super(totalItems, first, last, current);
        this.orderedItems = orderedItems;
    }

    public OrderedCollection(List<LinkOrObject> orderedItems) {
        super();
        this.orderedItems = orderedItems;
    }

    public List<LinkOrObject> getOrderedItems() {
        return orderedItems;
    }
}
