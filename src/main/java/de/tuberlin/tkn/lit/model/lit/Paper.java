package de.tuberlin.tkn.lit.model.lit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;
import de.tuberlin.tkn.lit.jsonutilities.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Paper extends ActivityPubObject {

    @OneToMany(targetEntity = Author.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_pub_id")
    private List<LinkOrObject> authors;
    private String title;
    private int year;
    private String category;
    private String paperAbstract;

    public List<LinkOrObject> getAuthors() {
        return authors;
    }

    public void setAuthors(List<LinkOrObject> authors) {
        this.authors = authors;
    }

    @JsonSetter("authors")
    public void setAuthors(JsonNode s) throws JsonProcessingException {
        authors = ArrayDeserializer.deserialize(s);
    }

    @JsonGetter("authors")
    public List<JsonNode> toJSONAuthors() throws JsonProcessingException {
        if (authors == null) return null;
        return ArraySerializer.serialize(authors);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }
}
