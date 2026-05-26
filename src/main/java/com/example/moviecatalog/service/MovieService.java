package com.example.moviecatalog.service;

import com.example.moviecatalog.model.Movie;
import com.example.moviecatalog.model.KinopoiskResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Service
public class MovieService {


    //rjls
    private final String API_KEY = System.getenv("KINOPOISK_API_KEY");
    private final String BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2";
    private final WebClient webClient;



    public MovieService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("X-API-KEY", API_KEY) // Заголовок специально для этого API
                .build();
    }
    public KinopoiskResponse getMovies(int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/films/collections")
                        .queryParam("type", "TOP_POPULAR_ALL")
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(KinopoiskResponse.class)
                .block();
    }
    public Movie getMovieDetails(int id) {
        try {
            return this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/films/{id}")
                            .build(id))
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Ошибка WebClient при запросе фильма " + id + ": " + e.getMessage());
            return null;
        }
    }
    public KinopoiskResponse searchMovies(String keyword, int page) {
        try {
            return this.webClient.get()
                    // Передаем строку с параметрами напрямую в .uri()
                    .uri("https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=" + keyword + "&page=" + page)
                    .retrieve()
                    .bodyToMono(KinopoiskResponse.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Ошибка при поиске фильмов: " + e.getMessage());
            return null;
        }
    }
}