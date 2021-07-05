package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.ArrayList;
import java.util.List;

public class Like extends Activity {
    private static final String type = "Like";

    public Like() {
    }

    public Like(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorId, IStorage storage, int port) {
        if(!actorId.startsWith("http"))
            actorId = storage.getActor(actorId).getId();
        ActivityPubObject obj = storage.getObject(getObject().getId());

        if (obj != null) {
            BibTeXArticle bibTeXArticle = (BibTeXArticle) obj;
            List<String> l = bibTeXArticle.getLikedBy();
            if (l != null) {
                for (String o : l) {
                    if (o.equals(actorId)) {
                        return this;
                    }
                }

                l.add(actorId);

            } else {
                List<String> list = new ArrayList<>();
                list.add(actorId);
                bibTeXArticle.setLikedBy(list);
            }

            if(UriUtilities.isLocaleServer(actorId, port))
                storage.addToLiked(UriUtilities.getActor(actorId), new LinkOrObject(bibTeXArticle));


            // Inform followers
            if (storage.getFollowersCollection(getActor().getLitObject().getName()).getOrderedItems() != null)
                setTo(storage.getFollowersCollection(getActor().getLitObject().getName()).getOrderedItems());
        }

        return this;
    }

    public String getType() {
        return type;
    }
}
