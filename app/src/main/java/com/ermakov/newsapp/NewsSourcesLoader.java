package com.ermakov.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Загрузка источников новостей.
 */
public class NewsSourcesLoader extends AsyncTaskLoader<List<NewsSource>> {

    private String mCategory;
    private String mLanguage;

    public NewsSourcesLoader(Context context, String category, String language) {
        super(context);
        this.mCategory = category;
        this.mLanguage = language;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

    }

    @Override
    public List<NewsSource> loadInBackground() {
        NewsApiService newsApiService = NewsApiFactory.createNewsApiService();
        try {
            Response<NewsSourceResponse> response = newsApiService.getNewsSources(mCategory, mLanguage)
                    .execute();
            return (response.isSuccessful()) ? response.body().getSources() : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
