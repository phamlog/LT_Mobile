package com.example.circleindicator3_viewpager2;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-∞,-1)
            view.setAlpha(0f);

        } else if (position <= 0) { // [-1,0]
            // Slide mặc định
            view.setAlpha(1f);
            view.setTranslationX(0f);
            view.setTranslationZ(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);

        } else if (position <= 1) { // (0,1]
            // Fade out
            view.setAlpha(1 - position);

            // Dịch sang trái để chống lại slide mặc định
            view.setTranslationX(pageWidth * -position);
            view.setTranslationZ(-1f);

            // Thu nhỏ lại
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+∞]
            view.setAlpha(0f);
        }
    }
}