package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.tuberlin.tkn.lit.model.activities.*;
import de.tuberlin.tkn.lit.model.actors.*;
import de.tuberlin.tkn.lit.model.objects.*;

import java.net.URI;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes( value = {
        @JsonSubTypes.Type(value = Article.class, name = "Article"),
        @JsonSubTypes.Type(value = Document.class, name = "Document"),
        @JsonSubTypes.Type(value = Event.class, name = "Event"),
        @JsonSubTypes.Type(value = Image.class, name = "Image"),
        @JsonSubTypes.Type(value = Note.class, name = "Note"),
        @JsonSubTypes.Type(value = Page.class, name = "Page"),
        @JsonSubTypes.Type(value = Place.class, name = "Place"),
        @JsonSubTypes.Type(value = Tombstone.class, name = "Tombstone"),

        @JsonSubTypes.Type(value = Application.class, name = "Application"),
        @JsonSubTypes.Type(value = Group.class, name = "Group"),
        @JsonSubTypes.Type(value = Organization.class, name = "Organization"),
        @JsonSubTypes.Type(value = Person.class, name = "Person"),
        @JsonSubTypes.Type(value = Service.class, name = "Service"),

        @JsonSubTypes.Type(value = Accept.class, name = "Accept"),
        @JsonSubTypes.Type(value = Add.class, name = "Add"),
        @JsonSubTypes.Type(value = Announce.class, name = "Announce"),
        @JsonSubTypes.Type(value = Block.class, name = "Block"),
        @JsonSubTypes.Type(value = Create.class, name = "Create"),
        @JsonSubTypes.Type(value = Delete.class, name = "Delete"),
        @JsonSubTypes.Type(value = Dislike.class, name = "Dislike"),
        @JsonSubTypes.Type(value = Flag.class, name = "Flag"),
        @JsonSubTypes.Type(value = Follow.class, name = "Follow"),
        @JsonSubTypes.Type(value = Ignore.class, name = "Ignore"),
        @JsonSubTypes.Type(value = Join.class, name = "Join"),
        @JsonSubTypes.Type(value = Leave.class, name = "Leave"),
        @JsonSubTypes.Type(value = Like.class, name = "Like"),
        @JsonSubTypes.Type(value = Move.class, name = "Move"),
        @JsonSubTypes.Type(value = Offer.class, name = "Offer"),
        @JsonSubTypes.Type(value = Question.class, name = "Question"),
        @JsonSubTypes.Type(value = Read.class, name = "Read"),
        @JsonSubTypes.Type(value = Reject.class, name = "Reject"),
        @JsonSubTypes.Type(value = Remove.class, name = "Remove"),
        @JsonSubTypes.Type(value = Undo.class, name = "Undo"),
        @JsonSubTypes.Type(value = Update.class, name = "Update"),
        @JsonSubTypes.Type(value = View.class, name = "View"),

        // TODO: Add all possible types here
})
public class ActivityPubObject {

    // TODO: Add all possible attributes + getter/setter
    private String summary;
    private String name;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    // Link
    private URI link;

    // Object
    private String id;

    // TODO: Create missing classes
    //private Attachment[] Hashmap<String, String>; // Eventuell String
    private String[] Attachment;
    private String[] attributedTo;
    private String[] audience;
    private String content;
    private HashMap<String, String> contentMap;
    private String context;
    private String name;
    private HashMap<String, String> nameMap;
    private String startTime;
    private String endTime;
    private String[] generator;
    private String[] icon;
    private String[] image;
    private String[] inReplyTo;
    private String[] location;
    private String[] preview;
    private String published;
    //private Collection replies;
    private String summary;
    private HashMap<String, String> summaryMap;
    private String[] tag;
    private String updated;
    private String[] url;
    private String[] to;
    private String[] bto;
    private String[] cc;
    private String[] bcc;
    private String mediaType;
    private String duration;
     */

}
