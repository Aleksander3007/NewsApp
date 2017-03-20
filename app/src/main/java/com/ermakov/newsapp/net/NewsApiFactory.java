package com.ermakov.newsapp.net;


import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс отвечает за работу с объектами для News API.
 */
public abstract class NewsApiFactory {

    private static final int CONNECT_TIMEOUT = 10;
    private static final int WRITE_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;

    public static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY_NAME = "apiKey";
    private static final String API_KEY_VALUE = "b516fc8be8ba4e57ace379b1fa35e897";

    private static OkHttpClient.Builder sHttpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

    private static HttpLoggingInterceptor sLogging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.NONE);

    static {
        // Добавляем авторизацию в запрос по ключу.
        sHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        });

        sHttpClient.addInterceptor(sLogging);
    }

    @NonNull
    public static NewsApiService createNewsApiService() {
        return createRetrofit().create(NewsApiService.class);
    }

    @NonNull
    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(sHttpClient.build())
                .build();
    }
}
