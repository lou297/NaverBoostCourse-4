package com.practice.mymovie.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.practice.mymovie.DataClass.CommentItem;
import com.practice.mymovie.R;

public class CommentItemView extends LinearLayout {
    private TextView tvCommentId;
    private TextView tvCommentTime;
    private RatingBar commentRatingBar;
    private TextView tvComment;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_comment_item, this, true);

        tvCommentId = findViewById(R.id.tvCommentId);
        tvCommentTime = findViewById(R.id.tvCommentTime);
        commentRatingBar = findViewById(R.id.commentRatingBar_CommentItemView);
        tvComment = findViewById(R.id.tvComment);
    }

    public void setCommentInfo(CommentItem item) {
        String id = item.getmId();
        String time = item.getmCommentTime();
        float credit = item.getmCommentCredit();
        String comment = item.getmComment();
        tvCommentId.setText(id);
        tvCommentTime.setText(time);
        if(credit>=0 && credit<=5.0)
            commentRatingBar.setRating(credit);
        tvComment.setText(comment);
    }
}
