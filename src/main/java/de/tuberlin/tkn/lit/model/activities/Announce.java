package de.tuberlin.tkn.lit.model.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Activity;

public class Announce extends Activity {

    @JsonIgnore
    private static final String type = "Announce";

    public Announce() {
    }

    public Announce(Activity activity) {
        super(activity);
    }

}
