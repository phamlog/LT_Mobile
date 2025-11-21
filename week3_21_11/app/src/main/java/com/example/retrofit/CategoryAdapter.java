package com.example.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Cần thêm thư viện Glide

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final Context context;
    private final List<Category> array;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        if (category == null) return;

        holder.tvCategoryName.setText(category.getName());

        String imageUrl = category.getImageUrl();

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            holder.ivCategoryImage.setImageResource(R.drawable.ic_launcher_background);
        } else {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.ivCategoryImage);
        }
    }


    @Override
    public int getItemCount() {
        return array != null ? array.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategoryImage;
        TextView tvCategoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.image_cate);
            tvCategoryName = itemView.findViewById(R.id.tvNameCategory);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Toast.makeText(context, "Bạn đã chọn: " + array.get(pos).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
