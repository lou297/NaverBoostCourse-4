package com.practice.mymovie.ConstantKey;

public final class ServerUrl {
    //서버 요청 url 목록
    public static final String MAIN_URL = "http://boostcourse-appapi.connect.or.kr:10000";

    public static final String READ_MOVIE_LIST = "/movie/readMovieList";
    public static final String READ_MOVIE = "/movie/readMovie";
    public static final String READ_COMMENT_LIST = "/movie/readCommentList";
    public static final String CREATE_COMMENT = "/movie/createComment";
    public static final String INCREASE_RECOMMEND = "/movie/increaseRecommend";
    public static final String INCREASE_LIKE_DISLIKE = "/movie/increaseLikeDisLike";
}
