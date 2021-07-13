package de.tuberlin.tkn.lit.model.Requests;

import java.util.List;

public class SearchRequest {
    private String searchType;
    private String query;
    
    private String literatureType;

    private String time;
    private Date date;

    private List<String> user; 
    private List<String> from;

    public String getSearchType() {
        return searchType;
    }

    public String getQuery() {
        return query;
    }

    public String getLiteratureType() {
        return literatureType;
    }

    public String getTime() {
        return time;
    }

    public List<String> getUser() {
        return user;
    }

    public List<String> getFrom() {
        return from;
    }

    public Date getDate() {
        return date;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLiteratureType(String literatureType) {
        this.literatureType = literatureType;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public void seFrom(List<String> from) {
        this.from = from;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public class Date {
        private String year;
        private String month;

        public String getYear() {
            return year;
        }

        public String getMonth() {
            return month;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
