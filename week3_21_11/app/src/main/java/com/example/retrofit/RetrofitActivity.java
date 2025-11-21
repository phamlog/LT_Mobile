package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    private RecyclerView rcCate;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);


        anhXa();


        setupRecyclerView();


        fetchCategories();
    }


    private void anhXa() {
        rcCate = findViewById(R.id.rc_category);
    }


    private void setupRecyclerView() {
        // Sử dụng context của Activity hiện tại (RetrofitActivity)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcCate.setLayoutManager(layoutManager);
        rcCate.setHasFixedSize(true); // Tối ưu hóa hiệu suất nếu kích thước item không đổi
    }


    private void fetchCategories() {
        // Lấy instance của APIService từ RetrofitClient
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);


        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();


                    categoryAdapter = new CategoryAdapter(RetrofitActivity.this, categoryList);

                    rcCate.setAdapter(categoryAdapter);

                } else {

                    Log.e("API_ERROR", "Lỗi khi gọi API: " + response.code());
                    Toast.makeText(RetrofitActivity.this, "Không thể lấy dữ liệu: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

                Log.e("API_FAILURE", "Lỗi onFailure: " + t.getMessage());
                Toast.makeText(RetrofitActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
