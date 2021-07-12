package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.objects.Tombstone;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LikeActivity")
public class Like extends Activity {
    @Transient
    private static final String type = "Like";

    public Like() {
    }

    public Like(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorId, IStorage storage, int port) {
        if (getActor().isObject()) {
            getActor().getLitObject().setId(storage.getActor(getActor().getLitObject().getName()).getId());
        }

        if (!actorId.startsWith("http")) {
            actorId = storage.getActor(actorId).getId();
        }

        ActivityPubObject obj = storage.getObject(getObject().getId());

        if (obj != null) {
            // Set activity object as tombstone so we can return proper http code in ClientController
            if (obj.getType().equals("Tombstone")) {
                this.setObject(new LinkOrObject(new Tombstone()));
                return null;
            }

            List<String> l = obj.getLikedBy();
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
                obj.setLikedBy(list);
            }

            if (UriUtilities.isLocaleServer(actorId, port))
                storage.addToLiked(UriUtilities.getActor(actorId), new LinkOrObject(obj));

            return this;
        }

        return null;
    }

    public String getType() {
        return type;
    }
}
