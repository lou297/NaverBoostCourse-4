package com.practice.mymovie.DataClass.ReadCommentList;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private int id;
    private String writer;
    private int movieId;
    private String writer_image;
    private String time;
    private long timestamp;
    private double rating;
    private String contents;
    private int recommend;

    public Comment(int id, String writer, int movieId, String writer_image, String time, long timestamp, double rating, String contents, int recommend) {
        this.id = id;
        this.writer = writer;
        this.movieId = movieId;
        this.writer_image = writer_image;
        this.time = time;
        this.timestamp = timestamp;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    private Comment(Parcel in) {
        id = in.readInt();
        writer = in.readString();
        movieId = in.readInt();
        writer_image = in.readString();
        time = in.readString();
        timestamp = in.readLong();
        rating = in.readDouble();
        contents = in.readString();
        recommend = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getWriter_image() {
        return writer_image;
    }

    public String getTime() {
        return time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getRating() {
        return rating;
    }

    public String getContents() {
        return contents;
    }

    public int getRecommend() {
        return recommend;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(writer);
        dest.writeInt(movieId);
        dest.writeString(writer_image);
        dest.writeString(time);
        dest.writeLong(timestamp);
        dest.writeDouble(rating);
        dest.writeString(contents);
        dest.writeInt(recommend);
    }
}
