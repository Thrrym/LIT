package de.tuberlin.tkn.lit.model.lit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;
import de.tuberlin.tkn.lit.jsonutilities.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import java.util.List;

public class Book extends ActivityPubObject {

    private List<LinkOrObject> authors;
    private String title;
    private String isbn;
    private String category;
    private int year;
    private int volume;
    private String bookAbstract;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getBookAbstract() {
        return bookAbstract;
    }

    public void setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
    }
}
