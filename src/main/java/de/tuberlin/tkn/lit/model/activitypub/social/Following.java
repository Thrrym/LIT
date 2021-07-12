package de.tuberlin.tkn.lit.model.activitypub.social;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String actorname;

    public String getActorname() {
        return actorname;
    }
}
