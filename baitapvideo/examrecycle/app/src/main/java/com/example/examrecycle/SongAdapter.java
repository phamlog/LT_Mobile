package com.example.examrecycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context mContext;
    private List<SongModel> mSongs;

    public SongAdapter(Context context, List<SongModel> songs) {
        this.mContext = context;
        this.mSongs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        SongModel song = mSongs.get(position);

        // GÁN DỮ LIỆU CHO VIEW
        holder.tvCode.setText(song.getmCode());
        holder.tvTitle.setText(song.getmTitle());
        holder.tvLyric.setText(song.getmLyric());
        holder.tvArtist.setText(song.getmArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    // =============================
    //      VIEW HOLDER
    // =============================
    public class SongViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode, tvTitle, tvLyric, tvArtist;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            // ÁNH XẠ VIEW (theo đúng id trong row_item_song.xml)
            tvCode = itemView.findViewById(R.id.tv_code);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLyric = itemView.findViewById(R.id.tv_lyric);
            tvArtist = itemView.findViewById(R.id.tv_artist);

            // XỬ LÝ SỰ KIỆN CLICK ITEM
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        SongModel clickedSong = mSongs.get(position);

                        Toast.makeText(mContext,
                                "Bạn chọn: " + clickedSong.getmTitle(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
