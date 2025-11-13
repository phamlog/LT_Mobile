package com.example.btap02;

import static com.example.btap02.R.*;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout bạn đang dùng

        // Lấy ImageView
        ImageView img1 = findViewById(R.id.imageView1);

        // Đổi ảnh bằng Java
        img1.setImageResource(R.drawable.kotlin);
    }
}