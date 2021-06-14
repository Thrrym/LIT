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

        if (UriUtilities.isLocaleServer(getObject().getLink(), port)) {
            String[] id = getObject().getLink().split("/");
            UUID uuid = java.util.UUID.fromString(id[id.length - 1]);
            LitObject obj = storage.getObject(uuid);

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

                bibTeXArticle.incrementLikes();

                storage.addToLiked(actorName, new LinkOrObject(bibTeXArticle));

                LinkOrObject o = storage.getRelevantObjects(actorName).getOrderedItems().stream().filter(x -> x.getLitObject().getId().equals(bibTeXArticle.getId())).findFirst().orElse(null);
                if (o != null) {
                    BibTeXArticle a = (BibTeXArticle) o.getLitObject();
                    List<LinkOrObject> list = a.getLikedBy();
                    if (list != null) {
                        for (LinkOrObject ob : list) {
                            if (ob.getLitObject().getId().equals(storage.getActor(actorName).getId())) {
                                return this;
                            }
                        }

                        list.add(new LinkOrObject(actor));

                    } else {
                        List<LinkOrObject> li = new ArrayList<>();
                        li.add(new LinkOrObject(actor));
                        a.setLikedBy(li);
                    }
                }
            }
        } else {
            LinkOrObject o = storage.getRelevantObjects(actorName).getOrderedItems().stream().filter(x -> x.getLitObject().getId().equals(getObject().getLink())).findFirst().orElse(null);
            if (o != null) {
                BibTeXArticle a = (BibTeXArticle) o.getLitObject();
                List<LinkOrObject> list = a.getLikedBy();
                if (list != null) {
                    for (LinkOrObject ob : list) {
                        if (ob.getLitObject().getId().equals(storage.getActor(actorName).getId())) {
                            return this;
                        }
                    }

                    a.incrementLikes();
                    list.add(new LinkOrObject(actor));

                } else {
                    a.incrementLikes();
                    List<LinkOrObject> li = new ArrayList<>();
                    li.add(new LinkOrObject(actor));
                    a.setLikedBy(li);
                }
            }
        }


        return this;
    }

    public String getType() {
        return type;
    }
}
