package com.example.bihu.mainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bihu.R;
import com.example.bihu.mvp.model.LoginModel;
import com.example.bihu.mvp.presenter.LoginPresenter;
import com.example.bihu.mvp.view.LoginView;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button register;
    Button login;
    EditText username;
    EditText usersecret;
    LoginModel loginModel = new LoginModel();
    LoginView loginView = new LoginView();
    LoginPresenter<LoginModel, LoginView> loginPresenter = new LoginPresenter<LoginModel, LoginView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginView.requireInternetPermission(this, this);
        loginView.requireCameraPermission(this, this);
        loginView.requireReadPermission(this, this);
        loginView.requireWritePermission(this, this);
        init();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    loginView.showToast(Login.this, "请输入用户名");
                } else if (usersecret.getText().toString().equals("")) {
                    loginView.showToast(Login.this, "请输入密码");
                } else {
                    Log.d("name", username.getText().toString());
                    Log.d("password", usersecret.getText().toString());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("username", username.getText().toString());
                    map.put("password", usersecret.getText().toString());
                    loginPresenter.registerModel(loginModel);
                    loginPresenter.registerView(loginView);
                    Boolean flag = loginPresenter.isAccess(Login.this, Login.this, map);
                    if (flag) {
                        String userPicture = loginPresenter.getPicture();
                        Intent intent = new Intent(Login.this, HomePage.class);
                        intent.putExtra("token", loginPresenter.token);
                        intent.putExtra("username", username.getText().toString());
                        intent.putExtra("userPicture", userPicture);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    void init() {
        register = findViewById(R.id.login_register);
        login = findViewById(R.id.login_login);
        username = findViewById(R.id.login_username);
        usersecret = findViewById(R.id.login_usersecret);
    }
}
