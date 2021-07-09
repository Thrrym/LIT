package de.tuberlin.tkn.lit.model.activitypub.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.storage.IStorage;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AcceptActivity")
public class Accept extends Activity {

    @JsonIgnore
    private static final String type = "Accept";

    public Accept() {
    }

    public Accept(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage,int port) {
        return this;
    }

}
