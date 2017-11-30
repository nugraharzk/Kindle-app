
package edu.upi.mobprogproject.api.mainmap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "main_map"
})
public class MainMap implements Serializable {

    @JsonProperty("main_map")
    private List<MainMap_> mainMap = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8576771422647728356L;

    /**
     * No args constructor for use in serialization
     */
    public MainMap() {
    }

    /**
     * @param mainMap
     */
    public MainMap(List<MainMap_> mainMap) {
        super();
        this.mainMap = mainMap;
    }

    @JsonProperty("main_map")
    public List<MainMap_> getMainMap() {
        return mainMap;
    }

    @JsonProperty("main_map")
    public void setMainMap(List<MainMap_> mainMap) {
        this.mainMap = mainMap;
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
