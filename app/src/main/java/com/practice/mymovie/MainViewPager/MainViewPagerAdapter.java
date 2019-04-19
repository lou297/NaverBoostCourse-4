package com.practice.mymovie.MainViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.practice.mymovie.MainViewPager.ViewPagerContent.MainMovieViewFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<MainMovieViewFragment> list;
    public MainViewPagerAdapter(FragmentManager fm, ArrayList<MainMovieViewFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
