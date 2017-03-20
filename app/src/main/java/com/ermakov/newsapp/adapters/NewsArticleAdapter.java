package com.ermakov.newsapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ermakov.newsapp.R;
import com.ermakov.newsapp.models.NewsArticle;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Адаптер для работы со статьями.
 */
public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>{

    /**
     * Интерфейс, который определяет методы для обработки нажатий на элементы.
     */
    public interface OnArticleClickListener {
        /**
         * Обработка нажатия на элемент списка.
         * @param newsArticle новостная статья.
         */
        void onArticleClick(NewsArticle newsArticle);
    }
    private final NewsArticleAdapter.OnArticleClickListener mArticleClickListener;

    private List<NewsArticle> mNewsArticles;

    public NewsArticleAdapter(List<NewsArticle> newsArticles,
                              OnArticleClickListener articleClickListener) {
        this.mNewsArticles = newsArticles;
        this.mArticleClickListener = articleClickListener;
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
        holder.mPublishedDateTextView.setText(
                formatPublishedDateTime(newsArticle.getPublishedAt()));

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title) TextView mTitleTextView;
        @BindView(R.id.tv_description) TextView mDescriptionTextView;
        @BindView(R.id.image_view) ImageView mImageView;
        @BindView(R.id.tv_published_date) TextView mPublishedDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mArticleClickListener != null)
                mArticleClickListener.onArticleClick(mNewsArticles.get(getAdapterPosition()));
        }
    }

    /**
     * Конвертируем дату и время публикации из формата, которое предоставляет нам API в наш  отображаемый формат.
     * @param publishedAt дата и время публикации формата API.
     * @return дата и время публикации отображаемого формата.
     */
    private String formatPublishedDateTime(String publishedAt) {
        SimpleDateFormat siteDateFormat = new SimpleDateFormat(NewsArticle.FORMAT_PUBLISHED_AT);
        SimpleDateFormat publishedDateFormat = new SimpleDateFormat("dd MMMM',' hh:mm");
        try {
            // Дата и время могут быть получены с сервера в одной временной зоне,
            // а выводит необходимо всегда в местной.
            siteDateFormat.setTimeZone(TimeZone.getTimeZone(NewsArticle.TIMEZONE_PUBLISHED_AT));
            publishedDateFormat.setTimeZone(TimeZone.getDefault());

            Date publishedDate = siteDateFormat.parse(publishedAt);
            return publishedDateFormat.format(publishedDate);
        }
        catch (ParseException pex) {
            // Если не удалось распарсить, значит дата и время пришло в неправильном формате,
            // поэтому просто не указываем дату публикации.
            return "";
        }
    }

}
