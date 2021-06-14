package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;
import de.tuberlin.tkn.lit.model.litobjects.BibTeXArticle;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Like extends Activity {
    private static final String type = "Like";

    public Like() {
    }

    public Like(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        Actor actor = storage.getActor(actorName);

        LitObject obj = storage.getObject(getObject().getLink());

        if (obj != null) {
            BibTeXArticle bibTeXArticle = (BibTeXArticle) obj;
            List<LinkOrObject> l = bibTeXArticle.getLikedBy();
            if (l != null) {
                for (LinkOrObject o : l) {
                    if (o.getLitObject().getId().equals(storage.getActor(actorName).getId())) {
                        return this;
                    }
                }

                l.add(new LinkOrObject(actor));

            } else {
                List<LinkOrObject> list = new ArrayList<>();
                list.add(new LinkOrObject(actor));
                bibTeXArticle.setLikedBy(list);
            }

            storage.addToLiked(actorName, new LinkOrObject(bibTeXArticle));
        }

        return this;
    }

    public String getType() {
        return type;
    }
}
