package com.practice.mymovie.MainViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.mymovie.Adapter.MainViewPagerAdapter;
import com.practice.mymovie.Interface.DataKey;
import com.practice.mymovie.MainActivity;
import com.practice.mymovie.R;

import java.util.ArrayList;

public class MainViewPagerFragment extends Fragment
        implements DataKey {
    private ArrayList<MainMovieViewFragment> mMovieList;
    private ViewPager viewPager;
    private MainActivity mActivity;
    private int order = 0;


    //    임의의 영화 정보 목록
    private String[] movieTitleList = {"군도", "공조", "더 킹", "레지던트 이블", "럭키", "아수라"};
    private String[] ticketSalesList = {"33.4", "20.9", "15.9", "6.8", "4.7", "3.9"};
    private int[] ratingList = {0, 15, 19, 15, 12, 0};
    private String[] movieDateList = {"상영중", "D-1", "상영중", "D-6", "상영중", "상영중"};
//

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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main_viewpager, container, false);
        viewPager = viewGroup.findViewById(R.id.vpMovieContainer);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mMovieList = new ArrayList<>();
        Bundle bundle = getArguments();
        readArgument(bundle);
        createMovieView();
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(mActivity.getSupportFragmentManager(), mMovieList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(order);
        super.onActivityCreated(savedInstanceState);
    }

    private void createMovieView() {
        //영화 6개에 대한 각각의 정보를 fragment에 전달하여 View를 생성하고,
        // 생성된 View 목록을 ArrayList에 담는다.
        for (int i = 0; i < 6; i++) {
            MainMovieViewFragment fragment = new MainMovieViewFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(MOVIE_ORDER, i + 1);
            bundle.putString(MOVIE_TITLE, movieTitleList[i]);
            bundle.putString(TICKET_SALES, ticketSalesList[i]);
            bundle.putInt(RATING, ratingList[i]);
            bundle.putString(MOVIE_DATE, movieDateList[i]);

            fragment.setArguments(bundle);
            mMovieList.add(fragment);
        }
    }

    private void readArgument(Bundle bundle) {
        if (bundle != null) {
            order = bundle.getInt(MOVIE_ORDER,1)-1;
        }
    }
}
