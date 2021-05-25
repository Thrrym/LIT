package de.tuberlin.tkn.lit.model.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Activity;

public class Block extends Ignore {

    @JsonIgnore
    private static final String type = "Block";

    public Block() {
    }

    public Block(Activity activity) {
        super(activity);
    }

    public String getType() {
        return type;
    }
}
