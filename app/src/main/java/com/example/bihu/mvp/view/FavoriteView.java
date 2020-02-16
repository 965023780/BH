package com.example.bihu.mvp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.example.bihu.mvp.presenter.FavoritePresenter;
import com.example.bihu.mvp.presenter.HpPresenter;
import com.example.bihu.myRecycler.FavoriteRecyclerAdapter;
import com.example.bihu.myRecycler.HomePageRecyclerAdapter;
import com.example.bihu.myRecycler.MyRecyclerItem;

import java.util.ArrayList;

public class FavoriteView extends BaseView {
    FavoriteRecyclerAdapter favoriteRecyclerAdapter;
    RecyclerView recyclerView;
    Context context;
    View view;

    public FavoriteView(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .create();
        alertDialog.show();
    }

    public void createMyRecyclerAdapter(ArrayList<String> username, ArrayList<String> title,
                                        ArrayList<String> content, ArrayList<String> likeNumber,
                                        ArrayList<String> naiveNumber, ArrayList<String> isLike, ArrayList<String> isNaive,
                                        ArrayList<Integer> id, ArrayList<String>avatar, Activity activity, FavoritePresenter favoritePresenter) {
        favoriteRecyclerAdapter = new FavoriteRecyclerAdapter(username,title, content,likeNumber, naiveNumber,
                isLike, isNaive, id,avatar, activity,context,favoritePresenter);
    }

    public void setRecyclerView() {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(245, 245, 245));
        recyclerView.addItemDecoration(new MyRecyclerItem(20, paint));
        recyclerView.setAdapter(favoriteRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, OrientationHelper.VERTICAL));
    }

}
