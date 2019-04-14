package com.practice.mymovie.DataClass.ReadCommentList;



import java.util.ArrayList;

public class ReadCommentList {
    private String message;
    private int code;
    private int totalCount;
    private ArrayList<Comment> result;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<com.practice.mymovie.DataClass.ReadCommentList.Comment> getResult() {
        return result;
    }
}
