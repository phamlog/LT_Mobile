package com.example.circleindicator3_viewpager2;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Images> imagesList1;

    private Handler handler = new Handler();
    private Runnable runnable;
    private static final long DELAY_MILLIS = 3000; // 3 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        viewPager2 = findViewById(R.id.viewpager2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);

        // Dữ liệu ảnh
        imagesList1 = getListImages();
        ImagesViewPager2Adapter adapter1 = new ImagesViewPager2Adapter(imagesList1);
        viewPager2.setAdapter(adapter1);

        // Liên kết ViewPager2 với Indicator
        circleIndicator3.setViewPager(viewPager2);

        // Transformer hiệu ứng chuyển trang (giống slide)
        viewPager2.setPageTransformer(new DepthPageTransformer());
        // hoặc: viewPager2.setPageTransformer(new ZoomOutPageTransformer());

        // Cài đặt AutoRun
        setupAutoRun();
    }

    private List<Images> getListImages() {
        List<Images> list = new ArrayList<>();
        // thay các drawable bên dưới bằng ảnh của cậu
        list.add(new Images(R.drawable.quangcao));
        list.add(new Images(R.drawable.coffee));
        list.add(new Images(R.drawable.companypizza));
        list.add(new Images(R.drawable.themoingon));
        return list;
    }

    // Bước 6: AutoRun
    private void setupAutoRun() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() == imagesList1.size() - 1) {
                    viewPager2.setCurrentItem(0);
                } else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
            }
        };

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, DELAY_MILLIS);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, DELAY_MILLIS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}