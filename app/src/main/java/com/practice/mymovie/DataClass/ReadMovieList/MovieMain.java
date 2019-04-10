package com.practice.mymovie.DataClass.ReadMovieList;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieMain implements Parcelable {
    private int id;
    private String title;
    private String title_eng;
    private String date;
    private double user_rating;
    private double audience_rating;
    private double reviewer_rating;
    private double reservation_rate;
    private int reservation_grade;
    private int grade;
    private String thumb;
    private String image;

    private MovieMain(Parcel in) {
        id = in.readInt();
        title = in.readString();
        title_eng = in.readString();
        date = in.readString();
        user_rating = in.readDouble();
        audience_rating = in.readDouble();
        reviewer_rating = in.readDouble();
        reservation_rate = in.readDouble();
        reservation_grade = in.readInt();
        grade = in.readInt();
        thumb = in.readString();
        image = in.readString();
    }

    public static final Creator<MovieMain> CREATOR = new Creator<MovieMain>() {
        @Override
        public MovieMain createFromParcel(Parcel in) {
            return new MovieMain(in);
        }

        @Override
        public MovieMain[] newArray(int size) {
            return new MovieMain[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public String getDate() {
        return date;
    }

    public double getUser_rating() {
        return user_rating;
    }

    public double getAudience_rating() {
        return audience_rating;
    }

    public double getReviewer_rating() {
        return reviewer_rating;
    }

    public double getReservation_rate() {
        return reservation_rate;
    }

    public int getReservation_grade() {
        return reservation_grade;
    }

    public int getGrade() {
        return grade;
    }

    public String getThumb() {
        return thumb;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(title_eng);
        dest.writeString(date);
        dest.writeDouble(user_rating);
        dest.writeDouble(audience_rating);
        dest.writeDouble(reviewer_rating);
        dest.writeDouble(reservation_rate);
        dest.writeInt(reservation_grade);
        dest.writeInt(grade);
        dest.writeString(thumb);
        dest.writeString(image);
    }
}
