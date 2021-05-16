package de.tuberlin.tkn.lit.model;

public class CollectionPage extends LitCollection{



    public CollectionPage(int totalItems, LitObject[] first, LitObject[] last, LitObject[] current) {
        super(totalItems, first, last, current);
    }

    public CollectionPage(LitObject[] items, int totalItems, LitObject[] first, LitObject[] last, LitObject[] current) {
        super(items, totalItems, first, last, current);
    }
}
