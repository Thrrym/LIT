package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import java.util.List;

public class BibTeXArticle extends ActivityPubObject {

    private List<LinkOrObject> authors;
    private String title;
    private String journal;
    private int year;
    private String volume;

    public BibTeXArticle() {
    }

    public BibTeXArticle(List<LinkOrObject> authors, String title, String journal, int year, String volume) {
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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
