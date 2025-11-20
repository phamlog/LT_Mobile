package com.example.week3_18_11;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText1;
    Button btnNhap, btnCapNhat;

    ArrayList<MonHoc> arrayList;
    MonhocAdapter adapter;

    int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AnhXa();
        KhoiTaoDuLieu();
        KhoiTaoAdapter();
        BatSuKien();
    }

    private void AnhXa() {
        listView = findViewById(R.id.listview1);
        editText1 = findViewById(R.id.editText1);
        btnNhap = findViewById(R.id.btnNhap);
        btnCapNhat = findViewById(R.id.btnCapNhat);
    }

    private void KhoiTaoDuLieu() {
        arrayList = new ArrayList<>();
        int img = R.drawable.ic_launcher_foreground;

        arrayList.add(new MonHoc("Java", "Lập trình Java cơ bản", img));
        arrayList.add(new MonHoc("C#", "Lập trình C# Winform", img));
        arrayList.add(new MonHoc("PHP", "Lập trình web PHP", img));
        arrayList.add(new MonHoc("Kotlin", "Lập trình Android Kotlin", img));
        arrayList.add(new MonHoc("Dart", "Lập trình Flutter với Dart", img));
    }

    private void KhoiTaoAdapter() {
        adapter = new MonhocAdapter(
                MainActivity.this,
                R.layout.row_monhoc,
                arrayList
        );
        listView.setAdapter(adapter);
    }

    private void BatSuKien() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                vitri = i;
                MonHoc monHoc = arrayList.get(i);
                editText1.setText(monHoc.getName());
                Toast.makeText(MainActivity.this,
                        "Bạn chọn: " + monHoc.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                MonHoc monHoc = arrayList.get(i);
                arrayList.remove(i);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,
                        "Đã xóa: " + monHoc.getName(),
                        Toast.LENGTH_SHORT).show();


                if (vitri == i) {
                    vitri = -1;
                    editText1.setText("");
                }
                return true;
            }
        });


        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Vui lòng nhập tên môn",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                arrayList.add(new MonHoc(name, "Môn mới thêm", R.drawable.ic_launcher_foreground));
                adapter.notifyDataSetChanged();
                editText1.setText("");
            }
        });


        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitri == -1) {
                    Toast.makeText(MainActivity.this,
                            "Chưa chọn dòng để cập nhật",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = editText1.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Vui lòng nhập tên môn",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                MonHoc monHoc = arrayList.get(vitri);
                monHoc.setName(name);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,
                        "Đã cập nhật: " + name,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
