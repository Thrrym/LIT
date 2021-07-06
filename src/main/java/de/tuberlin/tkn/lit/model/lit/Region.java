package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Region extends ActivityPubObject {

    private String region;
    private String country;
    private String timezone;

    public Region() {
    }

    public Region(String region, String country, String timezone) {
        this.region = region;
        this.country = country;
        this.timezone = timezone;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
