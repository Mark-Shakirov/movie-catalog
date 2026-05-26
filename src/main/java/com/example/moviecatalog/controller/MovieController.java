package com.example.moviecatalog.controller;

import com.example.moviecatalog.model.KinopoiskResponse;
import com.example.moviecatalog.model.Movie;
import com.example.moviecatalog.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping("/")
    public String viewHomePage(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        int currentPage = (page == null || page < 1) ? 1 : page;
        KinopoiskResponse response = null;
        try {
            if (keyword != null && !keyword.trim().isEmpty()) {
                System.out.println("Выполняется поиск по ключевому слову: " + keyword.trim());
                response = movieService.searchMovies(keyword.trim(), currentPage);
                model.addAttribute("keyword", keyword.trim());
            } else {
                System.out.println("Загрузка главной страницы популярных фильмов. Страница: " + currentPage);
                response = movieService.getMovies(currentPage);
            }
        } catch (Exception e) {
            System.err.println("Глобальный сбой при получении данных от API: " + e.getMessage());
        }

        int totalPages = 1;
        if (response != null && response.getItems() != null) {
            model.addAttribute("movies", response.getItems());
            totalPages = response.getTotalPages();
        } else {
            model.addAttribute("movies", Collections.emptyList());
        }

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPages, currentPage + 2);

        if (currentPage <= 3) {
            startPage = 1;
            endPage = Math.min(totalPages, 5);
        } else if (currentPage > totalPages - 2) {
            startPage = Math.max(1, totalPages - 4);
            endPage = totalPages;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }
    //------------------------------------------------------------
    @GetMapping("/movie/details/{id}")
    public String getMovieDetails(@PathVariable("id") int id, Model model) {
        try {
            System.out.println("=== AJAX запрос для фильма с ID: " + id + " ===");

            Movie movie = movieService.getMovieDetails(id);

            System.out.println("=== Успешно получено от API. Название: " + (movie != null ? movie.getTitle() : "null") + " ===");

            model.addAttribute("movie", movie);
            return "index :: movieDetailsModal";
        } catch (Exception e) {
            System.err.println("=== ОШИБКА ПРИ ПОЛУЧЕНИИ ДЕТАЛЕЙ ФИЛЬМА ===");
            e.printStackTrace();
            throw e;
        }
    }
}