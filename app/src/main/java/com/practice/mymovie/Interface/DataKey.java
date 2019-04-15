package com.practice.mymovie.Interface;

import android.widget.ListView;

public interface DataKey {
    String MOVIE_ORDER = "MOVIE_ORDER";
    String MOVIE_COMMENT_LIST = "MOVIE_COMMENT_LIST";
    String MOVIE_LIST = "MOVIE_LIST";
    String MOVIE = "MOVIE";

    String READ_MOVIE_LIST = "/movie/readMovieList";
    String READ_MOVIE = "/movie/readMovie";
    String READ_COMMENT_LIST = "/movie/readCommentList";
    String CREATE_COMMENT = "/movie/createComment";
    String INCREASE_RECOMMEND = "/movie/increaseRecommend";
    String INCREASE_LIKE_DISLIKE = "/movie/increaseLikeDisLike";

    String ID = "id";
    String REVIEW_ID = "review_id";

}
