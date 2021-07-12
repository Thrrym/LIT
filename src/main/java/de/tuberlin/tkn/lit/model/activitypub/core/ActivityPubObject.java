package de.tuberlin.tkn.lit.model.activitypub.core;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;
import de.tuberlin.tkn.lit.model.activitypub.activities.*;
import de.tuberlin.tkn.lit.model.activitypub.actors.*;
import de.tuberlin.tkn.lit.model.lit.*;
import de.tuberlin.tkn.lit.model.activitypub.objects.*;
import de.tuberlin.tkn.lit.jsonutilities.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.jsonutilities.serializer.LinkOrObjectSerializer;
import de.tuberlin.tkn.lit.model.activitypub.activities.*;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.objects.Note;
import de.tuberlin.tkn.lit.model.activitypub.objects.Tombstone;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.model.lit.Book;
import de.tuberlin.tkn.lit.model.lit.Paper;

import javax.persistence.*;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = ActivityPubCollection.class, name = "Collection"),
        @JsonSubTypes.Type(value = OrderedCollection.class, name = "OrderedCollection"),

        @JsonSubTypes.Type(value = BibTeXArticle.class, name = "bibtex_article"),
        @JsonSubTypes.Type(value = Author.class, name = "Author"),
        @JsonSubTypes.Type(value = Paper.class, name = "Paper"),
        @JsonSubTypes.Type(value = Book.class, name = "Book"),
        @JsonSubTypes.Type(value = Journal.class, name = "Journal"),
        @JsonSubTypes.Type(value = Note.class, name = "Note"),
        @JsonSubTypes.Type(value = Tombstone.class, name = "Tombstone"),

        @JsonSubTypes.Type(value = Actor.class, name = "Actor"),
        @JsonSubTypes.Type(value = Person.class, name = "Person"),

        @JsonSubTypes.Type(value = Activity.class, name = "Activity"),
        @JsonSubTypes.Type(value = Accept.class, name = "Accept"),
        @JsonSubTypes.Type(value = Create.class, name = "Create"),
        @JsonSubTypes.Type(value = Delete.class, name = "Delete"),
        @JsonSubTypes.Type(value = Follow.class, name = "Follow"),
        @JsonSubTypes.Type(value = Like.class, name = "Like"),
        @JsonSubTypes.Type(value = Offer.class, name = "Offer"),
        @JsonSubTypes.Type(value = Reject.class, name = "Reject"),
        @JsonSubTypes.Type(value = Update.class, name = "Update"),

        @JsonSubTypes.Type(value = Person.class, name = "Person"),

        @JsonSubTypes.Type(value = ActivityPubCollection.class, name = "Collection"),
        @JsonSubTypes.Type(value = OrderedCollection.class, name = "OrderedCollection"),

        @JsonSubTypes.Type(value = Note.class, name = "Note"),
        @JsonSubTypes.Type(value = Tombstone.class, name = "Tombstone"),

        @JsonSubTypes.Type(value = Author.class, name = "Author"),
        @JsonSubTypes.Type(value = BibTeXArticle.class, name = "bibtex_article"),
        @JsonSubTypes.Type(value = Book.class, name = "Book"),
        @JsonSubTypes.Type(value = Paper.class, name = "Paper")
})
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ActivityPubObject {

    @JsonProperty("@context")
    private String context;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonIgnore
    public long activityPubID;

    private String id;
    private String type;
    private String name;
    private String published;
    private String content;
    private String startTime;
    private String endTime;
    private String summary;
    private String updated;
    private String mediaType;
    private String duration;

    @Transient
    private OrderedCollection likes;
    //private int like;

    @OneToOne(targetEntity = ActivityPubCollection.class, cascade = CascadeType.ALL)
    private ActivityPubCollection replies;

    @OneToOne(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private LinkOrObject generator;
    @OneToOne(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private LinkOrObject preview;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> icon;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> image;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> inReplyTo;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> location;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> attachment;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> attributedTo;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> audience;
    @OneToMany(targetEntity = LinkOrObject.class, cascade = CascadeType.ALL)
    private List<LinkOrObject> tag;
    @ElementCollection
    private List<String> likedBy;

    @OneToMany(targetEntity = LinkOrObject.class, cascade=CascadeType.ALL)
    private List<LinkOrObject> url;
    @OneToMany(targetEntity = LinkOrObject.class, cascade=CascadeType.ALL)
    private List<LinkOrObject> to;
    @OneToMany(targetEntity = LinkOrObject.class, cascade=CascadeType.ALL)
    private List<LinkOrObject> bto;
    @OneToMany(targetEntity = LinkOrObject.class, cascade=CascadeType.ALL)
    private List<LinkOrObject> cc;
    @OneToMany(targetEntity = LinkOrObject.class, cascade=CascadeType.ALL)
    private List<LinkOrObject> bcc;

    public ActivityPubObject() {
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

    public List<LinkOrObject> getAttributedTo() {
        return attributedTo;
    }

    public void setAttributedTo(List<LinkOrObject> attributedTo) {
        this.attributedTo = attributedTo;
    }

    @JsonGetter("attributedTo")
    public List<JsonNode> toJSONAttributedTo() throws JsonProcessingException {
        if (attributedTo == null) return null;
        return ArraySerializer.serialize(attributedTo);
    }

    @JsonSetter("attributedTo")
    public void attributedTo(JsonNode s) throws JsonProcessingException {
        attributedTo = ArrayDeserializer.deserialize(s);
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

    @JsonGetter("generator")
    public JsonNode toJSONGenerator() throws JsonProcessingException {
        if (generator == null) return null;
        return LinkOrObjectSerializer.serialize(generator);
    }

    public LinkOrObject getGenerator() {
        return generator;
    }

    public void setGenerator(LinkOrObject generator) {
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

    public ActivityPubCollection getReplies() {
        return replies;
    }

    public void setReplies(ActivityPubCollection replies) {
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

    public void setTo(List<LinkOrObject> to) {
        this.to = to;
    }

    @JsonSetter("to")
    public void setTo(JsonNode s) throws JsonProcessingException {
        to = ArrayDeserializer.deserialize(s);
    }

    @JsonGetter("to")
    public List<JsonNode> toJSONTo() throws JsonProcessingException {
        if (to == null) return null;
        return ArraySerializer.serialize(to);
    }

    public List<LinkOrObject> getBto() {
        return bto;
    }

    public void setBto(List<LinkOrObject> bto) {
        this.bto = bto;
    }

    @JsonSetter("bto")
    public void setBto(JsonNode s) throws JsonProcessingException {
        bto = ArrayDeserializer.deserialize(s);
    }

    @JsonGetter("bto")
    public List<JsonNode> toJSONBto() throws JsonProcessingException {
        if (bto == null) return null;
        return ArraySerializer.serialize(bto);
    }

    public List<LinkOrObject> getCc() {
        return cc;
    }

    public void setCc(List<LinkOrObject> cc) {
        this.cc = cc;
    }

    @JsonSetter("cc")
    public void setCc(JsonNode s) throws JsonProcessingException {
        cc = ArrayDeserializer.deserialize(s);
    }

    @JsonGetter("cc")
    public List<JsonNode> toJSONCc() throws JsonProcessingException {
        if (cc == null) return null;
        return ArraySerializer.serialize(cc);
    }

    public List<LinkOrObject> getBcc() {
        return bcc;
    }

    public void setBcc(List<LinkOrObject> bcc) {
        this.bcc = bcc;
    }

    @JsonSetter("bcc")
    public void setBcc(JsonNode s) throws JsonProcessingException {
        bcc = ArrayDeserializer.deserialize(s);
    }

    @JsonGetter("bcc")
    public List<JsonNode> toJSONBcc() throws JsonProcessingException {
        if (bcc == null) return null;
        return ArraySerializer.serialize(bcc);
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

    public long getActivityPubID() {
        return activityPubID;
    }

    public void setActivityPubID(long activityPubID) {
        this.activityPubID = activityPubID;
    }

    @JsonGetter("likes")
    public int getLikes() {
        if (likedBy == null)
            return 0;
        return likedBy.size();
    }

    @JsonSetter("likes")
    public void setLikes(int value) {
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }
}
