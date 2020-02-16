package com.example.bihu.mvp.model;

import android.content.Context;
import android.util.Log;

import com.example.bihu.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseModel {
    private Map<String,String> in=new HashMap<String,String>();
    public Map<String,String> out=new HashMap<String,String>();

    public  void setMap(Map<String,String> map){
            this.in=map;
    }

    public String getJson(String address, Context context){
        HttpUtil httpUtil = new HttpUtil();
        httpUtil.setHttpUtil(address, context);
        String result = httpUtil.postData(in);
        Log.d("result",result);
        return result;
    }

    public void getData(String address, Context context) throws InterruptedException {}

}
