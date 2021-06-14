package de.tuberlin.tkn.lit.model.litobjects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;
import de.tuberlin.tkn.lit.serializer.ArraySerializer;

import java.util.List;

public class BibTeXArticle extends LitObject {

    private String author;
    private String title;
    private String journal;
    private String year;
    private String volume;
    private List<LinkOrObject> likedBy;

    public BibTeXArticle() {
    }

    public BibTeXArticle(String author, String title, String journal, String year, String volume) {
        this.author = author;
        this.title = title;
        this.journal = journal;
        this.year = year;
        this.volume = volume;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getJournal() {
        return journal;
    }

    public String getYear() {
        return year;
    }

    public String getVolume() {
        return volume;
    }

    @JsonGetter("likes")
    public int getLikes() {
        return likedBy.size();
    }

    @JsonSetter("likes")
    public void setLikes(int likes) {}

    @JsonGetter("likedBy")
    public List<JsonNode> toJSONLikedBy() throws JsonProcessingException {
        if (likedBy == null) return null;
        return ArraySerializer.serialize(likedBy);
    }

    public List<LinkOrObject> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<LinkOrObject> likedBy) {
        this.likedBy = likedBy;
    }
}
