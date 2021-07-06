package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Country extends ActivityPubObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String country;
    private String continent;
    private String timezone;

    public Country() {
    }

    public Country(long id, String country, String continent, String timezone) {
        this.id = id;
        this.country = country;
        this.continent = continent;
        this.timezone = timezone;
    }

    public long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
