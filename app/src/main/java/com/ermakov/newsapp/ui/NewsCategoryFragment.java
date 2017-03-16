package com.ermakov.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ermakov.newsapp.NewsSource;
import com.ermakov.newsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Фрагмент для отображения новостей одной из категории.
 */
public class NewsCategoryFragment extends Fragment {

    public static final String TAG = NewsCategoryFragment.class.getSimpleName();

    public static final String ARG_CATEGORY = "ARG_CATEGORY";

    private List<NewsSource> mNewsSources = new ArrayList<>();
    private String category;

    public static NewsCategoryFragment newInstance(String category) {

        Log.d(TAG, "newInstance(): " + category);

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

        Log.d(TAG, "onCreateView(): " + category);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_root, NewsSourcesFragment.newInstance(category))
                .commit();

        return view;
    }
}
