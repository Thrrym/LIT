package de.tuberlin.tkn.lit.model.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Accept extends Activity {

    @JsonIgnore
    private static final String type = "Accept";

    public Accept() {
    }

    public Accept(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage) {
        return this;
    }

}
