package org.vktask.vkrestapitask.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @JsonProperty(value = "street")
    private String street;
    @JsonProperty(value = "suite")
    private String suite;
    @JsonProperty(value = "city")
    private String city;
    @JsonProperty(value = "zipcode")
    private String zipcode;
    @JsonProperty(value = "geo")
    private Geodata geo;

}
