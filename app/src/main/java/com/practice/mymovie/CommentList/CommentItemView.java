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
import com.practice.mymovie.AppHelper;
import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ResponseResult.ResponseResult;
import com.practice.mymovie.R;

import java.util.HashMap;
import java.util.Map;

import static com.practice.mymovie.ConstantKey.ParamsKey.PARAMS_REVIEW_ID;
import static com.practice.mymovie.ConstantKey.ServerUrl.INCREASE_RECOMMEND;

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
        if(context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_comment_item, this, true);

            tvCommentId = findViewById(R.id.tvCommentId);
            tvCommentTime = findViewById(R.id.tvCommentTime);
            commentRatingBar = findViewById(R.id.commentRatingBar_CommentItemView);
            tvComment = findViewById(R.id.tvComment);
            tvCommentNumOfRecommend = findViewById(R.id.tvCommentNumOfRecommend);

            mContext = context;
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
                    Map<String, String> params = new HashMap<>();

                    params.put(PARAMS_REVIEW_ID, String.valueOf(item.getId()));
                    sendRequest(INCREASE_RECOMMEND, params);
                }
            });
        }
    }

    private void sendRequest(String addUrl, final Map<String, String> params) {
        String url = "http://boostcourse-appapi.connect.or.kr:10000/" + addUrl;

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

        AppHelper.requestQueue.add(stringRequest);
    }

    private void processResponse(String response) {
        Gson gson = new Gson();
        ResponseResult result = gson.fromJson(response, ResponseResult.class);

        if (result != null) {
            switch (result.getCode()){
                case 200:
                    iCommentNumOfRecoomend++;
                    tvCommentNumOfRecommend.setText(String.format(mContext.getString(R.string.comment_view_recommend), iCommentNumOfRecoomend));
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case 400:
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
