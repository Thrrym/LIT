package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Create extends Activity {

    public Create() {
    }

    public Create(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        if (getActor().isObject()) {
            getActor().getLitObject().setId(storage.getActor(getActor().getLitObject().getName()).getId());
        }

        ActivityPubObject createdObject;
        if (getObject().isObject()) {
            if (getObject().getLitObject() instanceof Author && storage.authorExists(((Author) getObject().getLitObject()).getOrcid())) {
                return null;
            }
            if (getObject().getLitObject() instanceof BibTeXArticle) {
                for (LinkOrObject author : ((BibTeXArticle) getObject().getLitObject()).getAuthors()) {
                    if (storage.getObject(author.getId()) == null) {
                        return null;
                    }
                }
            }
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
            setObject(new LinkOrObject(createdObject));

            return this;
        }

        return null;
    }
}
