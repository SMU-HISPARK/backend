package com.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FilteringResponseDto {
    @JsonProperty("filtered_title")
    private String filteredTitle;
    @JsonProperty("filtered_text")
    private String filteredText;
}