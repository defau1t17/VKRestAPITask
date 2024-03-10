package org.vktask.vkrestapitask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsDTO {
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "userId")
    private String userId;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "body")
    private String body;
}
