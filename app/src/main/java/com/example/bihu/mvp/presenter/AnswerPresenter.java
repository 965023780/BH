package com.example.bihu.mvp.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bihu.R;
import com.example.bihu.mainActivity.Login;
import com.example.bihu.mvp.model.AnswerModel;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.view.AnswerView;
import com.example.bihu.mvp.view.HpView;

import java.util.HashMap;
import java.util.Map;

public class AnswerPresenter<M extends AnswerModel, V extends AnswerView> {
    private Map<String, String> map = new HashMap<String, String>();
    private Context context;
    private Activity activity;
    private String token;
    M model;
    V view;

    public AnswerPresenter(String token, Context context, Activity activity) {
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

    public void showInputDialog(final String id){
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("评论").setView(editText)
                .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editText.getText().toString()==null){
                            view.showAlertDialog(context,"发送失败","评论内容不能为空");
                        }
                        map.clear();
                        map.put("token",token);
                        map.put("content",editText.getText().toString());
                        map.put("qid",id);
                        model.postAnswer("http://bihu.jay86.com/answer.php",context);
                    }
                });
        builder.create().show();
    }
    public boolean postEN(String address, int id, int k, int type) {
        Map<String, String> m = new HashMap<String, String>();
        if (k>1) {
            m.put("id", String.valueOf(id));
        } else {
            m.put("qid", String.valueOf(id));
        }
        if (k>1) {
            m.put("type", String.valueOf(type));
        }
        m.put("token", token);
        model.setMap(m);
        model.out.clear();
        model.postEN(address, context);
        if (model.out.get("status").equals("200")) {
            if (k == 0) {
                view.showToast(context, "取消收藏成功");
            } else if (k == 1) {
                view.showToast(context, "收藏成功");
            } else if (k == 2) {
                view.showToast(context, "认同成功");
            } else if (k == 3) {
                view.showToast(context, "取消认同成功");
            } else if (k == 4) {
                view.showToast(context, "不认同成功");
            } else {
                view.showToast(context, "取消不认同成功");
            }
            return true;
        } else {
            view.showToast(context, "失败");
            return false;
        }
    }

    public void showDialog(String text) {
        view.showToast(context, text);
    }

    public boolean create(String id) {
        if (!isAccess(context, activity,id)) {
            return true;
        }
        view.createMyRecyclerAdapter(model.getUsername(), model.getTimes(), model.getTitle(), model.getContent(),
                model.getLikeNumber(), model.getNaiveNumber(), model.getIsLike(), model.getIsNaive(),
                model.getId(), model.getAvatar(),activity, this);
        this.view.setRecyclerView();
        if (model.getContent().size()==0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isAccess(Context context, Activity activity,String id) {
        boolean flag = view.requireInternetPermission(context, activity);
        flag = view.requireInternetPermission(context, activity);
        if (!flag) {
            this.view.showToast(context, "网络请求失败");
            return false;
        }
        map.put("qid",id);
        model.setMap(map);
        model.getData("http://bihu.jay86.com/getAnswerList.php", context);
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
