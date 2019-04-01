package com.practice.mymovie.MainViewPager;

import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.mymovie.Interface.DataKey;
import com.practice.mymovie.MainActivity;
import com.practice.mymovie.R;

public class MainMovieViewFragment extends
        Fragment implements DataKey {
    private int mMovieOrder;
    private String mMovieTitle;
    private String mTicketSales;
    private int mRating;
    private String mMovieDate;
    private MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        mActivity = (MainActivity) getActivity();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if (mActivity != null)
            mActivity = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_main_movie_view, container, false);
        Bundle bundle = getArguments();
        readArgument(bundle);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void readArgument(Bundle bundle) {
        //영화 하나의 대한 정보를 받아온다.
        if (bundle != null) {
            mMovieOrder = bundle.getInt(MOVIE_ORDER);
            mMovieTitle = bundle.getString(MOVIE_TITLE);
            mTicketSales = bundle.getString(TICKET_SALES);
            mRating = bundle.getInt(RATING);
            mMovieDate = bundle.getString(MOVIE_DATE);
        }
    }

    private void initView(ViewGroup view) {
        //readArgument에서 받아온 영화 정보를 뷰에 넣어줌으로써 한 영화에 대한 fragment뷰를 생성해준다.
        TextView tvMovieOrderAndTitle = view.findViewById(R.id.tvMovieOrderAndTitle_Main);
        ImageView ivMoviePoster = view.findViewById(R.id.ivMoviePoster_Main);
        TextView tvMovieInfo = view.findViewById(R.id.tvMovieInfo_Main);
        Button btnDetailView = view.findViewById(R.id.btnDetailView_Main);

        //영화 제목 넣기
        String movieOrderAndTitle = String.format(getString(R.string.vp_main_movieOrderAndTitle), mMovieOrder, mMovieTitle);
        tvMovieOrderAndTitle.setText(movieOrderAndTitle);

        //영화 포스터 넣기
        switch (mMovieTitle) {
            case "군도":
                ivMoviePoster.setBackgroundResource(R.drawable.image1);
                break;
            case "공조":
                ivMoviePoster.setBackgroundResource(R.drawable.image2);
                break;
            case "더 킹":
                ivMoviePoster.setBackgroundResource(R.drawable.image3);
                break;
            case "레지던트 이블":
                ivMoviePoster.setBackgroundResource(R.drawable.image4);
                break;
            case "럭키":
                ivMoviePoster.setBackgroundResource(R.drawable.image5);
                break;
            case "아수라":
                ivMoviePoster.setBackgroundResource(R.drawable.image6);
                break;
            default:
                break;
        }

        //영화 정보 넣기
        String movieInfo;
        if (mRating == 0)
            movieInfo = String.format(getString(R.string.vp_main_movieInfo_all), mTicketSales, mMovieDate);
        else
            movieInfo = String.format(getString(R.string.vp_main_movieInfo), mTicketSales, mRating, mMovieDate);
        tvMovieInfo.setText(movieInfo);

        btnDetailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.goToDetailView(mMovieTitle);
            }
        });

    }


}