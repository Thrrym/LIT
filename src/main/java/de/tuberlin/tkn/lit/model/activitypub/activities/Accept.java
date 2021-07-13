package de.tuberlin.tkn.lit.model.activitypub.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IStorage;

import java.util.List;

public class Accept extends Activity {

    @JsonIgnore
    private static final String type = "Accept";

    public Accept() {
    }

    public Accept(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        Activity offer = storage.getActivity(getObject().getId());

        // Delete accepted Offer from Inbox
        List<LinkOrObject> inbox = storage.getInbox(actorName).getOrderedItems();
        LinkOrObject offer_to_delete = inbox.stream().filter(item -> item.getId().equals(offer.getId())).findFirst().get();
        inbox.remove(offer_to_delete);

        Activity update = new Update(this);
        update.setType("Update");
        update.setObject(offer.getObject());
        return update;
    }
}
