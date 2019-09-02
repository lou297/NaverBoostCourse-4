package com.practice.mymovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.practice.mymovie.DataClass.MovieGallery;

import java.util.ArrayList;

public class MoviePhotosAdapter extends RecyclerView.Adapter<MoviePhotosAdapter.ViewHolder> {
    Context context;

    ArrayList<MovieGallery> thumbnailUrlList = new ArrayList<>();

    OnItemClickListener listener;

    public interface  OnItemClickListener {
        public void OnItemClick(ViewHolder holder, View view, int position);
    }

    public MoviePhotosAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return thumbnailUrlList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_gallery_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MovieGallery gallery = thumbnailUrlList.get(i);
        viewHolder.setImageView(gallery);

        viewHolder.setOnItemClickListener(listener);
    }

    public void addImage(MovieGallery url) {
        thumbnailUrlList.add(url);
    }

    public void addImages(ArrayList<MovieGallery> urls) {
        for(MovieGallery url : urls) {
            thumbnailUrlList.add(url);
        }
    }

    public MovieGallery getGallery(int position) {
        return thumbnailUrlList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        ImageView ivPlayVideo;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.ivThumbnail_recyclerView);
            ivPlayVideo = itemView.findViewById(R.id.ivPlayVideo_recyclerView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null) {
                        listener.OnItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public void setImageView(MovieGallery gallery) {
            Glide.with(context).load(gallery.getImageurl()).into(ivThumbnail);
            if(gallery.getVideoUrl() != null)
                ivPlayVideo.setVisibility(View.VISIBLE);
            else
                ivPlayVideo.setVisibility(View.GONE);
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {

        }
    }
}
