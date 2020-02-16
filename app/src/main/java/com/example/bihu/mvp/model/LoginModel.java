package com.example.bihu.mvp.model;

import android.content.Context;

import org.json.JSONObject;

public class LoginModel extends BaseModel {
    private String result = "";

    @Override
    public void getData(String address, Context context) {
        final String result = getJson(address, context);
        try {
            JSONObject object = new JSONObject(result);
            out.put("status", object.getString("status"));
            String data=object.getString("data");
            if (out.get("status").equals("200")) {
                JSONObject jsonObject=new JSONObject(data);
                out.put("token", jsonObject.getString("token"));
                out.put("avatar",jsonObject.getString("avatar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
