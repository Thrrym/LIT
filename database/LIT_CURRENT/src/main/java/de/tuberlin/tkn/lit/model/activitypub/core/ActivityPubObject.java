package de.tuberlin.tkn.lit.model.activitypub.core;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;
import de.tuberlin.tkn.lit.model.activitypub.activities.*;
import de.tuberlin.tkn.lit.model.activitypub.actors.*;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.model.activitypub.objects.*;
import de.tuberlin.tkn.lit.jsonutilities.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.jsonutilities.serializer.LinkOrObjectSerializer;

import javax.persistence.*;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = ActivityPubCollection.class, name = "Collection"),
        @JsonSubTypes.Type(value = OrderedCollection.class, name = "OrderedCollection"),

        @JsonSubTypes.Type(value = BibTeXArticle.class, name = "bibtex_article"),

        @JsonSubTypes.Type(value = Note.class, name = "Note"),
        @JsonSubTypes.Type(value = Tombstone.class, name = "Tombstone"),

        @JsonSubTypes.Type(value = Actor.class, name = "Actor"),
        @JsonSubTypes.Type(value = Person.class, name = "Person"),

        @JsonSubTypes.Type(value = Activity.class, name = "Activity"),
        @JsonSubTypes.Type(value = Accept.class, name = "Accept"),
        @JsonSubTypes.Type(value = Block.class, name = "Block"),
        @JsonSubTypes.Type(value = Create.class, name = "Create"),
        @JsonSubTypes.Type(value = Delete.class, name = "Delete"),
        @JsonSubTypes.Type(value = Dislike.class, name = "Dislike"),
        @JsonSubTypes.Type(value = Follow.class, name = "Follow"),
        @JsonSubTypes.Type(value = Like.class, name = "Like"),
        @JsonSubTypes.Type(value = Reject.class, name = "Reject"),
        @JsonSubTypes.Type(value = Undo.class, name = "Undo"),
        @JsonSubTypes.Type(value = Update.class, name = "Update"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedSuperclass
public abstract class ActivityPubObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("@context")
    private String context;
    private String ap_id;
    private String type;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> attachment;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> attributedTo;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> audience;
    private String content;
    private String startTime;
    private String endTime;
    @OneToOne(targetEntity = LinkOrObject.class)
    private LinkOrObject generator;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> icon;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> image;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> inReplyTo;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> location;
    private String name;
    @OneToOne(targetEntity = LinkOrObject.class)
    private LinkOrObject preview;
    private String published;
    @OneToOne(targetEntity = ActivityPubCollection.class)
    private ActivityPubCollection replies;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> tag;
    //private OrderedCollection likes;
    //private int like;
    private String summary;
    private String updated;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> url;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> to;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> bto;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> cc;
    @OneToMany(targetEntity=LinkOrObject.class)
    private List<LinkOrObject> bcc;
    private String mediaType;
    private String duration;

    public ActivityPubObject() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAp_id() {
        return ap_id;
    }

    public void setAp_id(String ap_id) {
        this.ap_id = ap_id;
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

    @JsonGetter("attributedTo")
    public List<JsonNode> toJSONAttributedTo() throws JsonProcessingException {
        if (attributedTo == null) return null;
        return ArraySerializer.serialize(attributedTo);
    }

    public void setAttributedTo(List<LinkOrObject> attributedTo) {
        this.attributedTo = attributedTo;
    }

    @JsonSetter("attributedTo")
    public void attributedTo(JsonNode s) throws JsonProcessingException {
        attributedTo = ArrayDeserializer.deserialize(s);
    }

    /*public OrderedCollection getLikes() {
        return likes;
    }

    public void setLikes(OrderedCollection likes) {
        this.likes = likes;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
*/
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

    @JsonGetter("to")
    public List<JsonNode> toJSONTo() throws JsonProcessingException {
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
    public List<JsonNode> toJSONBto() throws JsonProcessingException {
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
    public List<JsonNode> toJSONCc() throws JsonProcessingException {
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
    public List<JsonNode> toJSONBcc() throws JsonProcessingException {
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