package de.tuberlin.tkn.lit.model.activitypub.actor_items;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(targetEntity = LinkOrObject.class)
    private List<LinkOrObject> following;
}
