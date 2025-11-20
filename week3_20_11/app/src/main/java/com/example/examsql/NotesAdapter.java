package com.example.examsql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<NotesModel> list;
    private NoteClickListener listener;

    public interface NoteClickListener {
        void onEdit(NotesModel note);
        void onDelete(NotesModel note);
    }

    public NotesAdapter(Context context, int layout, List<NotesModel> list, NoteClickListener listener) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    static class ViewHolder {
        TextView textViewNote;
        ImageView imageViewEdit, imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.textViewNote = convertView.findViewById(R.id.textViewNote);
            holder.imageViewEdit = convertView.findViewById(R.id.imageViewEdit);
            holder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NotesModel note = list.get(position);

        holder.textViewNote.setText(note.getNameNote());

        holder.imageViewEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(note);
        });

        holder.imageViewDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(note);
        });

        return convertView;
    }
}
