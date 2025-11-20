package com.example.week3_18_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonhocAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MonHoc> monHocList;

    public MonhocAdapter(Context context, int layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }

    @Override
    public int getCount() {
        return monHocList.size();
    }

    @Override
    public Object getItem(int position) {
        return monHocList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // ViewHolder
    private static class ViewHolder {
        TextView textName, textDesc;
        ImageView imagePic;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.textName = view.findViewById(R.id.textName);
            holder.textDesc = view.findViewById(R.id.textDesc);
            holder.imagePic = view.findViewById(R.id.imagePic);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MonHoc monHoc = monHocList.get(i);
        holder.textName.setText(monHoc.getName());
        holder.textDesc.setText(monHoc.getDesc());
        holder.imagePic.setImageResource(monHoc.getPic());

        return view;
    }
}
