package com.example.bihu.mainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bihu.R;
import com.example.bihu.mvp.model.RegisterModel;
import com.example.bihu.mvp.presenter.RegisterPresenter;
import com.example.bihu.mvp.view.RegisterView;

import java.util.HashMap;
import java.util.Map;

public class Register extends Activity {
    Button register;
    Button back;
    EditText username;
    EditText usersecret;
    RegisterModel registerModel = new RegisterModel();
    RegisterView registerView = new RegisterView();
    RegisterPresenter<RegisterModel, RegisterView> registerPresenter =
            new RegisterPresenter<RegisterModel, RegisterView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        init();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    registerView.showToast(Register.this, "请输入用户名");
                } else if (usersecret.getText().toString().equals("")) {
                    registerView.showToast(Register.this, "请输入密码");
                } else {
                    Log.d("name", username.getText().toString());
                    Log.d("password", usersecret.getText().toString());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("username", username.getText().toString());
                    map.put("password", usersecret.getText().toString());
                    registerPresenter.registerModel(registerModel);
                    registerPresenter.registerView(registerView);
                    Boolean flag = registerPresenter.isAccess(Register.this, Register.this, map);
                    if(flag) {
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void init() {
        register = findViewById(R.id.register_register);
        back = findViewById(R.id.register_back);
        username = findViewById(R.id.register_username);
        usersecret = findViewById(R.id.register_usersecret);
    }

}
