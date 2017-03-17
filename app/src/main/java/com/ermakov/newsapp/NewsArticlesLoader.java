package com.ermakov.newsapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Загрузка новостных статей.
 */
public class NewsArticlesLoader extends AsyncTaskLoader<List<NewsArticle>> {

    private String mSource;

    /**
     * Конструктор.
     * @param source Источник новостей.
     */
    public NewsArticlesLoader(Context context, String source) {
        super(context);
        this.mSource = source;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsArticle> loadInBackground() {
        NewsApiService newsApiService = NewsApiFactory.createNewsApiService();
        try {
            Response<NewsArticleResponse> response = newsApiService.getNewsArticle(mSource)
                    .execute();
            return (response.isSuccessful()) ? response.body().getArticles() : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
