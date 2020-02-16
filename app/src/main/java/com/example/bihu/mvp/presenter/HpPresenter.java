package com.example.bihu.mvp.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.bihu.R;
import com.example.bihu.mainActivity.HomePage;
import com.example.bihu.mainActivity.Login;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.view.HpView;

import java.util.HashMap;
import java.util.Map;

public class HpPresenter<M extends HpModel, V extends HpView> {
    private Map<String, String> map = new HashMap<String, String>();
    private Context context;
    private Activity activity;
    private String token;
    private int count = 20;
    M model;
    V view;

    public HpPresenter(String token, Context context, Activity activity) {
        map.put("token", token);
        Log.d("token", token);
        map.put("count", count + "");
        map.put("page", "1");
        this.context = context;
        this.activity = activity;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void reFresh() {
        count += 20;
        map.clear();
        map.put("token", token);
        map.put("count", count + "");
        map.put("page", "1");
        isAccess(context, activity);
        view.reSet(model.getUsername(), model.getTimes(), model.getTitle(), model.getContent(), model.getPicture(),
                model.getLikeNumber(), model.getNaiveNumber(), model.getIslike(), model.getIsnaive(),
                model.getId(), model.getAvatar(), activity, context, this);
    }

    public void postPicture(String url) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("token", token);
        m.put("avatar", url);
        model.setMap(m);
        model.postPicture("http://bihu.jay86.com/modifyAvatar.php", context);
        if (model.out.get("status").equals("200")) {
            view.showToast(context, "修改成功");
        } else {
            view.showToast(context, "修改失败");
        }
    }

    public boolean postEN(String address, int id, int k, int type) {
        Map<String, String> m = new HashMap<String, String>();
        if (k > 1) {
            m.put("id", String.valueOf(id));
        } else {
            m.put("qid", String.valueOf(id));
        }
        if (k > 1) {
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

    public void showPasswordDialog(final String token) {
        final Dialog dialog = view.showPassWordDialog();
        final View vw = view.getView();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().findViewById(R.id.dialog_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText oldOne = vw.findViewById(R.id.dialog_old_password);
                EditText newOne = vw.findViewById(R.id.dialog_new_password1);
                EditText newTwo = vw.findViewById(R.id.dialog_new_password2);
                final String oldPassword = oldOne.getText().toString();
                final String newPasswordOne = newOne.getText().toString();
                final String newPasswordTwo = newTwo.getText().toString();
                boolean flag = checkPasswordDialog(token, oldPassword, newPasswordOne, newPasswordTwo);
                dialog.dismiss();
                if (flag) {
                    Intent intent = new Intent(context, Login.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
    }

    public void showQuestionDialog(final String token) {
        Dialog dialog = view.showQuestionDialog();
        final View vw = view.getView();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().findViewById(R.id.question_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = vw.findViewById(R.id.question_title);
                EditText content = vw.findViewById(R.id.question_content);
                final String mTitle = title.getText().toString();
                final String mContent = content.getText().toString();
                String info = model.dealQuestion(context, token, mTitle, mContent);
                view.showToast(context, info);
            }
        });
    }

    public Boolean checkPasswordDialog(String token, String oldPassword, String newPasswordOne, String newPasswordTwo) {
        model.out.clear();
        String info = model.dealPassword(context, token, oldPassword, newPasswordOne, newPasswordTwo);
        if (model.out.get("status") == null) {
            view.showAlertDialog(context, "修改失败", info);
            return false;
        }
        if (model.out.get("status").equals("200")) {
            view.showToast(context, info);
            return true;
        }
        view.showAlertDialog(context, "修改失败", info);
        return false;
    }

    public void showDialog(String text) {
        view.showToast(context, text);
    }

    public void create() {
        if (!isAccess(context, activity)) {
            return;
        }
        view.createMyRecyclerAdapter(model.getUsername(), model.getTimes(), model.getTitle(), model.getContent(), model.getPicture(),
                model.getLikeNumber(), model.getNaiveNumber(), model.getIslike(), model.getIsnaive(),
                model.getId(), model.getAvatar(), activity, this);
        this.view.setRecyclerView();
    }

    public boolean isAccess(Context context, Activity activity) {
        boolean flag = view.requireInternetPermission(context, activity);
        flag = view.requireInternetPermission(context, activity);
        if (!flag) {
            this.view.showToast(context, "网络请求失败");
            return false;
        }
        model.setMap(map);
        model.getData("http://bihu.jay86.com/getQuestionList.php", context);
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
