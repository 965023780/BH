package com.example.bihu.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.example.bihu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HpModel extends BaseModel {
    private String result = "";
    private ArrayList<String> username = new ArrayList<String>();
    private ArrayList<String> times = new ArrayList<String>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> picture = new ArrayList<String>();
    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> likeNumber = new ArrayList<String>();
    private ArrayList<String> naiveNumber = new ArrayList<String>();
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<String> islike = new ArrayList<String>();
    private ArrayList<String> isnaive = new ArrayList<String>();
    private ArrayList<String> avatar =new ArrayList<String>();


    public ArrayList<String> getContent() {
        return content;
    }

    public ArrayList<String> getPicture() {
        return picture;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public ArrayList<String> getUsername() {
        return username;
    }

    public ArrayList<String> getLikeNumber() {
        return likeNumber;
    }

    public ArrayList<String> getNaiveNumber() {
        return naiveNumber;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<String> getIslike() {
        return islike;
    }

    public ArrayList<String> getIsnaive() {
        return isnaive;
    }

    public ArrayList<String> getAvatar() {
        return avatar;
    }

    public String dealPassword(Context context, String token, String oldPassword, String newPasswordOne, String newPasswordTwo) {
        if (oldPassword.equals("")) {
            return "旧密码为空";
        } else if (newPasswordOne.equals("")) {
            return "新密码为空";
        } else if (newPasswordTwo.equals("")) {
            return "请再输入一遍新密码";
        }
        if (!newPasswordOne.equals(newPasswordTwo)) {
            return "新密码两次输入不匹配";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", newPasswordOne);
        map.put("token", token);
        setMap(map);
        postEN("http://bihu.jay86.com/changePassword.php", context);
        if (out.get("status").equals("200")) {
            return "修改密码成功，请重新登入";
        }
        return out.get("info");
    }

    public String dealQuestion(Context context, String token, String title,String content) {
        if (title.equals("")) {
            return "标题为空";
        } else if (content.equals("")) {
            return "内容为空";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", title);
        map.put("content",content);
        map.put("token", token);
        setMap(map);
        postQuestion("http://bihu.jay86.com/question.php", context);
        if (out.get("status").equals("200")) {
            return "发布成功";
        }
        return out.get("info");
    }

    public void postPicture(String address, Context context) {
        final String result = getJson(address, context);
        try {
            JSONObject jsonObject = new JSONObject(result);
            out.put("status", jsonObject.getString("status"));
            if (!out.get("status").equals("200")) {
                out.put("status", jsonObject.getString("info"));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    public void postQuestion(String address, Context context) {
        final String result = getJson(address, context);
        try {
            JSONObject jsonObject = new JSONObject(result);
            out.put("status", jsonObject.getString("status"));
            if (!out.get("status").equals("200")) {
                out.put("status", jsonObject.getString("info"));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    public void postEN(String address, Context context) {
        final String result = getJson(address, context);
        try {
            JSONObject jsonObject = new JSONObject(result);
            out.put("status", jsonObject.getString("status"));
            if (!out.get("status").equals("200")) {
                out.put("status", jsonObject.getString("info"));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData(String address, Context context) {
        final String result = getJson(address, context);
        try {
            JSONObject jsonObject = new JSONObject(result);
            out.put("status", jsonObject.getString("status"));
            if (!out.get("status").equals("200")) {
                return;
            }
            JSONArray array = new JSONArray(new JSONObject(jsonObject.getString("data"))
                    .getString("questions"));
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("authorName");
                this.username.add(name);
                String times = object.getString("date");
                this.times.add(times);
                String title = object.getString("title");
                this.title.add(title);
                String picture = object.getString("images");
                int id = object.getInt("id");
                this.id.add(id);
                if (picture.equals("")) {
                    this.picture.add(null);
                } else {
                    this.picture.add(picture);
                }
                String content = object.getString("content");
                this.content.add(content);
                String likeNumber = object.getString("exciting");
                this.likeNumber.add(likeNumber);
                String naiveNumber = object.getString("naive");
                this.naiveNumber.add(naiveNumber);
                String isLike = object.getString("is_exciting");
                this.islike.add(isLike);
                String isNaive = object.getString("is_naive");
                this.isnaive.add(isNaive);
                String avatar=object.getString("authorAvatar");
                this.avatar.add(avatar);
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}