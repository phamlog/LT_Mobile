package com.example.circleindicator3_viewpager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImagesViewPager2Adapter
        extends RecyclerView.Adapter<ImagesViewPager2Adapter.ImagesViewHolder> {

    private List<Images> imagesList;

    public ImagesViewPager2Adapter(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        Images images = imagesList.get(position);
        if (images == null) {
            return;
        }
        holder.imageView.setImageResource(images.getImagesId());
    }

    @Override
    public int getItemCount() {
        if (imagesList != null) {
            return imagesList.size();
        }
        return 0;
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }
}
