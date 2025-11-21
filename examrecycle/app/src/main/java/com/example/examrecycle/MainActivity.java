package com.example.examrecycle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvSongs;
    private SongAdapter mSongAdapter;
    private List<SongModel> mSongs;

    private Button btnAdd, btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // EdgeToEdge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        rvSongs = findViewById(R.id.rv_songs);
        btnAdd = findViewById(R.id.btn_add);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        // Tạo dữ liệu mẫu
        mSongs = new ArrayList<>();
        mSongs.add(new SongModel("60696", "NẾU EM CÒN TỒN TẠI",
                "Khi anh bắt đầu tự tin yêu là lúc anh tự thay", "Trịnh Đình Quang"));
        mSongs.add(new SongModel("60697", "NGỌC",
                "Có một nhiều thứ nhưng câu chuyện Em đâu riêng mình em biết", "Khắc Việt"));
        mSongs.add(new SongModel("60698", "THIÊN ĐƯỜNG",
                "Đâu có đau ta sai khi ở bên nhau Cố yêu thương", "Thiên Dũng"));

        // Tạo Adapter
        mSongAdapter = new SongAdapter(this, mSongs);
        rvSongs.setAdapter(mSongAdapter);

        // Layout Manager + Animator (quan trọng để có animation)
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSongs.setLayoutManager(linearLayoutManager);
        rvSongs.setItemAnimator(new DefaultItemAnimator());


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextIndex = mSongs.size();
                SongModel newSong = new SongModel(
                        "NEW" + nextIndex,
                        "BÀI HÁT MỚI " + nextIndex,
                        "Lời mới demo để test animation",
                        "Ca sĩ mới"
                );
                mSongs.add(newSong);
                // Gọi notifyItemInserted -> RecyclerView animate thêm item
                mSongAdapter.notifyItemInserted(nextIndex);
                rvSongs.scrollToPosition(nextIndex);
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSongs.isEmpty()) return;

                // Sửa bài đầu tiên cho dễ thấy
                SongModel first = mSongs.get(0);
                first.setmTitle("ĐÃ SỬA TIÊU ĐỀ");
                first.setmLyric("Đây là lời mới sau khi sửa để test animation Changed");

                // notifyItemChanged -> item nháy nhẹ (animation change)
                mSongAdapter.notifyItemChanged(0);
                rvSongs.scrollToPosition(0);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSongs.isEmpty()) return;

                int lastIndex = mSongs.size() - 1;
                mSongs.remove(lastIndex);
                // notifyItemRemoved -> item fade-out, list kéo lên
                mSongAdapter.notifyItemRemoved(lastIndex);
            }
        });
    }
}
