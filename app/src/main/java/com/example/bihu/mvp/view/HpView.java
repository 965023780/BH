package com.example.bihu.mvp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bihu.R;
import com.example.bihu.mainActivity.HomePage;
import com.example.bihu.mainActivity.Login;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.presenter.HpPresenter;
import com.example.bihu.myRecycler.HomePageRecyclerAdapter;
import com.example.bihu.myRecycler.MyRecyclerItem;

import java.util.ArrayList;

import static android.view.Gravity.BOTTOM;

public class HpView extends BaseView {
    HomePageRecyclerAdapter homePageRecyclerAdapter;
    RecyclerView recyclerView;
    Context context;
    View view;

    public HpView(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public void reSet(ArrayList<String> username, ArrayList<String> times, ArrayList<String> title,
                      ArrayList<String> content, ArrayList<String> picture, ArrayList<String> likeNumber,
                      ArrayList<String> naiveNumber, ArrayList<String> isLike, ArrayList<String> isNaive,
                      ArrayList<Integer> id, ArrayList<String> avatar, Activity activity,
                      Context context, HpPresenter hpPresenter){
        homePageRecyclerAdapter.reSet(username, times, title, content, picture, likeNumber, naiveNumber,
                isLike, isNaive, id,avatar, activity,context,hpPresenter);

    }
    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .create();
        alertDialog.show();
    }

    public void createMyRecyclerAdapter(ArrayList<String> username, ArrayList<String> times, ArrayList<String> title,
                                        ArrayList<String> content, ArrayList<String> picture, ArrayList<String> likeNumber,
                                        ArrayList<String> naiveNumber, ArrayList<String> isLike, ArrayList<String> isNaive,
                                        ArrayList<Integer> id, ArrayList<String> avatar,Activity activity, HpPresenter hpPresenter) {
        homePageRecyclerAdapter = new HomePageRecyclerAdapter(username, times, title, content, picture, likeNumber, naiveNumber,
                isLike, isNaive, id,avatar, activity,context,hpPresenter);
    }

    public void setRecyclerView() {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(245, 245, 245));
        recyclerView.addItemDecoration(new MyRecyclerItem(20, paint));
        recyclerView.setAdapter(homePageRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, OrientationHelper.VERTICAL));
    }

    public Dialog showPassWordDialog() {
        Dialog dialog = new Dialog(context, R.style.Dialog_Style);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_password, null);
        Window window=dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_Anima);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0, 0, 0, 0);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int mWindowWidth = displayMetrics.widthPixels;
        int mWindowHeight = displayMetrics.heightPixels;
        dialog.setContentView(view, new ViewGroup.MarginLayoutParams(mWindowWidth,
                ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public Dialog showQuestionDialog() {
        Dialog dialog = new Dialog(context, R.style.Dialog_Style);
        view = LayoutInflater.from(context).inflate(R.layout.question, null);
        Window window=dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_Anima);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0, 0, 0, 0);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int mWindowWidth = displayMetrics.widthPixels;
        int mWindowHeight = displayMetrics.heightPixels;
        dialog.setContentView(view, new ViewGroup.MarginLayoutParams(mWindowWidth,
                ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


}
