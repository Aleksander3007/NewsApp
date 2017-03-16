package com.ermakov.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Адаптер для работы с источниками новостей.
 */
public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.ViewHolder> {

    List<NewsSource> mNewsSources;

    public NewsSourceAdapter(List<NewsSource> newsSources) {
        mNewsSources = newsSources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_source_item, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mNameTextView;
        @BindView(R.id.tv_description) TextView mDescriptionTextView;
        @BindView(R.id.iv_logo) ImageView mLogoImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
