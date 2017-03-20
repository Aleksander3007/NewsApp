package com.ermakov.newsapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ermakov.newsapp.R;
import com.ermakov.newsapp.models.NewsSource;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Адаптер для работы с источниками новостей.
 */
public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.ViewHolder> {

    private List<NewsSource> mNewsSources;

    /**
     * Интерфейс, который определяет методы для обработки нажатий на элементы.
     */
    public interface OnClickHandler {
        /**
         * Обработка нажатия на элемент списка.
         * @param newsSource данные источника новостей.
         */
        void onItemClick(NewsSource newsSource);
    }
    private final OnClickHandler mOnClickHandler;

    /**
     * Конструктор.
     * @param newsSources массив с источниками новостей.
     * @param clickHandler обработчик нажатий на элементы списка.
     */
    public NewsSourceAdapter(List<NewsSource> newsSources, OnClickHandler clickHandler) {
        mNewsSources = newsSources;
        mOnClickHandler = clickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_source, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsSource newsSource = mNewsSources.get(position);
        holder.mNameTextView.setText(newsSource.getName());
        holder.mDescriptionTextView.setText(newsSource.getDescription());
        Picasso.with(holder.itemView.getContext())
                .load(newsSource.getUrlsToLogos().getSmall())
                .into(holder.mLogoImageView);
    }

    @Override
    public int getItemCount() {
        return (mNewsSources != null) ? mNewsSources.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_description) TextView mDescriptionTextView;
        @BindView(R.id.iv_logo) ImageView mLogoImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickHandler != null)
                mOnClickHandler.onItemClick(mNewsSources.get(getAdapterPosition()));
        }
    }
}
