package de.tuberlin.tkn.lit.model.lit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;
import de.tuberlin.tkn.lit.jsonutilities.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class BibTeXArticle extends ActivityPubObject {

    @OneToMany(targetEntity = LinkOrObject.class,cascade = CascadeType.ALL)
    //@JoinColumn(name = "activity_pub_id")
    private List<LinkOrObject> authors;
    private String title;
    private String journal;
    private int year;
    private int volume;
    @ElementCollection
    private List<String> likedBy;

    public BibTeXArticle() {
    }

    public BibTeXArticle(List<LinkOrObject> authors, String title, String journal, int year, int volume) {
        this.authors = authors;
        this.title = title;
        this.journal = journal;
        this.year = year;
        this.volume = volume;
    }

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

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
