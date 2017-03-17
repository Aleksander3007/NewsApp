package com.ermakov.newsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Адаптер для работы со статьями.
 */
public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>{

    private List<NewsArticle> mNewsArticles;

    public NewsArticleAdapter(List<NewsArticle> newsArticles) {
        this.mNewsArticles = newsArticles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsArticle newsArticle = mNewsArticles.get(position);
        holder.mTitleTextView.setText(newsArticle.getTitle());
        holder.mDescriptionTextView.setText(newsArticle.getDescription());
        Picasso.with(holder.itemView.getContext())
                .load(newsArticle.getUrlToImage())
                .fit()
                .centerInside()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mNewsArticles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title) TextView mTitleTextView;
        @BindView(R.id.tv_description) TextView mDescriptionTextView;
        @BindView(R.id.image_view) ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
