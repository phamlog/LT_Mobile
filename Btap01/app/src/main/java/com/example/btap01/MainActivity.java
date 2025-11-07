package com.example.btap01;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtNumbers, edtString;
    private TextView tvResult;
    private Button btnProcessNumbers, btnReverseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ẩn ActionBar nếu muốn
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Ánh xạ
        edtNumbers = findViewById(R.id.edtNumbers);
        edtString = findViewById(R.id.edtString);
        tvResult = findViewById(R.id.tvResult);
        btnProcessNumbers = findViewById(R.id.btnProcessNumbers);
        btnReverseString = findViewById(R.id.btnReverseString);

        // Bài 4: nhập mảng số -> log chẵn/lẻ
        btnProcessNumbers.setOnClickListener(v -> {
            String input = edtNumbers.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tách theo dấu phẩy HOẶC khoảng trắng
            String[] parts = input.split("[,\\s]+");

            ArrayList<Integer> chan = new ArrayList<>();
            ArrayList<Integer> le = new ArrayList<>();

            for (String s : parts) {
                try {
                    int n = Integer.parseInt(s);
                    if (n % 2 == 0) chan.add(n);
                    else le.add(n);
                } catch (NumberFormatException e) {
                    Log.w("BTAP01", "Bỏ qua phần tử không phải số: " + s);
                }
            }

            // ✅ Chỉ in ra Logcat, KHÔNG set TextView
            Log.d("SO_CHAN", chan.toString());
            Log.d("SO_LE", le.toString());

            // (tuỳ chọn) xóa kết quả cũ trên màn hình nếu có
            // tvResult.setText("");
        });



        // Bài 5: đảo chuỗi theo TỪ + IN HOA, show TextView & Toast
        btnReverseString.setOnClickListener(v -> {
            String s = edtString.getText().toString().trim();
            if (s.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập chuỗi!", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> words = Arrays.asList(s.split("\\s+"));
            Collections.reverse(words);
            String result = String.join(" ", words).toUpperCase();

            tvResult.setText(result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        });
    }
}
