package com.practice.mymovie.DataClass.ReadMovie;

import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;

import java.util.ArrayList;

public class ReadMovie {
    private String message;
    private int code;
    private String resultType;
    private ArrayList<MovieDetail> result = new ArrayList<MovieDetail>();

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getResultType() {
        return resultType;
    }

    public ArrayList<MovieDetail> getResult() {
        return result;
    }
}
