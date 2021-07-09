package de.tuberlin.tkn.lit.model.activitypub.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BlockActivity")
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
