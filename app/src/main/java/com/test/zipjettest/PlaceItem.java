package com.test.zipjettest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kavya, 17-04-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceItem {
    String country,name;
    Double lati, longi;

    public PlaceItem(String country, String name, Double lati, Double longi){
        this.setCountry(country);
        this.setName(name);
        this.setLatitude(lati);
        this.setLongitude(longi);
    }

    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String id) {
        this.country = id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude(){return lati;}

    @JsonProperty("latitude")
    public void setLatitude(Double lati){ this.lati = lati;}

    public Double getLongitude(){return longi;}

    @JsonProperty("longitude")
    public void setLongitude(Double longi){ this.longi = longi;}
}
