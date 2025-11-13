package com.example.btap01;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ImageView img1;
    private ImageButton img2;
    private ConstraintLayout bg;
    private Switch sw;
    private CheckBox ck1;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private Button btnButton;

    private CountDownTimer countDownTimer;

    @SuppressLint("MissingInflatedId")
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


        img1        = findViewById(R.id.imageView1);
        img2        = findViewById(R.id.imageButton1);
        bg          = findViewById(R.id.main);
        sw          = findViewById(R.id.switch1);
        ck1         = findViewById(R.id.checkBox);
        radioGroup  = findViewById(R.id.radioGroup1);
        progressBar = findViewById(R.id.progressBar2);
        seekBar     = findViewById(R.id.seekBar);
        btnButton   = findViewById(R.id.btnButton);


        img1.setImageResource(R.drawable.kotlin);


        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.bg1);
        arrayList.add(R.drawable.bg2);
        arrayList.add(R.drawable.bg3);
        arrayList.add(R.drawable.bg4);

        Random random = new Random();
        int vitri = random.nextInt(arrayList.size());
        bg.setBackgroundResource(arrayList.get(vitri));


        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Wifi đang bật", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Wifi đang tắt", Toast.LENGTH_LONG).show();
            }
        });


        ck1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bg.setBackgroundResource(R.drawable.bg3);
            } else {
                bg.setBackgroundResource(R.drawable.bg4);
            }
        });


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton) {
                bg.setBackgroundResource(R.drawable.bg3);
            } else if (checkedId == R.id.radioButton2) {
                bg.setBackgroundResource(R.drawable.bg4);
            }
        });


        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromUser) {
                Log.d("SEEK", "Giá trị: " + progress);
                // đổi alpha của img1 theo progress (0–1)
                float alpha = progress / 100f;
                img1.setAlpha(alpha);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {
                Log.d("SEEK", "Start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                Log.d("SEEK", "Stop");
            }
        });


        progressBar.setMax(100);
        img2.setOnClickListener(v -> {
            // Đổi ảnh + kích thước
            img1.setImageResource(R.drawable.on);
            img1.getLayoutParams().width = 550;
            img1.getLayoutParams().height = 550;
            img1.requestLayout();

            // Hủy timer cũ nếu có
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            countDownTimer = new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int current = progressBar.getProgress();
                    if (current >= progressBar.getMax()) {
                        current = 0;
                    }
                    progressBar.setProgress(current + 10);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Hết giờ", Toast.LENGTH_LONG).show();
                }
            };
            countDownTimer.start();
        });



        registerForContextMenu(btnButton);


        btnButton.setOnClickListener(v -> showPopupMenu());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuSetting) {
            Toast.makeText(this, "Options: Setting", Toast.LENGTH_SHORT).show();
            showCustomDialog();
        } else if (id == R.id.menuShare) {
            Toast.makeText(this, "Options: Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuLogout) {
            showConfirmLogout();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu_setting, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menuSetting) {
                Toast.makeText(MainActivity.this,
                        "Popup: Bạn đang chọn Setting", Toast.LENGTH_LONG).show();
                return true;
            } else if (id == R.id.menuShare) {
                btnButton.setText("Chia sẻ");
                return true;
            } else if (id == R.id.menuLogout) {
                showConfirmLogout();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        menu.setHeaderTitle("Context Menu");
        menu.setHeaderIcon(R.mipmap.ic_launcher); // hoặc icon của bạn
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuSetting) {
            Toast.makeText(MainActivity.this,
                    "Context: Bạn đang chọn Setting", Toast.LENGTH_LONG).show();
        } else if (id == R.id.menuShare) {
            btnButton.setText("Chia sẻ");
        } else if (id == R.id.menuLogout) {
            showConfirmLogout();
        }

        return super.onContextItemSelected(item);
    }


    private void showConfirmLogout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setMessage("Bạn có muốn đăng xuất không?");

        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,
                        "Đã chọn Đăng xuất", Toast.LENGTH_SHORT).show();

            }
        });

        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCanceledOnTouchOutside(false);

        EditText editText1 = dialog.findViewById(R.id.editNumber1);
        Button btnOK = dialog.findViewById(R.id.btnOKDialog);

        btnOK.setOnClickListener(v -> {
            String value = editText1.getText().toString().trim();
            Toast.makeText(MainActivity.this,
                    "Bạn nhập: " + value, Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
