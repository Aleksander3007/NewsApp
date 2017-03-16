package com.ermakov.newsapp.ui;

import android.app.Activity;
import android.content.Context;
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
 * Фрагмент для отображения новостей одной из категории.
 */
public class NewsCategoryFragment extends Fragment {

    public static final String ARG_CATEGORY = "ARG_CATEGORY";

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

        category = getArguments().getString(ARG_CATEGORY);
        if (category == null)
            throw new IllegalArgumentException("Необходимо задать параметр category. " +
                    "Используй для создания NewsCategoryFragment его статический метод newInstance()");

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_root, NewsSourcesFragment.newInstance(category))
                .commit();

        return view;
    }
}
