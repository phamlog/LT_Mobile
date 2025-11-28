package com.example.slider_view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;   // 👈 import SliderView

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SliderView sliderView;
    private ArrayList<Integer> arrayList;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        sliderView = findViewById(R.id.imageSlider);

        // Danh sách ảnh (trong drawable)
        arrayList = new ArrayList<>();
        arrayList.add(R.drawable.shoppe1);
        arrayList.add(R.drawable.shoppe2);
        arrayList.add(R.drawable.shoppe3);
        arrayList.add(R.drawable.shoppe4);

        // Adapter
        sliderAdapter = new SliderAdapter(this, arrayList);
        sliderView.setSliderAdapter(sliderAdapter);

        // Thuộc tính Slider
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.RED);      // hoặc ContextCompat.getColor(...)
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5);
        sliderView.startAutoCycle();
    }
}
