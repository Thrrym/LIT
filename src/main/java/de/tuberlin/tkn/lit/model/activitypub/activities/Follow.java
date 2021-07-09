package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.storage.IStorage;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FollowActivity")
public class Follow extends Activity {
    private static final String type = "Follow";

    public Follow() {
    }

    public Follow(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        try {
            storage.addToFollowers(actorName, getObject());
        } catch (Exception ex) {
            return null;
        }
        return this;
    }

    public String getType() {
        return type;
    }
}
