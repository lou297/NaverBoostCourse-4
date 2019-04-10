package com.practice.mymovie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;
import com.practice.mymovie.DataClass.ReadMovieList.ReadMovieList;
import com.practice.mymovie.Interface.DataKey;
import com.practice.mymovie.MainViewPager.MainViewPagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        startAppSetting();
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
            Toast.makeText(this, "nav_menu 영화 목록 메뉴 클릭 됨", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_movieAPI) {
            Toast.makeText(this, "nav_menu 영화 API 메뉴 클릭 됨", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_reservation) {
            Toast.makeText(this, "nav_menu 예매하기 메뉴 클릭 됨", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_userSetting) {
            Toast.makeText(this, "nav_menu 사용자 설정 메뉴 클릭 됨", Toast.LENGTH_SHORT).show();
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

    }

    private void startAppSetting() {
        //requestQueue 준비
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(this);
        }

        String[] requiredPermissions = {Manifest.permission.INTERNET};
        int requestPermissionCode = 1;
        //permission 확인
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            sendRequest();
        } else {
            ActivityCompat.requestPermissions(this, requiredPermissions, requestPermissionCode);
        }
    }

    private void sendRequest() {
        String url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processRequest(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ErrorTest", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        AppHelper.requestQueue.add(stringRequest);
    }

    private void processRequest(String response) {
        Gson gson = new Gson();
        ReadMovieList readMoiveList = gson.fromJson(response, ReadMovieList.class);
        if (readMoiveList != null) {
            ArrayList<MovieMain> movieList = readMoiveList.getResult();

            loadViewPagerView(movieList);
        }
    }

    private void loadViewPagerView(ArrayList<MovieMain> movieList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_LIST, movieList);
        mainViewPagerFragment.setArguments(bundle);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length == 1) {
            boolean check = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check = false;
                    break;
                }
            }

            if (check) {
                sendRequest();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
