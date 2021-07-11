package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IStorage;

import java.util.List;

public class Reject extends Activity {
    private static final String type = "Reject";

    public Reject() {
    }

    public Reject(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName,IStorage storage,int port) {
        Activity offer = storage.getActivity(getObject().getId());

        // Delete rejected Offer from Inbox
        List<LinkOrObject> inbox = storage.getInbox(actorName).getOrderedItems();
        LinkOrObject offer_to_delete = inbox.stream().filter(item -> item.getLitObject().getId().equals(offer.getId())).findFirst().get();
        inbox.remove(offer_to_delete);

        return this;
    }

    public String getType() {
        return type;
    }
}
