package com.practice.mymovie.DbHelper;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;

import java.util.ArrayList;

import static com.practice.mymovie.DbHelper.OpenDatabase.database;

public class SelectTable {

    public static ArrayList<MovieMain> selectMovieListTable(Context context) {
        String selectSql = "select id, title, title_eng, date, user_rating, audience_rating, reviewer_rating, " +
                "reservation_rate, reservation_grade, grade, thumb, image " +
                "from movie_list";
        ArrayList<MovieMain> movieList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(selectSql, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String title_eng = cursor.getString(2);
                String date = cursor.getString(3);
                Double user_rating = cursor.getDouble(4);
                Double audience_rating = cursor.getDouble(5);
                Double reviewer_rating = cursor.getDouble(6);
                Double reservation_rate = cursor.getDouble(7);
                int reservation_grage = cursor.getInt(8);
                int grage = cursor.getInt(9);
                String thumb = cursor.getString(10);
                String image = cursor.getString(11);
                MovieMain movieMain = new MovieMain(id, title, title_eng, date, user_rating, audience_rating, reviewer_rating, reservation_rate, reservation_grage, grage, thumb, image);
                movieList.add(movieMain);
            }
        } catch (Exception e) {
            Toast.makeText(context, "movie_list table select 실패\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        return movieList;
    }

    public static MovieDetail selectMovieDetailTable(Context context, int movieId) {
        String selectSql = "select movie_id, title, date, user_rating, audience_rating, reviewer_rating, reservation_rate, " +
                "reservation_grade, grade, thumb, image, photos, videos, outlinks, genre, duration, audience, " +
                "synopsis, director, actor, like_num, dislike_num " +
                "from movie_detail where movie_id = ?";
        MovieDetail movieDetail = null;
        try {
            String[] params = {String.valueOf(movieId)};
            Cursor cursor = database.rawQuery(selectSql, params);
            while(cursor.moveToNext()) {
                int movie_id = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                double user_rating = cursor.getDouble(3);
                double audience_rating = cursor.getDouble(4);
                double reviewer_rating = cursor.getDouble(5);
                double reservation_rate = cursor.getDouble(6);
                int reservation_grage = cursor.getInt(7);
                int grade = cursor.getInt(8);
                String thumb = cursor.getString(9);
                String image = cursor.getString(10);
                String photos = cursor.getString(11);
                String videos = cursor.getString(12);
                String outlinks = cursor.getString(13);
                String genre = cursor.getString(14);
                int duration = cursor.getInt(15);
                int audience = cursor.getInt(16);
                String synopsis = cursor.getString(17);
                String director = cursor.getString(18);
                String actor = cursor.getString(19);
                int like_num = cursor.getInt(20);
                int dislike_num = cursor.getInt(21);

                movieDetail = new MovieDetail(title, date, user_rating, audience_rating, reviewer_rating, reservation_rate, reservation_grage, grade,
                        thumb, image, photos, videos, outlinks, genre, duration, audience, synopsis, director, actor, like_num, dislike_num);
            }
        } catch (Exception e) {
            Toast.makeText(context, "movie detail table select 실패\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        return movieDetail;
    }

    public static ArrayList<Comment> selectReviewTable(Context context, int movieId) {
        String selectSql = "select id, writer, movie_id, writer_image, time, timestamp, rating, contents, recommend from review where movie_id = ?";
        ArrayList<Comment> commentList = new ArrayList<>();

        try {
            String[] params = {String.valueOf(movieId)};
            Cursor cursor = database.rawQuery(selectSql, params);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String writer = cursor.getString(1);
                int movie_id = cursor.getInt(2);
                String writer_image = cursor.getString(3);
                String time = cursor.getString(4);
                long timestamp = cursor.getLong(5);
                double rating = cursor.getDouble(6);
                String contents = cursor.getString(7);
                int recommend = cursor.getInt(8);

                Comment comment = new Comment(id, writer, movie_id, writer_image, time, timestamp, rating, contents, recommend);
                commentList.add(comment);
            }
        } catch (Exception e) {
            Toast.makeText(context, "review table select 실패\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        return commentList;
    }
}
