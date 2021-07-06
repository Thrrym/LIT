package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;

public class Book extends ActivityPubObject {

    private String author;
    private String title;
    private String journal;
    private String year;
    //private String List<String> likedBy;



}
