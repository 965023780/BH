package com.example.bihu.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.bihu.mvp.model.LoginModel;
import com.example.bihu.mvp.view.LoginView;

import java.util.Map;

public class LoginPresenter<M extends LoginModel, V extends LoginView> {
    public String token = "";
    M model;
    V view;

    public String getPicture(){
        return model.out.get("avatar");
    }
    public boolean isAccess(Context context, Activity activity, Map<String, String> map) {
        boolean flag = view.requireInternetPermission(context, activity);
        flag = view.requireInternetPermission(context, activity);
        if (!flag) {
            this.view.showAlertDialog(context, "登陆失败", "网络请求失败");
            return false;
        }
        model.setMap(map);
        model.getData("http://bihu.jay86.com/login.php", context);
        if (model.out.get("status").equals("400")) {
            this.view.showAlertDialog(context, "登录失败", "用户名或密码错误");
        } else if (model.out.get("status").equals("401") || model.out.get("status").equals("500")) {
            this.view.showAlertDialog(context, "登录失败", "用户认证错误/服务器错误");
        } else if (model.out.get("status").equals("200")) {
            this.token = model.out.get("token");
            this.view.showToast(context, "登录成功");
            return true;
        }
        return false;
    }

    public void registerModel(M model) {
        this.model = model;
    }

    public void registerView(V view) {
        this.view = view;
    }

    public void destroy() {
        if (view != null) {
            view = null;
        }
    }
}
