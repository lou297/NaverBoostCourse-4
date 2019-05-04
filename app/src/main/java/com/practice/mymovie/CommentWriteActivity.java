package com.practice.mymovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ResponseResult.ResponseResult;
import com.practice.mymovie.NetWork.NetworkHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.practice.mymovie.ConstantKey.ParamsKey.*;
import static com.practice.mymovie.ConstantKey.ConstantKey.*;
import static com.practice.mymovie.ConstantKey.ServerUrl.*;
import static com.practice.mymovie.ConstantKey.MovieGrade.*;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.*;

//한줄평 작성하기 버튼 클릭 시 실행되는 class이다.

public class CommentWriteActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;

    private TextView tvMovieTitle;
    private ImageView ivMovieRating;
    private MovieDetail mMovie;
    private String mMovieId;

    private RatingBar RatingBar;
    private EditText etComment;

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
        RatingBar = findViewById(R.id.commentRatingBar_CommentWrite);
        etComment = findViewById(R.id.etComment_CommentWrite);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContent();
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
            mMovie = intent.getParcelableExtra(MOVIE);
            mMovieId = intent.getStringExtra(ID);

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

    private void saveContent() {
        boolean checkContent = true;
        if (RatingBar.getRating() < 0.5F) {
            Toast.makeText(this, getString(R.string.rating_point_min), Toast.LENGTH_SHORT).show();
            checkContent = false;
        }
        if (etComment.getText() == null || etComment.getText().toString().length() < 3) {
            Toast.makeText(this, getString(R.string.comment_length_min), Toast.LENGTH_SHORT).show();
            checkContent = false;
        }

        if (checkContent) {
            Map<String, String> params = new HashMap<>();
            Comment comment = getComment();
            params.put(PARAMS_ID, String.valueOf(comment.getId()));
            params.put(PARAMS_WRITER, comment.getWriter());
            params.put(PARAMS_RATING, String.valueOf(comment.getRating()));
            params.put(PARAMS_CONTENTS, comment.getContents());

            sendRequest(CREATE_COMMENT, params, comment);
        }
    }

    private Comment getComment() {
        int id = Integer.parseInt(mMovieId);
        String writer = "gildong";
        String writerImage = null;
        double rating = (double) RatingBar.getRating();

        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date(timeStamp));

        int recommend = 0;
        String contents = etComment.getText().toString();

        return new Comment(id, writer, id, writerImage, time, timeStamp, rating, contents, recommend);
    }

    private void sendRequest(String addUrl, final Map<String, String> params, final Comment comment) {
        String url = MAIN_URL + addUrl;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response, comment);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                } else {
                    return new HashMap<>();
                }
            }
        };
        NetworkHelper.requestQueue.add(stringRequest);
    }

    private void processResponse(String response, Comment comment) {
        Gson gson = new Gson();
        ResponseResult responseResult = gson.fromJson(response, ResponseResult.class);
        if (responseResult != null) {
            switch (responseResult.getCode()){
                case NETWORK_RESULT_OK:
                    Intent intent = new Intent();
                    intent.putExtra(COMMENT, comment);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case NETWORK_RESULT_FAIL:
                    Toast.makeText(this, getString(R.string.write_comment_request_fail), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
