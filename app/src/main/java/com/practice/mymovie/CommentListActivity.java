package com.practice.mymovie;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.mymovie.Adapter.CommentAdapter;
import com.practice.mymovie.DataClass.CommentItem;
import com.practice.mymovie.Interface.DataKey;
import com.practice.mymovie.R;

import java.util.ArrayList;

public class CommentListActivity extends AppCompatActivity
        implements DataKey {
    private TextView tvMovieTitle;
    private ImageView ivMovieRating;
    private RatingBar commentRatingBar;
    private TextView tvMovieCredits;
    private ListView commentListView;
    private TextView tvWriteComment;

    private String mMovieTitle;
    private int mMovieRating;
    private ArrayList<CommentItem> mCommentList;

    private final static int WRITE_COMMENT_FROM_COMMENT_LIST = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        initView();
        Intent intent = getIntent();
        readIntent(intent);
        loadComment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == WRITE_COMMENT_FROM_COMMENT_LIST) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "한줄평 작성 저장 버튼 눌림", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "한줄평 작성 취소됨", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        toolbar에서 뒤로가기 버튼 클릭 시 동작
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
//        toolbar 뒤로가기 버튼 생성
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        tvMovieTitle = findViewById(R.id.tvMovieTitle_CommentList);
        ivMovieRating = findViewById(R.id.ivMovieRating_CommentList);
        commentRatingBar = findViewById(R.id.commentRatingBar_CommentList);
        tvMovieCredits = findViewById(R.id.tvMovieCredits_CommentList);
        commentListView = findViewById(R.id.commentListView_CommentList);
        tvWriteComment = findViewById(R.id.tvWriteComment_CommentList);

        tvWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWriteComment();
            }
        });
    }

    private void readIntent(Intent intent) {
        if (intent != null) {
            mMovieTitle = intent.getStringExtra(MOVIE_TITLE);
            mMovieRating = intent.getIntExtra(RATING, R.drawable.ic_all);
            mCommentList = intent.getParcelableArrayListExtra(MOVIE_COMMENT_LIST);

            tvMovieTitle.setText(mMovieTitle);
            ivMovieRating.setBackgroundResource(mMovieRating);
        }
    }

    private void loadComment() {
        //한줄평 목록에서 평점 평균을 내서 ratingBar를 설정해주고. 그 외 정보들을 넣어준다.ex) 한줄평 작성자 수
        String movieCredits;
        if (mCommentList != null) {
            CommentAdapter adapter = new CommentAdapter(this, mCommentList);
            commentListView.setAdapter(adapter);

            int numOfReviewers = mCommentList.size();
            float total = 0;
            for (int i = 0; i < numOfReviewers; i++) {
                total += mCommentList.get(i).getmCommentCredit();
            }
            float average = total / (float) numOfReviewers;
            average = Math.round((average*100)/100.0);

            commentRatingBar.setRating(average);
            movieCredits = String.format(getString(R.string.comment_list_credits), String.valueOf(average), numOfReviewers);
        } else {
            movieCredits = String.format(getString(R.string.comment_list_credits), "0", 0);
        }
        tvMovieCredits.setText(movieCredits);
    }

    private void goToWriteComment() {
        Intent intent = new Intent(this, CommentWriteActivity.class);
        intent.putExtra(MOVIE_TITLE, mMovieTitle);
        intent.putExtra(RATING, mMovieRating);
        startActivityForResult(intent, WRITE_COMMENT_FROM_COMMENT_LIST);
    }
}
