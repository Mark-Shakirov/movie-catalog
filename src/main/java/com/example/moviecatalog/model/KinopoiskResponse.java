package com.example.moviecatalog.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;









@Data
public class KinopoiskResponse {
    @JsonProperty("items")
    @JsonAlias("films")
    private List<Movie> items;

    @JsonProperty("totalPages")
    @JsonAlias("pagesCount")
    private int totalPages;
    public List<Movie> getItems() {
        return items;
    }
    public int getTotalPages() {
        return totalPages;
    }
}