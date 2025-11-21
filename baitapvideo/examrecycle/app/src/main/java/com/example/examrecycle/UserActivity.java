package com.example.examrecycle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private RecyclerView rvMultipleViewType;
    private List<Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        rvMultipleViewType = findViewById(R.id.rv_multiple_view_type);

        // ===========================
        // 1) TẠO DỮ LIỆU
        // ===========================
        mData = new ArrayList<>();

        mData.add(new UserModel("Nguyen Van Nghia", "Quan 11"));

        mData.add("Text 1");

        mData.add(new UserModel("Nguyen Hoang Minh", "Quan 3"));

        mData.add("Text 2");

        mData.add(R.drawable.avatar_3);

        mData.add(new UserModel("Pham Nguyen Tam Phu", "Quan 10"));

        mData.add("Text 3");

        mData.add(new UserModel("Tran Van Phuc", "Quan 1"));

        mData.add(R.drawable.avatar_4);

        // ===========================
        // 2) GẮN ADAPTER
        // ===========================
        CustomAdapter adapter = new CustomAdapter(this, mData);
        rvMultipleViewType.setAdapter(adapter);

        rvMultipleViewType.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
    }
}
