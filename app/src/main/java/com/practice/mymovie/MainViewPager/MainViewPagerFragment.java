package com.practice.mymovie.MainViewPager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;
import com.practice.mymovie.MainActivity;
import com.practice.mymovie.MainViewPager.ViewPagerContent.MainMovieViewFragment;
import com.practice.mymovie.R;

import java.util.ArrayList;

import static com.practice.mymovie.ConstantKey.ConstantKey.*;

//앱 시작 후 보이는 View Pager를 담는 틀의 역할을 하는 Class이다.
//해당 클래스에서 영화 목록 각 페이지에 대한 정보를 새로 생성된 fragment로 보내준다.
//생성된 영화 목록 fragment들은 해당 클래스안에 담긴다.

public class MainViewPagerFragment extends Fragment {
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

        FragmentManager FM = mActivity.getSupportFragmentManager();
        setAdapter(FM);

        super.onActivityCreated(savedInstanceState);

    }

    private void readArgument(Bundle bundle) {
        //해당 메소드는 영화 상세화면 fragment에서 다시 viewpager화면으로 돌아온 경우를 위한 메소드이다.
        //2번째 영화의 상세보기를 눌러 들어갔다 나오면 viewpager가 2번째 영화를 다시 보여준다.
        if (bundle != null) {
            order = bundle.getInt(MOVIE_ORDER, 1) - 1;
            //영화 상세뷰에서 메인뷰로 넘어왔을 경우, 아닌 경우에도 기본 값이 있기 때문에 상관없다.
            movieList = bundle.getParcelableArrayList(MOVIE_LIST);
        }
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

    private void setAdapter(FragmentManager FM) {
        if (FM != null) {
            MainViewPagerAdapter adapter = new MainViewPagerAdapter(FM, mMovieList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(order);
        }
    }
}
