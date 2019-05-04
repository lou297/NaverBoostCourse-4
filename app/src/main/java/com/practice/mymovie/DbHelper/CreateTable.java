package com.practice.mymovie.DbHelper;

import android.content.Context;
import android.widget.Toast;

import com.practice.mymovie.R;

import static com.practice.mymovie.DbHelper.OpenDatabase.database;

public class CreateTable {

    /////////////////////////// Table create 관련 메소드///////////////////////////////////////////////
    public static void createMovieListTable(Context context) {
        //movie list table을 생성해 준다.
        String createTableSql =
                "create table if not exists movie_list" +
                        "(" +
                        "key_id             integer PRIMARY KEY autoincrement, " +
                        "id                 integer, " +
                        "title              text, " +
                        "title_eng          text, " +
                        "date               text, " +
                        "user_rating        double, " +
                        "audience_rating    double, " +
                        "reviewer_rating    double, " +
                        "reservation_rate   double, " +
                        "reservation_grade  integer, " +
                        "grade              integer, " +
                        "thumb              text, " +
                        "image              text" +
                        ")";
        if(database != null) {
            try{
                database.execSQL(createTableSql);
            } catch (Exception e) {
                if(context != null)
                    Toast.makeText(context, context.getString(R.string.create_table_fail_movie_list) + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            if(context != null)
                Toast.makeText(context, context.getString(R.string.database_is_null), Toast.LENGTH_SHORT).show();
        }
    }

    public static void createMovieDetailTable(Context context) {
        //movie detail table을 생성해 준다.

        //네트워크에 연결 되는 경우, db가 갱신될 가능성이 있다.
        //이때 db에 title명이 이미 존재하는 경우 추가가 아닌 갱신을 하기 위해 title을 기본키로 넣는다.
        String createTableSql =
                "create table if not exists movie_detail" +
                        "(" +
                        "movie_id           integer PRIMARY KEY, " +
                        "title              text, " +
                        "date               text, " +
                        "user_rating        double, " +
                        "audience_rating    double, " +
                        "reviewer_rating    double, " +
                        "reservation_rate   double, " +
                        "reservation_grade  integer, " +
                        "grade              integer, " +
                        "thumb              text, " +
                        "image              text, " +
                        "photos             text, " +
                        "videos             text, " +
                        "outlinks           text, " +
                        "genre              text, " +
                        "duration           integer, " +
                        "audience           integer, " +
                        "synopsis           text, " +
                        "director           text, " +
                        "actor              text, " +
                        "like_num               integer, " +
                        "dislike_num            integer" +
                        ")";

        if(database != null) {
            try{
                database.execSQL(createTableSql);
            } catch (Exception e) {
                if(context != null)
                    Toast.makeText(context, context.getString(R.string.create_table_fail_movie_detail) + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            if(context != null)
                Toast.makeText(context, context.getString(R.string.database_is_null), Toast.LENGTH_SHORT).show();
        }
    }

    public static void createReviewTable(Context context) {
        //review table을 생성해 준다.
        String createTableSql = "create table if not exists review" +
                "(" +
                "key_id             integer PRIMARY KEY autoincrement, " +
                "id                 integer, " +
                "writer             text, " +
                "movie_id           integer, " +
                "writer_image       text, " +
                "time               text, " +
                "timestamp          integer, " +
                "rating             double, " +
                "contents           text, " +
                "recommend          integer" +
                ")";

        if(database != null) {
            try{
                database.execSQL(createTableSql);
            } catch (Exception e) {
                if(context != null)
                    Toast.makeText(context, context.getString(R.string.create_table_fail_review) + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            if(context != null)
                Toast.makeText(context, context.getString(R.string.database_is_null), Toast.LENGTH_SHORT).show();
        }
    }

}
