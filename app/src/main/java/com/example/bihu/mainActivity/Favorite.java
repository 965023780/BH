package com.example.bihu.mainActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bihu.R;
import com.example.bihu.mvp.model.FavoriteModel;
import com.example.bihu.mvp.presenter.FavoritePresenter;
import com.example.bihu.mvp.view.FavoriteView;

public class Favorite extends AppCompatActivity {
    private Toolbar toolbar;
    private FavoriteView favoriteView;
    private FavoritePresenter favoritePresenter;
    private FavoriteModel favoriteModel;
    private RecyclerView recyclerView;
    private String token;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Bundle extras = getIntent().getExtras();
        token = extras.getString("token");
        init();
        favoritePresenter.create();
    }

    void init() {
        toolbar = findViewById(R.id.favorite_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        favoritePresenter = new FavoritePresenter<FavoriteModel, FavoriteView>(token, Favorite.this, this);
        recyclerView = findViewById(R.id.favorite_rc);
        favoriteModel = new FavoriteModel();
        favoriteView = new FavoriteView(recyclerView, this);
        favoritePresenter.registerModel(favoriteModel);
        favoritePresenter.registerView(favoriteView);
    }
}
