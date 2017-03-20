package com.ermakov.newsapp.net;

import com.ermakov.newsapp.models.NewsArticle;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Ответ на запрос статей.
 */
public class NewsArticleResponse {
    @SerializedName("status")
    private String mStatus;

    @SerializedName("source")
    private String mSource;

    @SerializedName("sortBy")
    private String mSortBy;

    @SerializedName("articles")
    private List<NewsArticle> mArticles;

    /**
     * If the request was successful or not. Options: ok, error.
     * In the case of error a message property will be populated.
     */
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    /**
     * The identifier of the source requested.
     */
    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    /**
     * Which type of article list is being returned. Options: top, latest, popular.
     */
    public String getSortBy() {
        return mSortBy;
    }

    public void setSortBy(String sortBy) {
        mSortBy = sortBy;
    }

    /**
     * The list of headline metadata requested.
     */
    public List<NewsArticle> getArticles() {
        return mArticles;
    }

    public void setArticles(List<NewsArticle> articles) {
        mArticles = articles;
    }
}
