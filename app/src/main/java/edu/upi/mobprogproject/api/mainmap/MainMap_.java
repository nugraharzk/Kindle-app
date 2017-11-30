
package edu.upi.mobprogproject.api.mainmap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "nama",
        "lat",
        "lon",
        "urgensi"
})
public class MainMap_ implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("nama")
    private String nama;
    @JsonProperty("lat")
    private String lat;
    @JsonProperty("lon")
    private String lon;
    @JsonProperty("urgensi")
    private String urgensi;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3715044290864435211L;

    /**
     * No args constructor for use in serialization
     */
    public MainMap_() {
    }

    /**
     * @param id
     * @param lon
     * @param urgensi
     * @param nama
     * @param lat
     */
    public MainMap_(String id, String nama, String lat, String lon, String urgensi) {
        super();
        this.id = id;
        this.nama = nama;
        this.lat = lat;
        this.lon = lon;
        this.urgensi = urgensi;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("nama")
    public String getNama() {
        return nama;
    }

    @JsonProperty("nama")
    public void setNama(String nama) {
        this.nama = nama;
    }

    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("lon")
    public String getLon() {
        return lon;
    }

    @JsonProperty("lon")
    public void setLon(String lon) {
        this.lon = lon;
    }

    @JsonProperty("urgensi")
    public String getUrgensi() {
        return urgensi;
    }

    @JsonProperty("urgensi")
    public void setUrgensi(String urgensi) {
        this.urgensi = urgensi;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
