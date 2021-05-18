package de.tuberlin.tkn.lit.model.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Activity;

public class Accept extends Activity {

    @JsonIgnore
    private static final String type = "Accept";

    public Accept() {
    }

    public Accept(Activity activity) {
        super(activity);
    }

}
