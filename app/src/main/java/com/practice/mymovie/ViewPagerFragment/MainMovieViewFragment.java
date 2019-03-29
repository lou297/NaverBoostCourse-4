package com.practice.mymovie.ViewPagerFragment;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.mymovie.R;

public class MainMovieViewFragment extends Fragment {
    private int movieOrder;
    private String movieTitle;
    private String ticketPercents;
    private int viewingClass;
    private String movieDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_main_movie_view,container,false);
        readArgument();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void readArgument() {
        Bundle bundle = getArguments();
        if(bundle!=null) {
            if(bundle.containsKey("movieOrder") && bundle.containsKey("movieTitle") && bundle.containsKey("ticketPercents")
                    && bundle.containsKey("viewingClass") && bundle.containsKey("movieDate")){
                movieOrder = bundle.getInt("movieOrder");
                movieTitle = bundle.getString("movieTitle");
                ticketPercents = bundle.getString("ticketPercents");
                viewingClass = bundle.getInt("viewingClass");
                movieDate = bundle.getString("movieDate");
            }
        }
    }

    private void initView(ViewGroup view) {
        TextView movieOrderAndTitleView = view.findViewById(R.id.movieOrderAndTitle_Main);
        ImageView moviewImageView = view.findViewById(R.id.movieImageView_Main);
        TextView movieInfoView = view.findViewById(R.id.movieInfo_Main);
        Button detailViewBut = view.findViewById(R.id.detailViewBut_Main);

        //영화 제목 넣기
        String movieOrderAndTitle = String.format(getString(R.string.viewpager_main_movieOrderAndTitle),movieOrder,movieTitle);
        movieOrderAndTitleView.setText(movieOrderAndTitle);

        //영화 포스터 넣기
        switch (movieTitle){
            case "군도" :
                moviewImageView.setBackgroundResource(R.drawable.image1);
                break;
            case "공조" :
                moviewImageView.setBackgroundResource(R.drawable.image2);
                break;
            case "더 킹":
                moviewImageView.setBackgroundResource(R.drawable.image3);
                break;
            case "레지던트 이블":
                moviewImageView.setBackgroundResource(R.drawable.image4);
                break;
            case "럭키":
                moviewImageView.setBackgroundResource(R.drawable.image5);
                break;
            case "아수라":
                moviewImageView.setBackgroundResource(R.drawable.image6);
                break;
            default:
                break;
        }

        //영화 정보 넣기
        String movieInfo;
        if (viewingClass==0)
            movieInfo = String.format(getString(R.string.viewpager_main_movieInfo_all),ticketPercents,movieDate);
        else
            movieInfo = String.format(getString(R.string.viewpager_main_movieInfo),ticketPercents,viewingClass,movieDate);
        movieInfoView.setText(movieInfo);

        detailViewBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
