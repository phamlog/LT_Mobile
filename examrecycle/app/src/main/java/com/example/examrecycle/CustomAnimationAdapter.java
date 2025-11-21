package com.example.examrecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAnimationAdapter
        extends RecyclerView.Adapter<CustomAnimationAdapter.ViewHolder> {

    private List<String> mDatas;

    public CustomAnimationAdapter(List<String> data) {
        mDatas = data;
    }

    // onCreateViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.row_animation, parent, false);
        return new ViewHolder(itemView);
    }

    // onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mDatas.get(position);
        holder.tvItem.setText(item);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    // add cuối list
    public void addItem(String item) {
        mDatas.add(item);
        notifyItemInserted(mDatas.size() - 1);
    }

    // add theo vị trí
    public void addItem(int position, String item) {
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    // remove theo vị trí
    public void removeItem(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    // remove theo giá trị
    public void removeItem(String item) {
        int index = mDatas.indexOf(item);
        if (index >= 0) {
            mDatas.remove(index);
            notifyItemRemoved(index);
        }
    }

    // replace
    public void replaceItem(int position, String item) {
        if (position < 0 || position >= mDatas.size()) return;
        mDatas.set(position, item);
        notifyItemChanged(position);
    }


    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tv_item);

            // LONG CLICK = remove
            itemView.setOnLongClickListener(view -> {
                removeItem(getAdapterPosition());
                Toast.makeText(
                        itemView.getContext(),
                        "Removed Animation",
                        Toast.LENGTH_SHORT
                ).show();
                return false;
            });

            // CLICK = replace item
            itemView.setOnClickListener(view -> {
                replaceItem(getAdapterPosition(), "item changed");
                Toast.makeText(
                        itemView.getContext(),
                        "Changed Animation",
                        Toast.LENGTH_SHORT
                ).show();
            });
        }
    }
}
