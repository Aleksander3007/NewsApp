package com.ermakov.newsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Источники новостей.
 */
public class NewsSource {
    public static final String CATEGORY_BUSINESS = "business";
    public static final String CATEGORY_ENTERTAINMENT = "entertainment";
    public static final String CATEGORY_GAMING = "gaming";
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_MUSIC = "music";
    public static final String CATEGORY_SCIENCE_AND_NATURE = "science-and-nature";
    public static final String CATEGORY_SPORT = "sport";
    public static final String CATEGORY_TECHNOLOGY = "technology";

    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_GERMAN = "de";
    public static final String LANGUAGE_FRENCH = "fr";

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("language")
    private String mLanguage;

    @SerializedName("country")
    private String mCountry;

    @SerializedName("urlsToLogos")
    private NewsSource.Logo mUrlsToLogos;

    @SerializedName("sortBysAvailable")
    private List<String> mSortBysAvailable;

    /**
     * The unique identifier for the news source.
     * This is needed when querying the /articles endpoint to retrieve article metadata.
     */
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    /**
     * The display-friendly name of the news source.
     */
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    /**
     * A brief description of the news source and what area they specialize in.
     */
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    /**
     * The base URL or homepage of the source.
     */
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    /**
     * The topic category that the source focuses on.
     */
    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    /**
     * The 2-letter ISO-639-1 code for the language that the source is written in.
     */
    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    /**
     * The 2-letter ISO 3166-1 code of the country that the source mainly focuses on.
     */
    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    /**
     * The available headline lists for the news source.
     */
    public List<String> getSortBysAvailable() {
        return mSortBysAvailable;
    }

    public void setSortBysAvailable(List<String> sortBysAvailable) {
        mSortBysAvailable = sortBysAvailable;
    }

    /**
     * An object containing URLs to the source's logo.
     */
    public Logo getUrlsToLogos() {
        return mUrlsToLogos;
    }

    public void setUrlsToLogos(Logo urlsToLogos) {
        mUrlsToLogos = urlsToLogos;
    }

    public static class Logo {

        @SerializedName("small")
        private String mSmall;

        @SerializedName("medium")
        private String mMedium;

        @SerializedName("large")
        private String mLarge;

        /**
         * The URL to a small (width 200px) image of the source's logo.
         */
        public String getSmall() {
            return mSmall;
        }

        public void setSmall(String small) {
            mSmall = small;
        }

        /**
         * The URL to a medium (width 400px) image of the source's logo.
         */
        public String getMedium() {
            return mMedium;
        }

        public void setMedium(String medium) {
            mMedium = medium;
        }

        /**
         * The URL to a large (width 600px) image of the source's logo.
         */
        public String getLarge() {
            return mLarge;
        }

        public void setLarge(String large) {
            mLarge = large;
        }
    }
}
