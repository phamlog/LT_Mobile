package com.example.week5_4_12;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImagesActivity extends AppCompatActivity {

    private static final String TAG = "UploadImagesActivity";
    private static final int MY_REQUEST_CODE = 100;

    private Button btnChoose, btnUpload;
    private ImageView imgMultipart;

    private Uri mUri;
    private String mRealPath;
    private String userId;

    // quyền cho Android 13 trở lên
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    // quyền cho Android < 13
    public static String[] storage_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        AnhXa();

        // lấy id user từ Intent
        userId = getIntent().getStringExtra(Const.EXTRA_USER_ID);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Uploading...");

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null && mRealPath != null) {
                    uploadImageToServer();
                } else {
                    Toast.makeText(UploadImagesActivity.this,
                            "Vui lòng chọn ảnh trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imgMultipart = findViewById(R.id.imgMultipart);
    }

    // launcher nhận kết quả chọn ảnh
    private ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                Intent data = result.getData();
                                mUri = data.getData();
                                if (mUri == null) return;

                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                            getContentResolver(), mUri);
                                    imgMultipart.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                // lấy real path
                                mRealPath = RealPathUtil.getRealPath(UploadImagesActivity.this, mUri);
                                Log.d(TAG, "Real path: " + mRealPath);
                            }
                        }
                    });

    // mở thư viện ảnh
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    // kiểm tra & xin quyền
    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(this,
                        storage_permissions_33, MY_REQUEST_CODE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                ActivityCompat.requestPermissions(this,
                        storage_permissions, MY_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Không được cấp quyền truy cập ảnh",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // upload file lên server
    private void uploadImageToServer() {
        mProgressDialog.show();

        File file = new File(mRealPath);
        String file_path = file.getAbsolutePath();

        // lấy tên file
        String[] split = file_path.split("\\.");
        String ext = split[split.length - 1];

        RequestBody requestBodyFile =
                RequestBody.create(MediaType.parse("image/" + ext), file);

        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData(Const.IMAGES, file.getName(), requestBodyFile);

        RequestBody requestBodyId =
                RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        ServiceAPI api = ApiClient.getService();
        Call<UpdateImageResponse> call =
                api.uploadImageProfile(requestBodyId, multipartBody);

        call.enqueue(new Callback<UpdateImageResponse>() {
            @Override
            public void onResponse(Call<UpdateImageResponse> call,
                                   Response<UpdateImageResponse> response) {
                mProgressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    UpdateImageResponse res = response.body();
                    Toast.makeText(UploadImagesActivity.this,
                            res.getMessage(), Toast.LENGTH_LONG).show();

                    if (res.isSuccess() && res.getResult() != null
                            && !res.getResult().isEmpty()) {
                        UserModel user = res.getResult().get(0);
                        String newImageUrl = user.getImages();

                        // trả về cho ProfileActivity để load lại ảnh
                        Intent intent = new Intent();
                        intent.putExtra(Const.EXTRA_USER_IMAGE, newImageUrl);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    Toast.makeText(UploadImagesActivity.this,
                            "Upload thất bại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateImageResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(UploadImagesActivity.this,
                        "Gọi API thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }
}
