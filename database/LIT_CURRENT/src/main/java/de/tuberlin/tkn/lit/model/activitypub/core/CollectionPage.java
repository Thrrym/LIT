package de.tuberlin.tkn.lit.model.activitypub.core;

import java.util.List;

public class CollectionPage extends ActivityPubCollection {



    public CollectionPage(int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        super(totalItems, first, last, current);
    }

    public CollectionPage(List<LinkOrObject> items, int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        super(items, totalItems, first, last, current);
    }
}
