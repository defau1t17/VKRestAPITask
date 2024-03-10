package org.vktask.vkrestapitask.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geodata {
    @JsonProperty(value = "lat")
    private String lat;
    @JsonProperty(value = "lng")
    private String lng;

}
