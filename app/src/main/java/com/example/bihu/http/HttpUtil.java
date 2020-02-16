package com.example.bihu.http;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.bihu.http.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpUtil {
    private String address;
    private Context context;

    public void setHttpUtil(String address, Context context) {
        this.address = address;
        this.context = context;
    }

    public String postData(Map<String, String> params) {
        final StringBuffer out = new StringBuffer();
        final StringBuffer result=new StringBuffer();

        for (String key : params.keySet()) {
            if (out.length() != 0) {
                out.append("&");
            }
            out.append(key).append("=").append(params.get(key));
        }
        Log.d("append",out.toString());
        Thread t=new Thread(new Runnable(){
            @Override
            public void run(){
                URL url;
                HttpURLConnection httpURLConnection=null;
                try{
                    url=new URL(address);
                    httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(4000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    PrintWriter pw=new PrintWriter(httpURLConnection.getOutputStream());
                    pw.write(out.toString());
                    pw.flush();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }
                    pw.close();
                    reader.close();
                    Log.d("result",result.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(httpURLConnection!=null)
                         httpURLConnection.disconnect();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
