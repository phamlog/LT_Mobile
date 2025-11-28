package com.example.recyclerview_indicator;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcIcon;
    private SearchView searchView;
    private ArrayList<IconModel> arrayList1;
    private IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcIcon = findViewById(R.id.rcIcon);
        searchView = findViewById(R.id.searchView);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });

        arrayList1 = new ArrayList<>();
        arrayList1.add(new IconModel(R.drawable.ic_voucher, "Mã giảm giá"));
        arrayList1.add(new IconModel(R.drawable.ic_shopee_food, "Shopee food"));
        arrayList1.add(new IconModel(R.drawable.ic_flash_sale, "Flash sale"));
        arrayList1.add(new IconModel(R.drawable.ic_freeship, "Miễn phí vận chuyển"));
        arrayList1.add(new IconModel(R.drawable.ic_cart, "Giỏ hàng"));

        int columns = calculateNoOfColumns(90); // auto-fit theo màn hình

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, columns, RecyclerView.VERTICAL, false);

        rcIcon.setLayoutManager(gridLayoutManager);
        iconAdapter = new IconAdapter(arrayList1, this);
        rcIcon.setAdapter(iconAdapter);
    }

    private int calculateNoOfColumns(float itemWidthDp) {
        float screenWidthDp = getResources().getDisplayMetrics().widthPixels /
                getResources().getDisplayMetrics().density;
        return (int) (screenWidthDp / itemWidthDp);
    }

    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();

        for (IconModel iconModel : arrayList1) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                list.add(iconModel);
            }
        }
        iconAdapter.setListenerList(list);
    }
}
