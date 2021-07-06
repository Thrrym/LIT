package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book extends ActivityPubObject {

    private String author;
    private String title;
    private String isbn;
    private String category;
    private String year;
    private String volume;
    private String _abstract;
    private String shop_url;
    @ElementCollection
    private List<String> likedBy;

    public Book() {
    }

    public Book(String author, String title, String isbn, String category, String year, String volume, String _abstract, String shop_url) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.category = category;
        this.year = year;
        this.volume = volume;
        this._abstract = _abstract;
        this.shop_url = shop_url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategory() {
        return category;
    }

    public String getYear() {
        return year;
    }

    public String getVolume() {
        return volume;
    }

    public String get_abstract() {
        return _abstract;
    }

    public String getShop_url() {
        return shop_url;
    }

    public List<String> getLikedBy() {
        return likedBy;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }
}
