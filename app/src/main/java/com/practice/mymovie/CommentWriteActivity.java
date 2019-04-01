package com.practice.mymovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.mymovie.Interface.DataKey;

public class CommentWriteActivity extends AppCompatActivity
        implements DataKey {
    private Button btnSave;
    private Button btnCancel;
    private TextView tvMovieTitle;
    private ImageView ivMovieRating;
    private String mMovieTitle;
    private int mMovieRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);
        initView();
        Intent intent = getIntent();
        readIntent(intent);
    }

    private void initView() {
        btnSave = findViewById(R.id.btnSave_CommentWrite);
        btnCancel = findViewById(R.id.btnCancel_CommentWrite);
        tvMovieTitle = findViewById(R.id.tvMovieTitle_CommentWrite);
        ivMovieRating = findViewById(R.id.ivMovieRating_CommentWrite);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void readIntent(Intent intent) {
//        영화 제목과 관람가를 intent로 받아 설정해준다.
        if (intent != null) {
            mMovieTitle = intent.getStringExtra(MOVIE_TITLE);
            mMovieRating = intent.getIntExtra(RATING, 0);

            tvMovieTitle.setText(mMovieTitle);
            ivMovieRating.setBackgroundResource(mMovieRating);
        }
    }
}
