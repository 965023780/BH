package com.example.bihu.mvp.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.bihu.R;
import com.example.bihu.mainActivity.Favorite;
import com.example.bihu.mainActivity.Login;
import com.example.bihu.mvp.model.FavoriteModel;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.view.FavoriteView;
import com.example.bihu.mvp.view.HpView;

import java.util.HashMap;
import java.util.Map;

public class FavoritePresenter<M extends FavoriteModel, V extends FavoriteView> {
    private Map<String, String> map = new HashMap<String, String>();
    private Context context;
    private Activity activity;
    private String token;
    M model;
    V view;

    public FavoritePresenter(String token, Context context, Activity activity) {
        map.put("token", token);
        Log.d("token", token);
        map.put("count", "20");
        map.put("page", "0");
        this.context = context;
        this.activity = activity;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void showDialog(String text) {
        view.showToast(context, text);
    }

    public void create() {
        if (!isAccess(context, activity)) {
            return;
        }
        view.createMyRecyclerAdapter(model.getUsername(), model.getTitle(), model.getContent(),
                model.getLikeNumber(), model.getNaiveNumber(), model.getIslike(), model.getIsnaive(),
                model.getId(), model.getAvatar(), activity, this);
        this.view.setRecyclerView();
    }

    public boolean cancelFavorite(int id) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("qid", String.valueOf(id));
        m.put("token", token);
        model.setMap(m);
        model.cancelFavorite("http://bihu.jay86.com/cancelFavorite.php", context);
        if (model.out.get("status").equals("200")) {
            view.showToast(context, "取消收藏成功");
            return true;
        } else {
            view.showToast(context, "取消收藏失败");
            return false;
        }
    }

    public boolean isAccess(Context context, Activity activity) {
        boolean flag = view.requireInternetPermission(context, activity);
        flag = view.requireInternetPermission(context, activity);
        if (!flag) {
            this.view.showToast(context, "网络请求失败");
            return false;
        }
        model.setMap(map);
        model.getData("http://bihu.jay86.com/getFavoriteList.php", context);
        if (model.out.get("status").equals("400") || model.out.get("status").equals("401") || model.out.get("status").equals("500")) {
            this.view.showToast(context, "请求失败");
        } else if (model.out.get("status").equals("200")) {
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
