package com.ermakov.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ermakov.newsapp.NewsApiFactory;
import com.ermakov.newsapp.NewsApiService;
import com.ermakov.newsapp.NewsSource;
import com.ermakov.newsapp.NewsSourceAdapter;
import com.ermakov.newsapp.NewsSourceResponse;
import com.ermakov.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Фрагмент для отображения новостей категории General.
 */
public class NewsCategoryFragment extends Fragment {

    public static final String ARG_CATEGORY = "ARG_CATEGORY";

    @BindView(R.id.rv_news_source) RecyclerView mNewsSourceRecyclerView;
    @BindView(R.id.pb_news_loading) ProgressBar mNewsLoadingProgressBar;

    private Unbinder mUnbinder;

    private List<NewsSource> mNewsSources = new ArrayList<>();
    private String category;

    public static NewsCategoryFragment newInstance(String category) {

        NewsCategoryFragment fragment = new NewsCategoryFragment();

        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_category, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        category = getArguments().getString(ARG_CATEGORY);
        if (category == null)
            throw new IllegalArgumentException("Необходимо задать параметр category. " +
                    "Используй для создания NewsCategoryFragment его статический метод newInstance()");

        NewsSourceAdapter newsSourceAdapter = new NewsSourceAdapter(mNewsSources);

        mNewsSourceRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mNewsSourceRecyclerView.setAdapter(newsSourceAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNewsLoadingProgressBar.setVisibility(View.VISIBLE);

        // TODO: Переделать на Loader.
        NewsApiService newsApiService = NewsApiFactory.createNewsApiService();
        newsApiService.getNewsSource(category, NewsSource.LANGUAGE_ENGLISH)
                .enqueue(new Callback<NewsSourceResponse>() {
            @Override
            public void onResponse(Call<NewsSourceResponse> call, Response<NewsSourceResponse> response) {
                if (response.isSuccessful()) {
                    mNewsSources.clear();
                    mNewsSources.addAll(response.body().getSources());
                    mNewsSourceRecyclerView.getAdapter().notifyDataSetChanged();
                }
                mNewsLoadingProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<NewsSourceResponse> call, Throwable t) {
                mNewsLoadingProgressBar.setVisibility(View.INVISIBLE);
                t.printStackTrace();
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
