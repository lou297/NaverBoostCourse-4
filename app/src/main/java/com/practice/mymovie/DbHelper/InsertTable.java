package com.practice.mymovie.DbHelper;

import android.content.Context;
import android.widget.Toast;

import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;
import com.practice.mymovie.R;

import java.util.ArrayList;

import static com.practice.mymovie.DbHelper.OpenDatabase.database;

public class InsertTable {

    //table insert와 관련된 메소드들을 저장해놓은 클래스이다.
    //기존에 저장된 데이터가 있는 경우에 기존 데이터를 delete해준 후 다시 저장해주는 경우가 있으므로
    //메소드 이름을 insert가 아닌 update라고 지정해주었다.

    public static void updateMovieListTable(Context context, ArrayList<MovieMain> movieList) {
        //영화 목록 갱신을 위해 테이블을 비워준 뒤 insert 한다.
        String deleteSql = "delete from movie_list";

        String insertSql = "insert into movie_list" +
                "(id, title, title_eng, date, user_rating, audience_rating, reviewer_rating, reservation_rate, reservation_grade, grade, thumb, image) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (database != null) {
            try {
                database.execSQL(deleteSql);
            } catch (Exception e) {
                if (context != null)
                    Toast.makeText(context, context.getString(R.string.delete_table_fail_movie_list) + e.toString(), Toast.LENGTH_LONG).show();
                return;
            }
            try {
                for (int i = 0; i < movieList.size(); i++) {
                    MovieMain movieMain = movieList.get(i);
                    Object[] params = {movieMain.getId(), movieMain.getTitle(), movieMain.getTitle_eng(), movieMain.getDate(),
                            movieMain.getUser_rating(), movieMain.getAudience_rating(), movieMain.getReviewer_rating(),
                            movieMain.getReservation_rate(), movieMain.getReservation_grade(), movieMain.getGrade(),
                            movieMain.getThumb(), movieMain.getImage()};

                    database.execSQL(insertSql, params);

                }
            } catch (Exception e) {
                if (context != null)
                    Toast.makeText(context, context.getString(R.string.insert_table_fail_movie_list) + e.toString(), Toast.LENGTH_LONG).show();
                return;
            }
            if (context != null)
                Toast.makeText(context, context.getString(R.string.save_table_success_movie_list), Toast.LENGTH_SHORT).show();

        }

    }

    public static void updateMovieDetailTable(Context context, int movieId, MovieDetail movieDetail) {
        String insertSql = "insert into movie_detail" +
                "(movie_id, title, date, user_rating, audience_rating, reviewer_rating, reservation_rate, reservation_grade, " +
                "grade, thumb, image, photos, videos, outlinks, genre, duration, audience, synopsis, director, actor, like_num, dislike_num) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String deleteSql = "delete from movie_detail where movie_id = " + movieId;

        //해당 영화 정보가 db에 있다면 갱신을 위해 지운 후 insert 해준다.
        if (SelectTable.selectMovieDetailTable(context, movieId) != null) {
            try {
                database.execSQL(deleteSql);
            } catch (Exception e) {
                if (context != null)
                    Toast.makeText(context, context.getString(R.string.delete_table_fail_movie_detail) + e.toString(), Toast.LENGTH_LONG).show();
                return;
            }
        }
        try {
            Object[] params = {movieId, movieDetail.getTitle(), movieDetail.getDate(), movieDetail.getUser_rating(), movieDetail.getAudience_rating(),
                    movieDetail.getReviewer_rating(), movieDetail.getReservation_rate(), movieDetail.getReservation_grade(), movieDetail.getGrade(),
                    movieDetail.getThumb(), movieDetail.getImage(), movieDetail.getPhotos(), movieDetail.getVideos(), movieDetail.getOutlinks(), movieDetail.getGenre(),
                    movieDetail.getDuration(), movieDetail.getAudience(), movieDetail.getSynopsis(), movieDetail.getDirector(), movieDetail.getActor(),
                    movieDetail.getLike(), movieDetail.getDislike()};
            database.execSQL(insertSql, params);
        } catch (Exception e) {
            if (context != null)
                Toast.makeText(context, context.getString(R.string.insert_table_fail_movie_detail) + e.toString(), Toast.LENGTH_LONG).show();
            return;
        }
        if (context != null)
            Toast.makeText(context, context.getString(R.string.save_table_success_movie_detail), Toast.LENGTH_LONG).show();
    }

    public static void updateReviewTable(Context context, int movieId, ArrayList<Comment> commentList) {
        //리뷰 목록 갱신을 위해 해당 영화의 리뷰 비운 뒤 새로 저장해준다.
        String deleteSql = "delete from review where movie_id = " + movieId;

        String insertSql = "insert into review" +
                "(id, writer, movie_id, writer_image, time, timestamp, rating, contents, recommend) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            database.execSQL(deleteSql);
        } catch (Exception e) {
            if (context != null)
                Toast.makeText(context, context.getString(R.string.delete_table_fail_review) + e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            for (int i = 0; i < commentList.size(); i++) {
                Comment comment = commentList.get(i);
                Object[] params = {comment.getId(), comment.getWriter(), comment.getMovieId(), comment.getWriter_image(),
                        comment.getTime(), comment.getTimestamp(), comment.getRating(), comment.getContents(), comment.getRecommend()};

                database.execSQL(insertSql, params);
            }
        } catch (Exception e) {
            if (context != null)
                Toast.makeText(context, context.getString(R.string.insert_table_fail_review) + e.toString(), Toast.LENGTH_LONG).show();
            return;
        }
        if (context != null)
            Toast.makeText(context, context.getString(R.string.save_table_success_review), Toast.LENGTH_LONG).show();
    }
}
