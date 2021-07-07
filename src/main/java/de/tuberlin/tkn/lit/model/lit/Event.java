package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;

@Entity
public class Event extends ActivityPubObject {
    private String shortName;
    private String longName;
    private int month;
    private int year;
    private boolean virtual;
    //    private City city;
    //    private Event event;
    //    private Person creator;

    public Event() {
    }

    public Event(String shortName, String longName, int month, int year, boolean virtual) {
    }

}
