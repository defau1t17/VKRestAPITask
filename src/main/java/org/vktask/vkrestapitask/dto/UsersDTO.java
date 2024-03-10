package org.vktask.vkrestapitask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vktask.vkrestapitask.entity.Address;
import org.vktask.vkrestapitask.entity.Company;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDTO {
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "address")
    private Address address;
    @JsonProperty(value = "phone")
    private String phone;
    @JsonProperty(value = "website")
    private String website;
    @JsonProperty(value = "company")
    private Company company;
}
