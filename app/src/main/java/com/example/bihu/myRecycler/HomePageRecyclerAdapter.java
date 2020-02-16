package com.example.bihu.myRecycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
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

import java.util.ArrayList;

import com.example.bihu.imageloader.ImageLoader;
import com.example.bihu.mainActivity.Answer;
import com.example.bihu.mvp.model.HpModel;
import com.example.bihu.mvp.presenter.HpPresenter;
import com.example.bihu.mvp.view.HpView;


public class HomePageRecyclerAdapter extends RecyclerView.Adapter<HomePageRecyclerAdapter.MyInnerViewHolder>    {

    private ArrayList<String> username;
    private ArrayList<String> times;
    private ArrayList<String> title;
    private ArrayList<String> picture;
    private ArrayList<String> content;
    private ArrayList<String> likeNumber;
    private ArrayList<String> naiveNumber;
    private ArrayList<String> isLike;
    private ArrayList<String> isNaive;
    private ArrayList<Integer> id;
    private ArrayList<String> avatar;
    private Activity activity;
    private HpPresenter<HpModel, HpView> hpPresenter;
    private Context context;

    public HomePageRecyclerAdapter(ArrayList<String> username, ArrayList<String> times, ArrayList<String> title,
                                   ArrayList<String> content, ArrayList<String> picture, ArrayList<String> likeNumber,
                                   ArrayList<String> naiveNumber, ArrayList<String> isLike, ArrayList<String> isNaive,
                                   ArrayList<Integer> id, ArrayList<String> avatar, Activity activity,
                                   Context context, HpPresenter<HpModel, HpView> hpPresenter) {
        this.username = username;
        this.times = times;
        this.title = title;
        this.picture = picture;
        this.activity = activity;
        this.content = content;
        this.context = context;
        this.naiveNumber = naiveNumber;
        this.likeNumber = likeNumber;
        this.hpPresenter = hpPresenter;
        this.isLike = isLike;
        this.isNaive = isNaive;
        this.id = id;
        this.avatar=avatar;
    }

    public void reSet(ArrayList<String> username, ArrayList<String> times, ArrayList<String> title,
                                   ArrayList<String> content, ArrayList<String> picture, ArrayList<String> likeNumber,
                                   ArrayList<String> naiveNumber, ArrayList<String> isLike, ArrayList<String> isNaive,
                                   ArrayList<Integer> id, ArrayList<String> avatar, Activity activity,
                                   Context context, HpPresenter<HpModel, HpView> hpPresenter) {
        this.username = username;
        this.times = times;
        this.title = title;
        this.picture = picture;
        this.activity = activity;
        this.content = content;
        this.context = context;
        this.naiveNumber = naiveNumber;
        this.likeNumber = likeNumber;
        this.hpPresenter = hpPresenter;
        this.isLike = isLike;
        this.isNaive = isNaive;
        this.id = id;
        this.avatar=avatar;
        reFresh();
    }

