package com.ermakov.newsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Интерфейс, объявляющий методы для работы с News API.
 */
public interface NewsApiService {

    /**
     * Получить список источников новостей.
     * @param category The category you would like to get sources for.
     * @param language The 2-letter ISO-639-1 code of the language you would like to get sources for.
     */
    @GET("/v1/sources")
    Call<NewsSourceResponse> getNewsSource(
            @Query("category") String category,
            @Query("language") String language
    );

    /**
     * Получить список статей.
     * @param source The identifer for the news source or blog you want headlines from.
     *               Use the /sources endpoint to locate this or use the sources index.
     * @return
     */
    @GET("/v1/articles")
    Call<NewsArticleResponse> getNewsArticle(
            @Query("source") String source
    );
}
