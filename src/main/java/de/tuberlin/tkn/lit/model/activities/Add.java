package de.tuberlin.tkn.lit.model.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Add extends Activity {

    @JsonIgnore
    private static final String type = "Add";

    public Add() {
    }

    public Add(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName,IStorage storage,int port) {
        return this;
    }
}
