package com.practice.mymovie.MainViewPager.ViewPagerContent;

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

import com.bumptech.glide.Glide;
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;
import com.practice.mymovie.MainActivity;
import com.practice.mymovie.R;

import static com.practice.mymovie.ConstantKey.ConstantKey.*;

//앱 시작 후 보이는 ViewPager 영화 목록들 중 하나의 페이지를 보여주기 위한 class이다.
//MainViewPagerFragment에서 영화 정보를 bundle을 통해 넘겨 받아 화면에 띄워준다.

public class MainMovieViewFragment extends Fragment {
    private MovieMain mMovie;
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
            mMovie = bundle.getParcelable(MOVIE);
        }
    }

    private void initView(ViewGroup view) {
        //readArgument에서 받아온 영화 정보를 뷰에 넣어줌으로써 한 영화에 대한 fragment뷰를 생성해준다.
        TextView tvMovieOrderAndTitle = view.findViewById(R.id.tvMovieOrderAndTitle_Main);
        ImageView ivMoviePoster = view.findViewById(R.id.ivMoviePoster_Main);
        TextView tvMovieInfo = view.findViewById(R.id.tvMovieInfo_Main);
        Button btnDetailView = view.findViewById(R.id.btnDetailView_Main);

        //영화 제목 넣기
        String movieOrderAndTitle = String.format(getString(R.string.vp_main_movieOrderAndTitle), mMovie.getReservation_grade(), mMovie.getTitle());
        tvMovieOrderAndTitle.setText(movieOrderAndTitle);

        //영화 포스터 넣기
        String ImageUrl = mMovie.getImage();
        if(ImageUrl != null)
            Glide.with(mActivity).load(ImageUrl).into(ivMoviePoster);

        //영화 정보 넣기
        String movieInfo;

        movieInfo = String.format(getString(R.string.vp_main_movieInfo), String.valueOf(mMovie.getReservation_rate()), mMovie.getGrade(), mMovie.getDate());
        tvMovieInfo.setText(movieInfo);

        btnDetailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.goToDetailView(mMovie.getId());
            }
        });

    }



}
