package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.deserializer.ArrayDeserializer;
import de.tuberlin.tkn.lit.model.activities.*;
import de.tuberlin.tkn.lit.model.actors.*;
import de.tuberlin.tkn.lit.model.litobjects.BibTeXArticle;
import de.tuberlin.tkn.lit.model.objects.*;
import de.tuberlin.tkn.lit.serializer.ArraySerializer;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = LitCollection.class, name = "Collection"),
        @JsonSubTypes.Type(value = OrderedCollection.class, name = "OrderedCollection"),

        @JsonSubTypes.Type(value = BibTeXArticle.class, name = "bibtex_article"),

        @JsonSubTypes.Type(value = Article.class, name = "Article"),
        @JsonSubTypes.Type(value = Document.class, name = "Document"),
        @JsonSubTypes.Type(value = Event.class, name = "Event"),
        @JsonSubTypes.Type(value = Image.class, name = "Image"),
        @JsonSubTypes.Type(value = Note.class, name = "Note"),
        @JsonSubTypes.Type(value = Page.class, name = "Page"),
        @JsonSubTypes.Type(value = Place.class, name = "Place"),
        @JsonSubTypes.Type(value = Tombstone.class, name = "Tombstone"),

        @JsonSubTypes.Type(value = Actor.class, name = "Actor"),
        @JsonSubTypes.Type(value = Application.class, name = "Application"),
        @JsonSubTypes.Type(value = Group.class, name = "Group"),
        @JsonSubTypes.Type(value = Organization.class, name = "Organization"),
        @JsonSubTypes.Type(value = Person.class, name = "Person"),
        @JsonSubTypes.Type(value = Service.class, name = "Service"),

        @JsonSubTypes.Type(value = Activity.class, name = "Activity"),
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
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class LitObject {

    @JsonProperty("@context")
    private String context;
    private String id;
    private String type;
    private List<LinkOrObject> attachment;
    private String attributedTo;
    private List<LinkOrObject> audience;
    private String content;
    private String startTime;
    private String endTime;
    private List<LinkOrObject> generator;
    private List<LinkOrObject> icon;
    private List<LinkOrObject> image;
    private List<LinkOrObject> inReplyTo;
    private List<LinkOrObject> location;
    private String name;
    private LinkOrObject preview;
    private String published;
    private LitCollection replies;
    private List<LinkOrObject> tag;
    private String summary;
    private String updated;
    private List<LinkOrObject> url;
    private List<LinkOrObject> to;
    private List<LinkOrObject> bto;
    private List<LinkOrObject> cc;
    private List<LinkOrObject> bcc;
    private String mediaType;
    private String duration;

    public LitObject() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LinkOrObject> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<LinkOrObject> attachment) {
        this.attachment = attachment;
    }

    public String getAttributedTo() {
        return attributedTo;
    }

    public void setAttributedTo(String attributedTo) {
        this.attributedTo = attributedTo;
    }

    public List<LinkOrObject> getAudience() {
        return audience;
    }

    public void setAudience(List<LinkOrObject> audience) {
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

    public List<LinkOrObject> getGenerator() {
        return generator;
    }

    public void setGenerator(List<LinkOrObject> generator) {
        this.generator = generator;
    }

    public List<LinkOrObject> getIcon() {
        return icon;
    }

    public void setIcon(List<LinkOrObject> icon) {
        this.icon = icon;
    }

    public List<LinkOrObject> getImage() {
        return image;
    }

    public void setImage(List<LinkOrObject> image) {
        this.image = image;
    }

    public List<LinkOrObject> getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(List<LinkOrObject> inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public List<LinkOrObject> getLocation() {
        return location;
    }

    public void setLocation(List<LinkOrObject> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkOrObject getPreview() {
        return preview;
    }

    public void setPreview(LinkOrObject preview) {
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

    public List<LinkOrObject> getTag() {
        return tag;
    }

    public void setTag(List<LinkOrObject> tag) {
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

    public List<LinkOrObject> getUrl() {
        return url;
    }

    public void setUrl(List<LinkOrObject> url) {
        this.url = url;
    }

    public List<LinkOrObject> getTo() {
        return to;
    }

    @JsonGetter("to")
    public List<String> toJSONTo() throws JsonProcessingException {
        if (to == null) return null;
        return ArraySerializer.serialize(to);
    }

    public void setTo(List<LinkOrObject> to) {
        this.to = to;
    }

    @JsonSetter("to")
    public void setTo(JsonNode s) throws JsonProcessingException {
        to = ArrayDeserializer.deserialize(s);
    }

    public List<LinkOrObject> getBto() {
        return bto;
    }

    @JsonGetter("bto")
    public List<String> toJSONBto() throws JsonProcessingException {
        if (bto == null) return null;
        return ArraySerializer.serialize(bto);
    }

    public void setBto(List<LinkOrObject> bto) {
        this.bto = bto;
    }

    @JsonSetter("bto")
    public void setBto(JsonNode s) throws JsonProcessingException {
        bto = ArrayDeserializer.deserialize(s);
    }

    public List<LinkOrObject> getCc() {
        return cc;
    }

    public void setCc(List<LinkOrObject> cc) {
        this.cc = cc;
    }

    @JsonGetter("cc")
    public List<String> toJSONCc() throws JsonProcessingException {
        if (cc == null) return null;
        return ArraySerializer.serialize(cc);
    }

    @JsonSetter("cc")
    public void setCc(JsonNode s) throws JsonProcessingException {
        cc = ArrayDeserializer.deserialize(s);
    }

    public List<LinkOrObject> getBcc() {
        return bcc;
    }

    @JsonGetter("bcc")
    public List<String> toJSONBcc() throws JsonProcessingException {
        if (bcc == null) return null;
        return ArraySerializer.serialize(bcc);
    }

    public void setBcc(List<LinkOrObject> bcc) {
        this.bcc = bcc;
    }

    @JsonSetter("bcc")
    public void setBcc(JsonNode s) throws JsonProcessingException {
        bcc = ArrayDeserializer.deserialize(s);
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
