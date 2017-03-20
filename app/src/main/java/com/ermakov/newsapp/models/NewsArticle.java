package com.ermakov.newsapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Статья.
 */
public class NewsArticle {

    public static final String FORMAT_PUBLISHED_AT = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    public static final String TIMEZONE_PUBLISHED_AT = "GMT+0";
    @SerializedName("author")
    private String mAuthor;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("urlToImage")
    private String mUrlToImage;

    @SerializedName("publishedAt")
    private String mPublishedAt;

    /**
     * The author of the article.
     */
    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    /**
     * A description or preface for the article.
     */
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    /**
     * The headline or title of the article.
     */
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * The direct URL to the content page of the article.
     */
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    /**
     * The URL to a relevant image for the article.
     */
    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    /**
     * The best attempt at finding a date for the article, in UTC (+0).
     */
    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }
}
