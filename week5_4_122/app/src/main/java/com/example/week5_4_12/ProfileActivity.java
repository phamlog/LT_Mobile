package com.example.week5_4_12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private TextView tvId, tvUsername, tvFullname, tvEmail, tvGender;

    private String userId = "5"; // ví dụ, bạn lấy từ đăng nhập hoặc API profile
    private String imageUrl = ""; // link ảnh hiện tại từ server

    private ActivityResultLauncher<Intent> uploadLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                String newImage =
                                        result.getData().getStringExtra(Const.EXTRA_USER_IMAGE);
                                if (newImage != null && !newImage.isEmpty()) {
                                    imageUrl = newImage;
                                    loadAvatar(imageUrl);
                                }
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);  // layout hồ sơ của bạn

        imgProfile = findViewById(R.id.imgProfile); // ảnh tròn trên màn profile

        // ví dụ: sau khi gọi API profile, bạn gán các giá trị:
        // userId = response.getId();
        // imageUrl = response.getImages();

        loadAvatar(imageUrl);

        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UploadImagesActivity.class);
            intent.putExtra(Const.EXTRA_USER_ID, userId);
            uploadLauncher.launch(intent);
        });
    }

    private void loadAvatar(String url) {
        if (url == null || url.isEmpty()) {
            imgProfile.setImageResource(R.drawable.ic_account_circle_blue_200);
        } else {
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_account_circle_blue_200)
                    .error(R.drawable.ic_account_circle_blue_200)
                    .into(imgProfile);
        }
    }
}
