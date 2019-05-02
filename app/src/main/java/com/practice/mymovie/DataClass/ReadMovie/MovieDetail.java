package com.practice.mymovie.DataClass.ReadMovie;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetail implements Parcelable {
    private String title;
    private String date;
    private double user_rating;
    private double audience_rating;
    private double reviewer_rating;
    private double reservation_rate;
    private int reservation_grade;
    private int grade;
    private String thumb;
    private String image;
    private String photos;
    private String videos;
    private String outlinks;
    private String genre;
    private int duration;
    private int audience;
    private String synopsis;
    private String director;
    private String actor;
    private int like;
    private int dislike;

    public MovieDetail(String title, String date, double user_rating, double audience_rating, double reviewer_rating,
                       double reservation_rate, int reservation_grade, int grade, String thumb, String image, String photos,
                       String videos, String outlinks, String genre, int duration, int audience, String synopsis, String director,
                       String actor, int like, int dislike) {
        this.title = title;
        this.date = date;
        this.user_rating = user_rating;
        this.audience_rating = audience_rating;
        this.reviewer_rating = reviewer_rating;
        this.reservation_rate = reservation_rate;
        this.reservation_grade = reservation_grade;
        this.grade = grade;
        this.thumb = thumb;
        this.image = image;
        this.photos = photos;
        this.videos = videos;
        this.outlinks = outlinks;
        this.genre = genre;
        this.duration = duration;
        this.audience = audience;
        this.synopsis = synopsis;
        this.director = director;
        this.actor = actor;
        this.like = like;
        this.dislike = dislike;
    }

    private MovieDetail(Parcel in) {
        title = in.readString();
        date = in.readString();
        user_rating = in.readDouble();
        audience_rating = in.readDouble();
        reviewer_rating = in.readDouble();
        reservation_rate = in.readDouble();
        reservation_grade = in.readInt();
        grade = in.readInt();
        thumb = in.readString();
        image = in.readString();
        photos = in.readString();
        videos = in.readString();
        outlinks = in.readString();
        genre = in.readString();
        duration = in.readInt();
        audience = in.readInt();
        synopsis = in.readString();
        director = in.readString();
        actor = in.readString();
        like = in.readInt();
        dislike = in.readInt();
    }

    public static final Creator<MovieDetail> CREATOR = new Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

    public String getTitle() {
        return title;
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

    public String getPhotos() {
        return photos;
    }

    public String getVideos() {
        return videos;
    }

    public String getOutlinks() {
        return outlinks;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public int getAudience() {
        return audience;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public static Creator<MovieDetail> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeDouble(user_rating);
        dest.writeDouble(audience_rating);
        dest.writeDouble(reviewer_rating);
        dest.writeDouble(reservation_rate);
        dest.writeInt(reservation_grade);
        dest.writeInt(grade);
        dest.writeString(thumb);
        dest.writeString(image);
        dest.writeString(photos);
        dest.writeString(videos);
        dest.writeString(outlinks);
        dest.writeString(genre);
        dest.writeInt(duration);
        dest.writeInt(audience);
        dest.writeString(synopsis);
        dest.writeString(director);
        dest.writeString(actor);
        dest.writeInt(like);
        dest.writeInt(dislike);
    }
}
