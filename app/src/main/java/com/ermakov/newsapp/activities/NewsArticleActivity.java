package com.ermakov.newsapp.activities;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ermakov.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity для отображения новостной статьи.
 */
public class NewsArticleActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE_URL = "com.ermakov.newsapp.EXTRA_ARTICLE_URL";

    @BindView(R.id.wv_article) WebView mArticleWebView;
    @BindView(R.id.pb_article_loading) ProgressBar mArticleLoadingProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_article);
        ButterKnife.bind(this);

        final String articleUrl = getIntent().getExtras().getString(EXTRA_ARTICLE_URL);

        mArticleWebView.setWebViewClient(new WebViewClient() {

            // Переопределяем для того, чтобы страница открывалась в нашем приложении.
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }

            // Переопределяем для того, чтобы страница открывалась в нашем приложении.
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                mArticleLoadingProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                mArticleLoadingProgressBar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }
        });

        mArticleWebView.loadUrl(articleUrl);
    }

    @Override
    public void onBackPressed()
    {
        if (mArticleWebView.canGoBack()) {
            mArticleWebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
