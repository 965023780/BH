package com.example.bihu.http;

interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
