package com.ermakov.newsapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ermakov.newsapp.models.NewsArticle;
import com.ermakov.newsapp.adapters.NewsArticleAdapter;
import com.ermakov.newsapp.loaders.NewsArticlesLoader;
import com.ermakov.newsapp.R;
import com.ermakov.newsapp.activities.NewsArticleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Фрагмент для отображения новостей одной из категорий определенного источника новостей.
 */
public class NewsArticlesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<NewsArticle>>,
        NewsArticleAdapter.OnArticleClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = NewsArticlesFragment.class.getSimpleName();

    public static final String ARG_SOURCE = "ARG_SOURCE";
    private static final int NEWS_ARTICLE_LOADER = 1;

    @BindView(R.id.rv_news_articles) RecyclerView mNewsArticleRecyclerView;
    @BindView(R.id.srl_news_articles) SwipeRefreshLayout mNewsArticlesSwipeRefreshLayout;
    @BindView(R.id.l_error) View mErrorView;

    private Unbinder mUnbinder;

    private List<NewsArticle> mNewsArticles = new ArrayList<>();
    private String mSource;

    public static NewsArticlesFragment newInstance(String source) {
        NewsArticlesFragment fragment = new NewsArticlesFragment();

        Bundle args = new Bundle();
        args.putString(ARG_SOURCE, source);
        fragment.setArguments(args);

        Log.d(TAG, "newInstance()");

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_articles, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mSource = getArguments().getString(ARG_SOURCE);
        }
        else
            throw new IllegalArgumentException("Необходимо задать параметр mCategory. " +
                    "Используй для создания NewsArticlesFragment его статический метод newInstance()");

        NewsArticleAdapter newsArticleAdapter = new NewsArticleAdapter(mNewsArticles, this);
        mNewsArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsArticleRecyclerView.setAdapter(newsArticleAdapter);

        mNewsArticlesSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mNewsArticlesSwipeRefreshLayout.setOnRefreshListener(this);

        Log.d(TAG, "onCreateView()");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLoaderManager().restartLoader(NEWS_ARTICLE_LOADER, null, this);

        Log.d(TAG, "onViewCreated()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case NEWS_ARTICLE_LOADER:
                Log.d(TAG, "onCreateLoader(): " + mSource);
                return new NewsArticlesLoader(getActivity(), mSource);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> data) {
        mNewsArticlesSwipeRefreshLayout.setRefreshing(false);
        if (data != null) {
            mNewsArticles.clear();
            mNewsArticles.addAll(data);
            mNewsArticleRecyclerView.getAdapter().notifyDataSetChanged();
        }
        else {
            mNewsArticles.clear();
            mNewsArticleRecyclerView.getAdapter().notifyDataSetChanged();
            mErrorView.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onLoadFinished(): " + mSource);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {

    }

    @Override
    public void onArticleClick(NewsArticle newsArticle)
    {
        Intent intent = new Intent(getActivity(), NewsArticleActivity.class);
        intent.putExtra(NewsArticleActivity.EXTRA_ARTICLE_URL, newsArticle.getUrl());
        startActivity(intent);
    }

    @OnClick(R.id.btn_connect)
    public void onRetryLoadDataClick() {
        loadData();
        Log.d(TAG, "onConnectClick(): " + mSource);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "Refresh is started: " + mSource);
        loadData();
    }

    private void loadData() {
        mNewsArticlesSwipeRefreshLayout.setRefreshing(true);
        mErrorView.setVisibility(View.INVISIBLE);
        getLoaderManager().restartLoader(NEWS_ARTICLE_LOADER, null, NewsArticlesFragment.this);
    }
}
