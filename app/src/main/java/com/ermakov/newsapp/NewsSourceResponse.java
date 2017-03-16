package com.ermakov.newsapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Ответ на запрос источников новостей.
 */
public class NewsSourceResponse {

    /**
     * If the request was successful or not. Options: ok, error.
     * In the case of error a message property will be populated.
     */
    @SerializedName("status")
    private String mStatus;

    /**
     * A list of the news sources and blogs available on News API.
     */
    @SerializedName("sources")
    private List<NewsSource> sources;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public List<NewsSource> getSources() {
        return sources;
    }

    public void setSources(List<NewsSource> sources) {
        this.sources = sources;
    }
}
