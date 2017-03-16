package com.ermakov.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ermakov.newsapp.NewsSource;
import com.ermakov.newsapp.NewsSourceAdapter;
import com.ermakov.newsapp.NewsSourcesLoader;
import com.ermakov.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Фрагмент для отображения источников новостей одной из категорий.
 */
public class NewsSourcesFragment extends Fragment implements NewsSourceAdapter.OnClickHandler,
        LoaderManager.LoaderCallbacks<List<NewsSource>> {

    public static final String TAG = NewsSourcesFragment.class.getSimpleName();

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    private static final int NEWS_SOURCE_LOADER = 1;

    @BindView(R.id.rv_news_source) RecyclerView mNewsSourceRecyclerView;
    @BindView(R.id.pb_news_loading) ProgressBar mNewsLoadingProgressBar;

    private Unbinder mUnbinder;

    private List<NewsSource> mNewsSources = new ArrayList<>();
    private String mCategory;

    public static NewsSourcesFragment newInstance(String category) {

        Log.d(TAG, "newInstance(): " + category);

        NewsSourcesFragment fragment = new NewsSourcesFragment();

        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_sources, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mCategory = getArguments().getString(ARG_CATEGORY);
        if (mCategory == null)
            throw new IllegalArgumentException("Необходимо задать параметр mCategory. " +
                    "Используй для создания NewsSourcesFragment его статический метод newInstance()");

        Log.d(TAG, "onCreateView(): " + mCategory);

        NewsSourceAdapter newsSourceAdapter = new NewsSourceAdapter(mNewsSources, this);
        mNewsSourceRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        mNewsSourceRecyclerView.setAdapter(newsSourceAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLoaderManager().restartLoader(NEWS_SOURCE_LOADER, null, this);
        Log.d(TAG, "onViewCreated(): " + mCategory);
    }

    @Override
    public void onItemClick(NewsSource newsSource) {
        NewsArticlesFragment newsArticlesFragment =
                NewsArticlesFragment.newInstance(newsSource.getId());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.fragment_root, newsArticlesFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public Loader<List<NewsSource>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case NEWS_SOURCE_LOADER:
                mNewsLoadingProgressBar.setVisibility(View.VISIBLE);
                Log.d("MyTag", "onCreateLoader(): " + mCategory);
                return new NewsSourcesLoader(getActivity(), mCategory, NewsSource.LANGUAGE_ENGLISH);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<NewsSource>> loader, List<NewsSource> data) {
        mNewsLoadingProgressBar.setVisibility(View.INVISIBLE);
        if (data != null) {
            mNewsSources.clear();
            mNewsSources.addAll(data);
            mNewsSourceRecyclerView.getAdapter().notifyDataSetChanged();
        }
        Log.d(TAG, "onLoadFinished(): " + mCategory);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsSource>> loader) {

    }
}
