package com.example.uniqueimagefinder.adapters;

import static java.util.Collections.emptyList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uniqueimagefinder.R;
import com.example.uniqueimagefinder.classes.SearchItem;
import com.example.uniqueimagefinder.databinding.RvItemBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context ctx;
    List<SearchItem> images = emptyList();
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        RvItemBinding bnd = RvItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(bnd);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder h, int p) {
        SearchItem image = images.get(p);
        ImageView imView = h.bnd.image;
        Glide.with(ctx).load(image.link).placeholder(R.drawable.load).into(imView);
        h.bnd.source.setText(image.title);
    }

    @Override
    public int getItemCount() {
        return  images.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final RvItemBinding bnd;
        public ImageViewHolder(RvItemBinding bnd) {
            super(bnd.getRoot());
            this.bnd = bnd;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setImages(List<SearchItem> items, Context ctx) {
        images = items;
        this.ctx = ctx;
        notifyDataSetChanged();
    }
}
