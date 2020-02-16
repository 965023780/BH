package com.example.bihu.myRecycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bihu.MyCircleImageView;
import com.example.bihu.R;
import com.example.bihu.imageloader.ImageLoader;
import com.example.bihu.mainActivity.Answer;
import com.example.bihu.mainActivity.Favorite;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.presenter.FavoritePresenter;
import com.example.bihu.mvp.presenter.HpPresenter;
import com.example.bihu.mvp.view.FavoriteView;
import com.example.bihu.mvp.view.HpView;

import java.util.ArrayList;


public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.MyInnerViewHolder> {

    private ArrayList<String> username;
    private ArrayList<String> title;
    private ArrayList<String> content;
    private ArrayList<String> likeNumber;
    private ArrayList<String> naiveNumber;
    private ArrayList<String> isLike;
    private ArrayList<String> isNaive;
    private ArrayList<Integer> id;
    private ArrayList<String> avatar;
    private Activity activity;
    private FavoritePresenter favoritePresenter;
    private Context context;

    public FavoriteRecyclerAdapter(ArrayList<String> username, ArrayList<String> title,
                                   ArrayList<String> content, ArrayList<String> likeNumber,
                                   ArrayList<String> naiveNumber, ArrayList<String> isLike, ArrayList<String> isNaive,
                                   ArrayList<Integer> id, ArrayList<String> avatar, Activity activity,
                                   Context context, FavoritePresenter favoritePresenter) {
        this.username = username;
        this.title = title;
        this.activity = activity;
        this.content = content;
        this.context = context;
        this.naiveNumber = naiveNumber;
        this.likeNumber = likeNumber;
        this.isLike = isLike;
        this.isNaive = isNaive;
        this.id = id;
        this.favoritePresenter = favoritePresenter;
        this.avatar = avatar;
    }

    public void reFresh() {
        notifyDataSetChanged();
    }

    public void deleteFavorite(int position) {
        if (position >= 0 && position < title.size()) {
            username.remove(position);
            title.remove(position);
            content.remove(position);
            likeNumber.remove(position);
            naiveNumber.remove(position);
            id.remove(position);
            avatar.remove(position);
            isLike.remove(position);
            isNaive.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, title.size() - position, "removerFavorite");
        }
    }

    @NonNull
    @Override
    public FavoriteRecyclerAdapter.MyInnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View favoriteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new MyInnerViewHolder(favoriteView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerAdapter.MyInnerViewHolder holder, int position) {
        holder.name.setText(username.get(position));
        holder.title.setText(title.get(position));
        holder.content.setText(content.get(position));
        holder.id = id.get(position);
        holder.textView.setText(likeNumber + "认同·" + naiveNumber + "不认同");
        if (avatar.get(position) != null) {
            ImageLoader imageLoader = new ImageLoader();
            imageLoader.loadImage(avatar.get(position), holder.avatar);
        }
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyInnerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;
        private TextView content;
        private ImageButton elic;
        private int id;
        private MyCircleImageView avatar;
        private TextView textView;

        public MyInnerViewHolder(final View favoriteView) {
            super(favoriteView);
            name = favoriteView.findViewById(R.id.favorite_username);
            title = favoriteView.findViewById(R.id.favorite_title);
            content = favoriteView.findViewById(R.id.favorite_content);
            elic = favoriteView.findViewById(R.id.favorite_eli);
            avatar = favoriteView.findViewById(R.id.favorite_user_picture);
            textView = favoriteView.findViewById(R.id.favorite_en);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, Answer.class);
                    intent.putExtra("token", favoritePresenter.getToken());
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("content", content.getText().toString());
                    intent.putExtra("id", id + "");
                    activity.startActivity(intent);
                }
            });
            elic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final String[] favorites = {"取消收藏", "不看该问题"};
                    new AlertDialog.Builder(context).setTitle("其它").setItems(favorites, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (!favoritePresenter.cancelFavorite(id)) {
                                        break;
                                    }
                                case 1:
                                    deleteFavorite(getAdapterPosition());
                                    break;
                            }
                        }
                    }).show();
                }
            });

        }
    }
}

