package com.ermakov.newsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Интерфейс, объявляющий методы для работы с News API.
 */
public interface NewsApiService {
    @GET("/v1/sources")
    Call<NewsSourceResponse> getNewsSource(
            @Query("category") String category,
            @Query("language") String language
    );
}
