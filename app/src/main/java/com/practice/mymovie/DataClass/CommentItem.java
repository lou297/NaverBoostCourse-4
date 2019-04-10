package com.practice.mymovie.DataClass;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentItem implements Parcelable {
    private String mId;
    private String mCommentTime;
    private float mCommentCredit;
    private String mComment;

    public CommentItem(String id, String commentTime, float commentCredit, String comment) {
        mId = id;
        mCommentTime = commentTime;
        mCommentCredit = commentCredit;
        mComment = comment;
    }

    private CommentItem(Parcel in) {
        mId = in.readString();
        mCommentTime = in.readString();
        mCommentCredit = in.readFloat();
        mComment = in.readString();
    }

    public static final Creator<CommentItem> CREATOR = new Creator<CommentItem>() {
        @Override
        public CommentItem createFromParcel(Parcel in) {
            return new CommentItem(in);
        }

        @Override
        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCommentTime);
        dest.writeFloat(mCommentCredit);
        dest.writeString(mComment);
    }

    private void readFromParcel(Parcel in){
        mId = in.readString();
        mCommentTime = in.readString();
        mCommentCredit = in.readFloat();
        mComment = in.readString();
    }

    public String getmId() {
        return mId;
    }

    public String getmCommentTime() {
        return mCommentTime;
    }

    public float getmCommentCredit() {
        return mCommentCredit;
    }

    public String getmComment() {
        return mComment;
    }
}
