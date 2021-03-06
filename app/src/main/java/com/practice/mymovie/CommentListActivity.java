package com.practice.mymovie;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.mymovie.CommentList.CommentAdapter;
import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;

import java.util.ArrayList;

import static com.practice.mymovie.ConstantKey.ConstantKey.*;
import static com.practice.mymovie.ConstantKey.ActivityResultKey.*;
import static com.practice.mymovie.ConstantKey.MovieGrade.*;

//한줄 평 목록 모두 보기 클릭 시 띄워지는 class이다.
//해당 class에서 전체 한줄 평 목록을 볼 수 있다.

public class CommentListActivity extends AppCompatActivity {
    private TextView tvMovieTitle;
    private ImageView ivMovieRating;
    private RatingBar commentRatingBar;
    private TextView tvMovieCredits;
    private ListView commentListView;
    private TextView tvWriteComment;

    private MovieDetail mMovie;
    private String mMovieId;

    private ArrayList<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;




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
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Toast.makeText(this, getString(R.string.write_comment_fail), Toast.LENGTH_SHORT).show();
                } else {
                    Comment comment = data.getParcelableExtra(COMMENT);
                    mCommentList.add(0, comment);
                    mCommentAdapter.notifyDataSetChanged();
                    Toast.makeText(this, getString(R.string.write_comment_success), Toast.LENGTH_SHORT).show();
                }
            }
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
        //영화에 대한 정보를 intent를 통해 가져와 화면에 보여준다.
        if (intent != null) {
            mMovie = intent.getParcelableExtra(MOVIE);
            mMovieId = intent.getStringExtra(ID);
            mCommentList = intent.getParcelableArrayListExtra(MOVIE_COMMENT_LIST);

            tvMovieTitle.setText(mMovie.getTitle());
            switch (mMovie.getGrade()) {
                case GRADE_12:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_12);
                    break;
                case GRADE_15:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_15);
                    break;
                case GRADE_19:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_19);
                    break;
                default:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_all);
                    break;
            }
        }
    }

    private void loadComment() {
        //한줄평 목록에서 평점 평균을 내서 ratingBar를 설정해주고. 그 외 정보들을 넣어준다.ex) 한줄평 작성자 수
        String movieCredits;
        if (mCommentList != null) {
            mCommentAdapter = new CommentAdapter(this, mCommentList);
            commentListView.setAdapter(mCommentAdapter);

            int numOfReviewers = mCommentList.size();
            float total = 0;
            for (int i = 0; i < numOfReviewers; i++) {
                total += mCommentList.get(i).getRating();
            }
            float average = total / (float) numOfReviewers;

            commentRatingBar.setRating(average);
            movieCredits = String.format(getString(R.string.comment_list_credits), String.format("%.2f", average), numOfReviewers);
        } else {
            movieCredits = String.format(getString(R.string.comment_list_credits), "0", 0);
        }
        tvMovieCredits.setText(movieCredits);
    }

    private void goToWriteComment() {
        Intent intent = new Intent(this, CommentWriteActivity.class);
        intent.putExtra(MOVIE, mMovie);
        intent.putExtra(ID, mMovieId);
        startActivityForResult(intent, WRITE_COMMENT_FROM_COMMENT_LIST);
    }
}
