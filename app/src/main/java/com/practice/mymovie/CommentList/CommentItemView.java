package com.practice.mymovie.CommentList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.practice.mymovie.DataClass.ResponseResult.ResponseResult;
import com.practice.mymovie.NetWork.NetworkHelper;
import com.practice.mymovie.R;

import java.util.HashMap;
import java.util.Map;

import static com.practice.mymovie.ConstantKey.ParamsKey.PARAMS_REVIEW_ID;
import static com.practice.mymovie.ConstantKey.ServerUrl.INCREASE_RECOMMEND;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.*;
import static com.practice.mymovie.ConstantKey.ServerUrl.*;

public class CommentItemView extends LinearLayout {
    private TextView tvCommentId;
    private TextView tvCommentTime;
    private RatingBar commentRatingBar;
    private TextView tvComment;
    private TextView tvCommentNumOfRecommend;

    private Context mContext;
    private int iCommentNumOfRecoomend;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if(mContext == null) {
            mContext = getContext();
        }
        if(mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_comment_item, this, true);

            tvCommentId = findViewById(R.id.tvCommentId);
            tvCommentTime = findViewById(R.id.tvCommentTime);
            commentRatingBar = findViewById(R.id.commentRatingBar_CommentItemView);
            tvComment = findViewById(R.id.tvComment);
            tvCommentNumOfRecommend = findViewById(R.id.tvCommentNumOfRecommend);
        }
    }

    public void setCommentInfo(final Comment item) {
        if(item != null) {
            String id = item.getWriter();
            String time = item.getTime();
            float credit = (float) item.getRating();
            String comment = item.getContents();
            tvCommentId.setText(id);
            tvCommentTime.setText(time);
            if (credit >= 0 && credit <= 5.0)
                commentRatingBar.setRating(credit);
            tvComment.setText(comment);
            iCommentNumOfRecoomend = item.getRecommend();

            tvCommentNumOfRecommend.setText(String.format(mContext.getString(R.string.comment_view_recommend), iCommentNumOfRecoomend));

            tvCommentNumOfRecommend.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //한줄평의 '추천 %d'의 글자를 누르면 한줄평 추천 수가 올라간다.
                    startRecommendButClickEvent(String.valueOf(item.getId()));
                }
            });
        }
    }

    private void startRecommendButClickEvent(String review_id) {
        Map<String, String> params = new HashMap<>();

        params.put(PARAMS_REVIEW_ID, review_id);
        sendRequest(INCREASE_RECOMMEND, params);
    }

    private void sendRequest(String addUrl, final Map<String, String> params) {
        String url = MAIN_URL + addUrl;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Comment Adapter", "요청 실패");
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

    private void processResponse(String response) {
        Gson gson = new Gson();
        ResponseResult result = gson.fromJson(response, ResponseResult.class);

        if (result != null) {
            switch (result.getCode()){
                case NETWORK_RESULT_OK:
                    iCommentNumOfRecoomend++;
                    tvCommentNumOfRecommend.setText(String.format(mContext.getString(R.string.comment_view_recommend), iCommentNumOfRecoomend));
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case NETWORK_RESULT_FAIL:
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
