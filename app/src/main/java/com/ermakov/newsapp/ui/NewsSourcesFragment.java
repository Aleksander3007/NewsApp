package com.ermakov.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Фрагмент для отображения источников новостей одной из категорий.
 */
public class NewsSourcesFragment extends Fragment implements NewsSourceAdapter.OnClickHandler {

    public static final String ARG_CATEGORY = "ARG_CATEGORY";

    @BindView(R.id.rv_news_source) RecyclerView mNewsSourceRecyclerView;
    @BindView(R.id.pb_news_loading) ProgressBar mNewsLoadingProgressBar;

    private Unbinder mUnbinder;

    private List<NewsSource> mNewsSources = new ArrayList<>();
    private String category;

    public static NewsSourcesFragment newInstance(String category) {

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
                        else {
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                        mNewsLoadingProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<NewsSourceResponse> call, Throwable t) {
                        mNewsLoadingProgressBar.setVisibility(View.INVISIBLE);
                        t.printStackTrace();
                        Toast.makeText(getActivity(), "onFailure()", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(NewsSource newsSource) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(R.id.fragment_root, new NewsArticlesFragment())
                //        .hide(this)
                //        .add(new NewsArticlesFragment(), "art")
                .commit();
    }
}
