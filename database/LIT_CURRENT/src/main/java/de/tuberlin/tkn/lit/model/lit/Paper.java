package de.tuberlin.tkn.lit.model.lit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Paper extends ActivityPubObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String author;
    private String title;
    private String year;
    private String category;
    private String _abstract;
    private List<String> likedBy;

    public Paper() {
    }

    public Paper(long id, String author, String title, String year, String category, String _abstract) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.year = year;
        this.category = category;
        this._abstract = _abstract;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public String get_abstract() {
        return _abstract;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }
}
