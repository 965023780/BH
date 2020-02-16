package com.example.bihu.mainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bihu.MyCircleImageView;
import com.example.bihu.R;
import com.example.bihu.imageloader.ImageLoader;
import com.example.bihu.myRecycler.HomePageRecyclerAdapter;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.presenter.HpPresenter;
import com.example.bihu.mvp.view.HpView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    private static String token;
    HomePageRecyclerAdapter homePageRecyclerAdapter;
    RecyclerView recyclerView;
    HpPresenter<HpModel, HpView> hpPresenter;
    HpView hpView;
    HpModel hpModel = new HpModel();
    //private ImageView imageView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView userName;
    private static String name;
    private static String pictureUrl = "";
    private File tempFile;
    private MyCircleImageView userPicture;
    private String uri1;
    private String uri2;
    private String uri3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
            name = extras.getString("username");
            pictureUrl = extras.getString("userPicture");
        }
        init();
        setToolbar();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        hpPresenter.create();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_password:
                        hpPresenter.showPasswordDialog(token);
                        break;
                    case R.id.nav_favorite:
                        Intent intent = new Intent(HomePage.this, Favorite.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                        break;
                    case R.id.nav_picture:
                        setPicture();
                        break;
                    case R.id.nav_question:
                        hpPresenter.showQuestionDialog(token);
                        break;
                }
                return false;
            }
        });
    }


    void init() {
        recyclerView = findViewById(R.id.rc);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    hpPresenter.reFresh();
                    hpPresenter.showDialog("加载");
                }
            }
        });
        hpPresenter = new HpPresenter<HpModel, HpView>(token, this, this);
        hpView = new HpView(recyclerView, this);
        hpPresenter.registerModel(hpModel);
        hpPresenter.registerView(hpView);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav);
        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        userPicture = header.findViewById(R.id.imageView);
        if (!pictureUrl.equals("null")) {
            userPicture.setImageURI(Uri.fromFile(new File(pictureUrl)));
        }
        userName = header.findViewById(R.id.nav_name);
        userName.setText(name);
    }


    void setToolbar() {
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
    }

    void setPicture() {
        final String[] items = {"从相册选择图片", "拍照", ""};
        new AlertDialog.Builder(HomePage.this).setTitle("修改头像").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        getFromAblu();
                        break;
                    case 1:
                        if (hpView.requireCameraPermission(HomePage.this, HomePage.this)) {
                            getFromCamera();
                        }
                        break;
                }
            }
        }).show();
    }

    private void getFromCamera() {
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, 1);
    }

    public void getFromAblu() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(Uri.fromFile(tempFile));
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case 3:
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Bitmap image = bundle.getParcelable("data");
                    userPicture.setImageBitmap(image);
                    try {
                        String address = getImage(name, image);
                        hpPresenter.postPicture(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public String getImage(String name, Bitmap bmp) {
        File appDir = new File("/data/data/com.example.bihu/files");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".png";
        File file = new File(appDir, fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homepage_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_question:
                hpPresenter.showQuestionDialog(token);
                break;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1109:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                    getFromCamera();
                } else {
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
