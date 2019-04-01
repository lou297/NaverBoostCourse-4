package com.practice.mymovie;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.practice.mymovie.Adapter.MainViewPagerAdapter;
import com.practice.mymovie.Interface.DataKey;
import com.practice.mymovie.MainViewPager.MainMovieViewFragment;
import com.practice.mymovie.MainViewPager.MainViewPagerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DataKey {
    private MainViewPagerFragment mainViewPagerFragment;

    //fragment에서 onBackPressed를 받기 위해 사용
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPagerFragment = new MainViewPagerFragment();
        initView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movieList) {
            Toast.makeText(this,"nav_menu 영화 목록 메뉴 클릭 됨",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_movieAPI) {
            Toast.makeText(this,"nav_menu 영화 API 메뉴 클릭 됨",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reservation) {
            Toast.makeText(this,"nav_menu 예매하기 메뉴 클릭 됨",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_userSetting) {
            Toast.makeText(this,"nav_menu 사용자 설정 메뉴 클릭 됨",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //영화 목록 ViewPager 가져오기
        getSupportFragmentManager().beginTransaction().add(R.id.flContainer_Main, mainViewPagerFragment).commit();
    }

    public void goToDetailView(String movieTitle) {
        MovieDetailViewFragment movieDetailViewFragment = new MovieDetailViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_TITLE, movieTitle);
        movieDetailViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer_Main, movieDetailViewFragment).commit();

        toolbar.setTitle(getString(R.string.main_toolbar_detail));
    }

    public void backToMainView(int order) {
        //메인 뷰로 돌아온다.
        mainViewPagerFragment = new MainViewPagerFragment();
        //bundle에 영화 상세 화면 순서를 넣어주어서 돌아왔을 때 viewpager가 해당 영화 페이지를 보여주게 한다.
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ORDER, order);
        mainViewPagerFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer_Main, mainViewPagerFragment).commit();
        toolbar.setTitle(getString(R.string.main_toolbar_title));
    }

    public interface onKeyBackPressedListener {
        void onBackKey();
    }

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

}
