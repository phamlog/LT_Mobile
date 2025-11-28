package com.example.viewflipper;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {

    private ViewFlipper viewFlipperMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipperMain = findViewById(R.id.viewflipper);
        actionViewFlipperMain();
    }

    // Hàm cấu hình ViewFlipper
    private void actionViewFlipperMain() {
        List<String> arrayListFlipper = new ArrayList<>();

        arrayListFlipper.add("https://picsum.photos/800/400?1");
        arrayListFlipper.add("https://picsum.photos/800/400?2");
        arrayListFlipper.add("https://picsum.photos/800/400?3");


        // Thêm ImageView cho từng URL
        for (int i = 0; i < arrayListFlipper.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());

            Glide.with(getApplicationContext())
                    .load(arrayListFlipper.get(i))
                    .into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageView);
        }

        // Thiết lập thời gian lật & tự động chạy
        viewFlipperMain.setFlipInterval(3000); // 3 giây
        viewFlipperMain.setAutoStart(true);

        // Gán animation vào ViewFlipper
        Animation slideIn = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.slide_out_right);

        viewFlipperMain.setInAnimation(slideIn);
        viewFlipperMain.setOutAnimation(slideOut);
    }
}
