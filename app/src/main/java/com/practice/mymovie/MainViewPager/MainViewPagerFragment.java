package com.practice.mymovie.MainViewPager;

import android.content.Context;
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
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;
import com.practice.mymovie.Interface.DataKey;
import com.practice.mymovie.MainActivity;
import com.practice.mymovie.R;

import java.util.ArrayList;

public class MainViewPagerFragment extends Fragment
        implements DataKey {
    private ArrayList<MainMovieViewFragment> mMovieList;
    private ViewPager viewPager;
    private MainActivity mActivity;
    private int order;
    private ArrayList<MovieMain> movieList = new ArrayList<>();


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
        for (MovieMain movie : movieList) {
            MainMovieViewFragment fragment = new MainMovieViewFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable(MOVIE, movie);

            fragment.setArguments(bundle);
            mMovieList.add(fragment);
        }
    }

    private void readArgument(Bundle bundle) {
        if (bundle != null) {
            order = bundle.getInt(MOVIE_ORDER,1)-1;
            //영화 상세뷰에서 메인뷰로 넘어왔을 경우, 아닌 경우에도 기본 값이 있기 때문에 상관없다.
            movieList = bundle.getParcelableArrayList(MOVIE_LIST);
            Log.d("viewpagerTest",movieList.size()+"");
        }
    }
}
