package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Country extends ActivityPubObject {

    private String country;
    private String continent;
    private String timezone;

    public Country() {
    }

    public Country(String country, String continent, String timezone) {
        this.country = country;
        this.continent = continent;
        this.timezone = timezone;
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
