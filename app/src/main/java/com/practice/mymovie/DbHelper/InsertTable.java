package com.practice.mymovie.DbHelper;

import android.content.Context;
import android.widget.Toast;

import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;

import java.util.ArrayList;

import static com.practice.mymovie.DbHelper.OpenDatabase.database;

public class InsertTable {

    public static void updateMovieListTable(Context context, ArrayList<MovieMain> movieList) {
        //영화 목록 갱신을 위해 테이블을 비워준 뒤 insert 한다.
        String deleteSql = "delete from movie_list";

        String insertSql = "insert into movie_list" +
                "(id, title, title_eng, date, user_rating, audience_rating, reviewer_rating, reservation_rate, reservation_grade, grade, thumb, image) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if(database != null) {
            try {
                database.execSQL(deleteSql);
            } catch (Exception e) {
                Toast.makeText(context, "movie list table 비우기 실패 \n" +e.toString(), Toast.LENGTH_LONG).show();
                return;
            }
            try{
                for(int i = 0 ; i < movieList.size(); i++) {
                    MovieMain movieMain = movieList.get(i);
                    Object[] params = {movieMain.getId(), movieMain.getTitle(), movieMain.getTitle_eng(), movieMain.getDate(),
                                        movieMain.getUser_rating(), movieMain.getAudience_rating(), movieMain.getReviewer_rating(),
                                        movieMain.getReservation_rate(), movieMain.getReservation_grade(), movieMain.getGrade(),
                                        movieMain.getThumb(), movieMain.getImage()};

                    database.execSQL(insertSql, params);

                }
            } catch (Exception e) {
                Toast.makeText(context, "movie list insert 실패 \n" +e.toString(), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(context, "DB에 영화 목록을 저장했습니다.", Toast.LENGTH_SHORT).show();

        }

    }

    public static void updateMovieDetailTable(Context context, int movieId, MovieDetail movieDetail) {
        String insertSql = "insert into movie_detail" +
                "(movie_id, title, date, user_rating, audience_rating, reviewer_rating, reservation_rate, reservation_grade, " +
                "grade, thumb, image, photos, videos, outlinks, genre, duration, audience, synopsis, director, actor, like_num, dislike_num) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String deleteSql = "delete from movie_detail where movie_id = " + movieId;

        //해당 영화 정보가 db에 있다면 갱신을 위해 지운 후 insert 해준다.
        if(SelectTable.selectMovieDetailTable(context, movieId) != null) {
            try {
                database.execSQL(deleteSql);
            } catch (Exception e) {
                Toast.makeText(context, "movie detail delete 실패 \n" +e.toString(), Toast.LENGTH_LONG).show();
                return;
            }
        }
        try{
            Object[] params = {movieId, movieDetail.getTitle(), movieDetail.getDate(), movieDetail.getUser_rating(), movieDetail.getAudience_rating(),
                    movieDetail.getReviewer_rating(), movieDetail.getReservation_rate(), movieDetail.getReservation_grade(), movieDetail.getGrade(),
                    movieDetail.getThumb(), movieDetail.getImage(), movieDetail.getPhotos(), movieDetail.getVideos(), movieDetail.getOutlinks(), movieDetail.getGenre(),
                    movieDetail.getDuration(), movieDetail.getAudience(), movieDetail.getSynopsis(), movieDetail.getDirector(), movieDetail.getActor(),
                    movieDetail.getLike(), movieDetail.getDislike()};
            database.execSQL(insertSql, params);
        } catch (Exception e) {
            Toast.makeText(context, "movie detail insert 실패 \n" +e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(context, "DB에 영화 정보를 저장했습니다.", Toast.LENGTH_LONG).show();
    }

    public static void updateReviewTable(Context context, int movieId , ArrayList<Comment> commentList) {
        //리뷰 목록 갱신을 위해 해당 영화의 리뷰 비운 뒤 새로 저장해준다.
        String deleteSql = "delete from review where movie_id = " + movieId;

        String insertSql = "insert into review" +
                "(id, writer, movie_id, writer_image, time, timestamp, rating, contents, recommend) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            database.execSQL(deleteSql);
        } catch (Exception e) {
            Toast.makeText(context, "review table 특정 영화 한줄평 비우기 실패 \n" +e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            for(int i = 0 ; i < commentList.size(); i++) {
                Comment comment = commentList.get(i);
                Object[] params = {comment.getId(), comment.getWriter(), comment.getMovieId(), comment.getWriter_image(),
                        comment.getTime(), comment.getRating(), comment.getContents(), comment.getRecommend()};

                database.execSQL(insertSql, params);
            }
        } catch (Exception e) {
            Toast.makeText(context, "review table insert 실패 \n" +e.toString(), Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(context, "DB에 한줄평 목록을 저장했습니다.", Toast.LENGTH_LONG).show();
    }
}
