package com.example.examrecycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Object> mObjects;

    public static final int TEXT  = 0;
    public static final int IMAGE = 1;
    public static final int USER  = 2;

    public CustomAdapter(Context context, List<Object> objects) {
        this.mContext = context;
        this.mObjects = objects;
    }

    // ===========================
    // 1) getItemViewType()
    // ===========================
    @Override
    public int getItemViewType(int position) {
        Object obj = mObjects.get(position);

        if (obj instanceof String) {
            return TEXT;
        } else if (obj instanceof Integer) {
            return IMAGE;
        } else if (obj instanceof UserModel) {
            return USER;
        }
        return -1;
    }


    // ===========================
    // 2) onCreateViewHolder()
    // ===========================
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(mContext);

        switch (viewType) {
            case TEXT: {
                View itemView0 = li.inflate(R.layout.row_text, parent, false);
                return new TextViewHolder(itemView0);
            }
            case IMAGE: {
                View itemView1 = li.inflate(R.layout.row_image, parent, false);
                return new ImageViewHolder(itemView1);
            }
            case USER: {
                View itemView2 = li.inflate(R.layout.row_user, parent, false);
                return new UserViewHolder(itemView2);
            }
        }
        return null;
    }


    // ===========================
    // 3) onBindViewHolder()
    // ===========================
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case TEXT:
                TextViewHolder textVH = (TextViewHolder) holder;
                textVH.tvText.setText(mObjects.get(position).toString());
                break;

            case IMAGE:
                ImageViewHolder imageVH = (ImageViewHolder) holder;
                imageVH.imvImage.setImageResource((int) mObjects.get(position));
                break;

            case USER:
                UserModel user = (UserModel) mObjects.get(position);
                UserViewHolder userVH = (UserViewHolder) holder;
                userVH.tvName.setText(user.getName());
                userVH.tvAddress.setText(user.getAddress());
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mObjects.size();
    }


    // ===========================
    //  VIEW HOLDERS (3 loại)
    // ===========================

    // 1) TEXT HOLDER
    public class TextViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);

            itemView.setOnClickListener(v ->
                    Toast.makeText(mContext,
                            mObjects.get(getAdapterPosition()).toString(),
                            Toast.LENGTH_SHORT).show());
        }
    }


    // 2) IMAGE HOLDER
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imvImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imv_image);

            itemView.setOnClickListener(v ->
                    Toast.makeText(mContext,
                            mObjects.get(getAdapterPosition()).toString(),
                            Toast.LENGTH_SHORT).show());
        }
    }


    // 3) USER HOLDER
    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(v ->
                    Toast.makeText(mContext,
                            mObjects.get(getAdapterPosition()).toString(),
                            Toast.LENGTH_SHORT).show());
        }
    }
}
