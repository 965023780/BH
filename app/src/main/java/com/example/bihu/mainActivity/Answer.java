package com.example.bihu.mainActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bihu.R;
import com.example.bihu.mvp.model.AnswerModel;
import com.example.bihu.mvp.presenter.AnswerPresenter;
import com.example.bihu.mvp.view.AnswerView;

public class Answer extends AppCompatActivity {
    private Toolbar toolbar;
    private AnswerView answerView;
    private AnswerPresenter answerPresenter;
    private AnswerModel answerModel;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvBackGround;
    private String token;
    private String id;
    private String content;
    private String title;
    private TextView comment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Bundle extras = getIntent().getExtras();
        token = extras.getString("token");
        id = extras.getString("id");
        content = extras.getString("content");
        title = extras.getString("title");
        init();
        if(!answerPresenter.create(id)){
            tvBackGround.setText("");
        }
        comment.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                answerPresenter.showInputDialog(id);
            }
        });
    }

    void init() {
        toolbar = findViewById(R.id.answer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvContent=findViewById(R.id.qa_content);
        tvTitle=findViewById(R.id.qa_title);
        tvBackGround=findViewById(R.id.answer_background);
        comment=findViewById(R.id.comment);
        tvTitle.setText("提问:  "+title);
        tvContent.setText("内容： "+content);
        answerPresenter = new AnswerPresenter<AnswerModel, AnswerView>(token, Answer.this, this);
        recyclerView = findViewById(R.id.answer_rc);
        answerModel = new AnswerModel();
        answerView = new AnswerView(recyclerView, this);
        answerPresenter.registerModel(answerModel);
        answerPresenter.registerView(answerView);
    }
}
