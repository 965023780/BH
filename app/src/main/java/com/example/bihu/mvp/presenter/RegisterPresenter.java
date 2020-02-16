package com.example.bihu.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.bihu.mvp.model.RegisterModel;
import com.example.bihu.mvp.view.RegisterView;

import java.util.Map;

public class RegisterPresenter<M extends RegisterModel,V extends RegisterView>{
    public String token="";
    M model;
    V view;

    public boolean isAccess(Context context, Activity activity, Map<String, String> map){
        boolean flag=view.requireInternetPermission(context,activity);
        flag=view.requireInternetPermission(context,activity);
        if(!flag){
            this.view.showAlertDialog(context,"注册失败","网络请求失败");
            return false;
        }
        model.setMap(map);
        model.getData("http://bihu.jay86.com/register.php",context);
        if (model.out.get("status").equals("400")||model.out.get("status").equals("401") || model.out.get("status").equals("500")) {
            this.view.showAlertDialog(context,"注册失败",model.out.get("info"));
        }else if (model.out.get("status").equals("200")){
            this.view.showToast(context,"注册成功");
            return true;
        }
        return false;
    }
    public void registerModel(M model){
        this.model=model;
    }
    public void registerView(V view){
        this.view=view;
    }
    public void destroy(){
        if(view!=null){
            view=null;
        }
    }
}
