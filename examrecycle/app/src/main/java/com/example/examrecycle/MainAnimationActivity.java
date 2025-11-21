package com.example.examrecycle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAnimationActivity extends AppCompatActivity {

    private Button btnAddItem;
    private Button btnEditItem;
    private Button btnDeleteItem;
    private RecyclerView rvItems;

    private CustomAnimationAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);

        btnAddItem = findViewById(R.id.btn_add_item);
        btnEditItem = findViewById(R.id.btn_edit_item);
        btnDeleteItem = findViewById(R.id.btn_delete_item);
        rvItems = findViewById(R.id.rv_items);

        // Tạo dữ liệu ban đầu
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("item " + i);
        }

        adapter = new CustomAnimationAdapter(data);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setItemAnimator(new DefaultItemAnimator());

        // =======================
        // NÚT THÊM (ADD)
        // =======================
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextIndex = adapter.getItemCount();
                adapter.addItem("new item " + nextIndex);
                rvItems.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

        // =======================
        // NÚT SỬA (EDIT)
        // Sửa phần tử đầu tiên để thấy animation "Changed"
        // =======================
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getItemCount() == 0) return;
                adapter.replaceItem(0, "item changed at 0");
                rvItems.scrollToPosition(0);
            }
        });

        // =======================
        // NÚT XÓA (DELETE)
        // Xóa phần tử cuối để thấy animation "Removed"
        // =======================
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = adapter.getItemCount();
                if (count == 0) return;
                adapter.removeItem(count - 1);
            }
        });
    }
}
