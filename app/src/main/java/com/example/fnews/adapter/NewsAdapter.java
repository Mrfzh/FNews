package com.example.fnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fnews.R;
import com.example.fnews.entity.NewsData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author fengzhaohao
 * @date 2020/11/3
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<NewsData> mNewsList;
    private NewsListener mListener;

    public NewsAdapter(Context context, List<NewsData> newsList, NewsListener listener) {
        mContext = context;
        mNewsList = newsList;
        mListener = listener;
    }

    public interface NewsListener {
        void onClickItem(String url);
    }

    @Override public void onClick(View view) {

    }

    @NonNull @Override public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news, null));
    }

    @Override public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.title.setText(mNewsList.get(position).getTitle());
        holder.src.setText(mNewsList.get(position).getSrc());
        holder.time.setText(getTime(mNewsList.get(position).getTime()));

//        mNovelCoverIv = findViewById(R.id.iv_novel_intro_novel_cover);
//        Glide.with(this)
//                .load(mNovelSourceData.getCover())
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.cover_place_holder)
//                        .error(R.drawable.cover_error))
//                .into(mNovelCoverIv);

        Glide.with(mContext)
                .load(mNewsList.get(position).getPic())
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mListener.onClickItem(mNewsList.get(position).getUrl());
            }
        });
    }

    @Override public int getItemCount() {
        return mNewsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView src;
        TextView time;
        ImageView pic;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_news_title);
            src = itemView.findViewById(R.id.tv_item_news_src);
            time = itemView.findViewById(R.id.tv_item_news_time);
            pic = itemView.findViewById(R.id.iv_item_news_pic);
        }
    }

    private String getTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(time);
            if (date == null) {
                return "";
            }
            long diff = System.currentTimeMillis() - date.getTime();
            long diffHours = diff / (60 * 60 * 1000) % 24;
            int hours = (int) (Math.abs(diffHours) + 1);

            return hours + "小时前";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
