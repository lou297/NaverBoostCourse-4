package com.practice.mymovie.DataClass.ReadMovieList;

import java.util.ArrayList;

public class ReadMovieList {
    private String message;
    private int code;
    private String resultType;
    private ArrayList<MovieMain> result = new ArrayList<MovieMain>();

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getResultType() {
        return resultType;
    }

    public ArrayList<MovieMain> getResult() {
        return result;
    }
}
