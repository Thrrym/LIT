package de.tuberlin.tkn.lit.model.litobjects;

import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;

import java.util.List;

public class BibTeXArticle extends LitObject {

    private String author;
    private String title;
    private String journal;
    private String year;
    private String volume;

    public int likes;
    public List<LinkOrObject> likedBy;

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
}