    public void reFresh() {
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < title.size()) {
            notifyItemRemoved(position);
            username.remove(position);
            times.remove(position);
            title.remove(position);
            picture.remove(position);
            content.remove(position);
            likeNumber.remove(position);
            naiveNumber.remove(position);
            isLike.remove(position);
            isNaive.remove(position);
            id.remove(position);
            avatar.remove(position);
            notifyItemRangeChanged(position, title.size() - position, "removerItem");
        }
    }

    @NonNull
    @Override
    public HomePageRecyclerAdapter.MyInnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (picture != null) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_picture, parent, false);
        }
        return new MyInnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageRecyclerAdapter.MyInnerViewHolder holder, int position) {
        holder.name.setText(username.get(position));
        holder.times.setText(times.get(position) + "·发布问题");
        holder.title.setText(title.get(position));
        holder.content.setText(content.get(position));
        holder.likeNumber.setText(likeNumber.get(position));
        holder.naiveNumber.setText(naiveNumber.get(position));
        holder.id = id.get(position);
        if(avatar.get(position)!=null){
            ImageLoader imageLoader=new ImageLoader();
            imageLoader.loadImage(avatar.get(position),holder.avatar);
        }
        if (picture.get(position) != null) {
            ImageLoader imageLoader = new ImageLoader();
            imageLoader.loadImage(picture.get(position), holder.picture);
        }
        if (isLike.get(position).equals("false")) {
            holder.like.setImageResource(R.drawable.like);
            holder.likeSituation = false;
        } else {
            holder.like.setImageResource(R.drawable.exciting);
            holder.likeSituation = true;
        }
        if (isNaive.get(position).equals("false")) {
            holder.naive.setImageResource(R.drawable.naive);
            holder.naiveSituation = false;
        } else {
            holder.naive.setImageResource(R.drawable.naive2);
            holder.naiveSituation = true;
        }
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyInnerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;
        private ImageView picture;
        private TextView content;
        private TextView times;
        private TextView likeNumber;
        private TextView naiveNumber;
        private ImageButton like;
        private ImageButton naive;
        private ImageButton elic;
        private boolean likeSituation;
        private boolean naiveSituation;
        private int id;
        private MyCircleImageView avatar;

        public MyInnerViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_username);
            title = itemView.findViewById(R.id.item_title);
            content = itemView.findViewById(R.id.item_content);
            times = itemView.findViewById(R.id.item_time);
            likeNumber = itemView.findViewById(R.id.item_like_number);
            naiveNumber = itemView.findViewById(R.id.item_naive_number);
            picture = itemView.findViewById(R.id.item_picture);
            naive = itemView.findViewById(R.id.item_naive);
            like = itemView.findViewById(R.id.item_like);
            elic = itemView.findViewById(R.id.item_eli);
            avatar=itemView.findViewById(R.id.item_user_picture);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity, Answer.class);
                    intent.putExtra("token",hpPresenter.getToken());
                    intent.putExtra("title",title.getText().toString());
                    intent.putExtra("content",content.getText().toString());
                    intent.putExtra("id",id+"");
                    activity.startActivity(intent);
                }
            });

            like.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean flag;
                    if (!likeSituation) {
                        flag = clickSituation("http://bihu.jay86.com/exciting.php", 2, 1);
                    } else {
                        flag = clickSituation("http://bihu.jay86.com/cancelExciting.php", 3, 1);
                    }
                    if (flag) {
                        if (likeSituation) {
                            like.setImageResource(R.drawable.like);
                            changeNumber(likeNumber,-1);
                        } else {
                            like.setImageResource(R.drawable.exciting);
                            changeNumber(likeNumber,1);
                        }
                        likeSituation = !likeSituation;
                    }
                }
            });

            elic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final String[] items = {"收藏", "取消收藏", "不看该问题"};
                    new AlertDialog.Builder(context).setTitle("其它").setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    hpPresenter.postEN("http://bihu.jay86.com/favorite.php", id, 1, 0);
                                    break;
                                case 1:
                                    hpPresenter.postEN("http://bihu.jay86.com/cancelFavorite.php", id, 0, 0);
                                    break;
                                case 2:
                                    deleteItem(getAdapterPosition());
                                    break;
                            }
                        }
                    }).show();
                }
            });

            naive.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean flag;
                    if (!naiveSituation) {
                        flag = clickSituation("http://bihu.jay86.com/naive.php", 4, 1);
                        changeNumber(naiveNumber,1);
                    } else {
                        flag = clickSituation("http://bihu.jay86.com/cancelNaive.php", 5, 1);
                        changeNumber(naiveNumber,-1);
                    }
                    if (flag) {
                        if (naiveSituation) {
                            naive.setImageResource(R.drawable.naive);
                        } else {
                            naive.setImageResource(R.drawable.naive2);
                        }
                    }
                    naiveSituation = !naiveSituation;
                }
            });
        }

        boolean clickSituation(String address, int k, int type) {
            if (k == 1 || k == 3 || k == 5) {
                return hpPresenter.postEN(address, id, k, type);
            }
            if (likeSituation) {
                hpPresenter.showDialog("你已选择点赞");
                return false;
            }
            if (naiveSituation) {
                hpPresenter.showDialog("你已选择不同意");
                return false;
            }
            if (k == 2) {
                return hpPresenter.postEN(address, id, 2, type);
            }
            if (k == 4) {
                return hpPresenter.postEN(address, id, k, type);
            }
            return false;
        }
        void changeNumber(TextView textView,int k){
            String number=textView.getText().toString();
            int a=Integer.parseInt(number)+k;
            textView.setText(""+a);
        }
    }
}

