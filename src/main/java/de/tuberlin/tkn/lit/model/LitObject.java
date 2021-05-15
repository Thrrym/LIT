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
public class LitObject {
    // Link
    private URI link;

    // Object
    private String id;
    private LitObject[] attachment;
    private LitObject[] attributedTo;
    private LitObject[] audience;
    private String content;
    private String context;
    private String startTime;
    private String endTime;
    private LitObject generator;
    private LitObject[] icon;
    private LitObject[] image;
    private LitObject[] inReplyTo;
    private LitObject[] location;
    private String name;
    private LitObject preview;
    private String published;
    private LitCollection replies;
    private LitObject[] tag;
    private String summary;
    private String updated;
    private URI[] url;
    private URI[] to;
    private URI[] bto;
    private URI[] cc;
    private URI[] bcc;
    private String mediaType;
    private String duration;

    public URI getLink() {
        return link;
    }

    public void setLink(URI link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LitObject[] getAttachment() {
        return attachment;
    }

    public void setAttachment(LitObject[] attachment) {
        this.attachment = attachment;
    }

    public LitObject[] getAttributedTo() {
        return attributedTo;
    }

    public void setAttributedTo(LitObject[] attributedTo) {
        this.attributedTo = attributedTo;
    }

    public LitObject[] getAudience() {
        return audience;
    }

    public void setAudience(LitObject[] audience) {
        this.audience = audience;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public LitObject getGenerator() {
        return generator;
    }

    public void setGenerator(LitObject generator) {
        this.generator = generator;
    }

    public LitObject[] getIcon() {
        return icon;
    }

    public void setIcon(LitObject[] icon) {
        this.icon = icon;
    }

    public LitObject[] getImage() {
        return image;
    }

    public void setImage(LitObject[] image) {
        this.image = image;
    }

    public LitObject[] getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(LitObject[] inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public LitObject[] getLocation() {
        return location;
    }

    public void setLocation(LitObject[] location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LitObject getPreview() {
        return preview;
    }

    public void setPreview(LitObject preview) {
        this.preview = preview;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public LitCollection getReplies() {
        return replies;
    }

    public void setReplies(LitCollection replies) {
        this.replies = replies;
    }

    public LitObject[] getTag() {
        return tag;
    }

    public void setTag(LitObject[] tag) {
        this.tag = tag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public URI[] getUrl() {
        return url;
    }

    public void setUrl(URI[] url) {
        this.url = url;
    }

    public URI[] getTo() {
        return to;
    }

    public void setTo(URI[] to) {
        this.to = to;
    }

    public URI[] getBto() {
        return bto;
    }

    public void setBto(URI[] bto) {
        this.bto = bto;
    }

    public URI[] getCc() {
        return cc;
    }

    public void setCc(URI[] cc) {
        this.cc = cc;
    }

    public URI[] getBcc() {
        return bcc;
    }

    public void setBcc(URI[] bcc) {
        this.bcc = bcc;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
