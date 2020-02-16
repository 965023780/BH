package com.example.bihu.mvp.view;

import android.app.AlertDialog;
import android.content.Context;

public class LoginView extends BaseView {

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .create();
        alertDialog.show();
    }
}
