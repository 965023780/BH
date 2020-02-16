package com.example.bihu.mvp.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoriteModel extends BaseModel {
    private String result = "";
    private ArrayList<String> username = new ArrayList<String>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> picture = new ArrayList<String>();
    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> likeNumber = new ArrayList<String>();
    private ArrayList<String> naiveNumber = new ArrayList<String>();
    private ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<String> isLike = new ArrayList<String>();
    private ArrayList<String> isNaive = new ArrayList<String>();
    private ArrayList<String> avatar = new ArrayList<String>();


    public ArrayList<String> getContent() {
        return content;
    }

    public ArrayList<String> getPicture() {
        return picture;
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
        return isLike;
    }

    public ArrayList<String> getIsnaive() {
        return isNaive;
    }

    public ArrayList<String> getAvatar() {
        return avatar;
    }

    public void cancelFavorite(String address, Context context) {
        final String result = getJson(address, context);
        out.clear();
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
                this.isLike.add(isLike);
                String isNaive = object.getString("is_naive");
                this.isNaive.add(isNaive);
                String avatar = object.getString("authorAvatar");
                this.avatar.add(avatar);
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}