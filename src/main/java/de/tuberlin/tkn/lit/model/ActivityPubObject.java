package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.tuberlin.tkn.lit.model.objects.Note;

import java.util.HashMap;
import java.net.URI;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes( value = {
        @JsonSubTypes.Type(value = Note.class, name = "Note"),
})
public class ActivityPubObject {

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
