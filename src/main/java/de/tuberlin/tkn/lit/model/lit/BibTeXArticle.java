package de.tuberlin.tkn.lit.model.lit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import java.util.List;

public class BibTeXArticle extends ActivityPubObject {

    private String author;
    private String title;
    private String journal;
    private String year;
    private String volume;
    private List<String> likedBy;

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
        if(likedBy == null)
            return 0;
        return likedBy.size();
    }

    @JsonSetter("likes")
    public void setLikes(int value){}

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }
}
