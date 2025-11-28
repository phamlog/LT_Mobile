package com.example.fragment_tablayout_viewpager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.fragment_tablayout_viewpager2.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPager2Adapter viewPager2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setSupportActionBar(binding.toolBar);

        // FAB
        binding.fabAction.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG).show()
        );

        // Tabs
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Xác nhận"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lấy hàng"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đang giao"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đánh giá"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Hủy"));

        // ViewPager2
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager2Adapter = new ViewPager2Adapter(fragmentManager, getLifecycle());
        binding.viewPager2.setAdapter(viewPager2Adapter);

        // Tab → ViewPager2
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
                changeFabIcon(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // ViewPager2 → Tab
        binding.viewPager2.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        binding.tabLayout.selectTab(
                                binding.tabLayout.getTabAt(position)
                        );
                    }
                }
        );
    }

    private void changeFabIcon(final int index) {

        binding.fabAction.hide();

        new Handler().postDelayed(() -> {

            switch (index) {
                case 0:
                    binding.fabAction.setImageResource(R.drawable.ic_baseline_chat_24);
                    break;

                case 1:
                    binding.fabAction.setImageResource(R.drawable.ic_baseline_camera_alt_24);
                    break;

                case 2:
                    binding.fabAction.setImageResource(R.drawable.ic_baseline_call_24);
                    break;
            }

            binding.fabAction.show();

        }, 400); // đẹp hơn 2000ms
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Toast.makeText(this,
                "Bạn chọn: " + item.getTitle(),
                Toast.LENGTH_SHORT
        ).show();

        return super.onOptionsItemSelected(item);
    }
}
