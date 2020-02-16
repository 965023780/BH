package com.example.bihu.mvp.model;

import android.content.Context;
import org.json.JSONObject;

public class RegisterModel extends BaseModel {
    private String result = "";

    @Override
    public void getData(String address, Context context) {
        final String result = getJson(address, context);
        try {
            JSONObject object = new JSONObject(result);
            out.put("status", object.getString("status"));
            if (!out.get("status").equals("200")) {
                out.put("info", object.getString("info"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
