package com.example.moviecatalog.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class Movie {
    @JsonProperty("kinopoiskId")
    @JsonAlias("filmId")
    private int id;
    @JsonProperty("nameRu")
    private String nameRu;
    @JsonProperty("nameEn")
    private String nameEn;
    @JsonProperty("nameOriginal")
    private String nameOriginal;
    @JsonProperty("title")
    private String title;

    public String getTitle() {
        if (nameRu != null && !nameRu.isEmpty()) {
            return nameRu;
        }
        if (title != null && !title.isEmpty()) {
            return title;
        }
        if (nameEn != null && !nameEn.isEmpty()) {
            return nameEn;
        }
        if (nameOriginal != null && !nameOriginal.isEmpty()) {
            return nameOriginal;
        }
        return "Без названия";
    }
    @JsonProperty("description")
    private String description;
    @JsonProperty("year")
    private String releaseDate;
    @JsonProperty("ratingKinopoisk")
    @JsonAlias("rating")
    private Double rating;

    @JsonProperty("posterUrl")
    @JsonAlias("posterUrlPreview")
    private String posterPath;
    @JsonProperty("filmLength")
    private String filmLength;

    @JsonProperty("countries")
    private List<Country> countries;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("slogan")
    private String slogan;
    @JsonProperty("type")
    private String type;
//---------------
    @JsonProperty("ratingAgeLimits")
    private String ratingAgeLimits;
    public String getNameOriginal() {
        if (nameOriginal == null || nameOriginal.isBlank() || nameOriginal.equals("null")) return "";
        return nameOriginal;
    }

    public String getSlogan() {
        if (slogan == null || slogan.isBlank() || slogan.equals("null")) return "";
        return "«" + slogan + "»";
    }
    public String getFormattedType() {
        if (type == null) return "Медиапроект";
        switch (type) {
            case "FILM": return "Художественный фильм";
            case "TV_SERIES": return "Сериал";
            case "MINI_SERIES": return "Мини-сериал";
            case "TV_SHOW": return "Телешоу";
            default: return "Видео";
        }
    }
    public String getFormattedAgeLimit() {
        if (ratingAgeLimits == null || ratingAgeLimits.isBlank()) return "";
        return ratingAgeLimits.replace("age", "") + "+";
    }
    public static class Country {
        @JsonProperty("country")
        private String country;

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
    public static class Genre {
        @JsonProperty("genre")
        private String genre;

        public String getGenre() { return genre; }
        public void setGenre(String genre) { this.genre = genre; }
    }
    public String getFormattedCountries() {
        if (countries == null || countries.isEmpty()) return "Не указаны";
        return countries.stream()
                .map(Country::getCountry)
                .collect(java.util.stream.Collectors.joining(", "));
    }
    public String getFormattedGenres() {
        if (genres == null || genres.isEmpty()) return "Не указаны";
        return genres.stream()
                .map(Genre::getGenre)
                .collect(java.util.stream.Collectors.joining(", "));
    }
    public String getFormattedLength() {
        if (filmLength == null || filmLength.trim().isEmpty() || filmLength.equals("null")) {
            return "Неизвестно";
        }

        if (filmLength.contains(":")) {
            return filmLength;
        }
        return filmLength + " мин.";
    }

    public String getFullPosterUrl() {
        if (posterPath == null || posterPath.trim().isEmpty() || posterPath.equals("null")) {
            return "https://images.unsplash.com/photo-1594322436404-5a0526db4d13?q=80&w=360&auto=format&fit=crop";
        }
        return posterPath;
    }
    public String getDescription() {
        if (description == null || description.isBlank()) {
            return "Описание для данного фильма временно отсутствует. Команда сервиса уже работает над добавлением информации)))";
        }
        return description;
    }

    public String getReleaseYear() {
        return releaseDate;
    }
}