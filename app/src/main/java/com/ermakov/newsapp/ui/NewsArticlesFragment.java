package com.ermakov.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ermakov.newsapp.R;

/**
 * Фрагмент для отображения новостей одной из категорий определенного источника новостей.
 */
public class NewsArticlesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_articles, container, false);
    }
}
