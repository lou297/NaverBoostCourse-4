package com.practice.mymovie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ReadMovie.ReadMovie;
import com.practice.mymovie.DataClass.ReadMovieList.MovieMain;
import com.practice.mymovie.DataClass.ReadMovieList.ReadMovieList;
import com.practice.mymovie.DbHelper.CreateTable;
import com.practice.mymovie.DbHelper.InsertTable;
import com.practice.mymovie.DbHelper.OpenDatabase;
import com.practice.mymovie.DbHelper.SelectTable;
import com.practice.mymovie.MainViewPager.MainViewPagerFragment;
import com.practice.mymovie.NetWork.NetworkHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.practice.mymovie.ConstantKey.DatabaseKey.DATABASE_NAME;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_NOT_CONNECTED;
import static com.practice.mymovie.ConstantKey.ParamsKey.*;
import static com.practice.mymovie.ConstantKey.ConstantKey.*;
import static com.practice.mymovie.ConstantKey.ServerUrl.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MainViewPagerFragment mainViewPagerFragment;
    private MovieDetailViewFragment movieDetailViewFragment;
    private ArrayList<MovieMain> mMovieList;

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
        if(item != null){
            int id = item.getItemId();

            switch (id) {
                case R.id.nav_movieList:
                    Toast.makeText(this, getString(R.string.selected_nav_movie_list), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_movieAPI:
                    Toast.makeText(this, getString(R.string.selected_nav_movie_api), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_reservation:
                    Toast.makeText(this, getString(R.string.selected_nav_reservation), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_userSetting:
                    Toast.makeText(this, getString(R.string.selected_nav_user_setting), Toast.LENGTH_SHORT).show();
                    break;
            }
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

    }

    private void startAppSetting() {
        //requestQueue 준비
        if (NetworkHelper.requestQueue == null) {
            NetworkHelper.requestQueue = Volley.newRequestQueue(this);
        }

        //database open
        OpenDatabase.openDatabase(this, DATABASE_NAME);

        //database table create
        CreateTable.createMovieListTable(this);
        CreateTable.createMovieDetailTable(this);
        CreateTable.createReviewTable(this);

        //인터넷 권한 확인 후, 인터넷 권한 있을 시에 서버로 요청 보낸다.
        String[] requiredPermissions = {Manifest.permission.INTERNET};
        int requestPermissionCode = 1;
        //permission 확인
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            loadMovieList();
        } else {
            ActivityCompat.requestPermissions(this, requiredPermissions, requestPermissionCode);
        }
    }

    private void loadMovieList() {
        // 인터넷이 연결된 경우, 서버에 요청을 보내고.
        // 인터넷이 연결되지 않은 경우 DB에서 가져온다.
        if(NetworkHelper.getNetWorkStatus(this) == TYPE_NOT_CONNECTED) {
            Toast.makeText(this, getString(R.string.load_from_db), Toast.LENGTH_SHORT).show();
            mMovieList = SelectTable.selectMovieListTable(this);
            if(mMovieList.size() == 0) {
                Toast.makeText(this, getString(R.string.no_movie_list_in_db), Toast.LENGTH_SHORT).show();
            }
            loadViewPagerView(mMovieList);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(PARAMS_TYPE, "1");
            sendRequest(READ_MOVIE_LIST, map, 1);
        }
    }

    //index -> 1) MainViewPager  2) MovieDetailView
    private void sendRequest(String addUrl, final Map<String, String> params, final int index) {
        String url = MAIN_URL + addUrl;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (index) {
                            case 1:
                                processRequest_ReadMovieList(response);
                                break;
                            case 2:
                                processRequest_ReadMovie(response, params.get(PARAMS_ID));
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ServerRequestError", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //파라미터가 null인 경우 빈 해시맵을 전달해준다.
                if (params != null) {
                    return params;
                } else {
                    return new HashMap<>();
                }
            }
        };

        NetworkHelper.requestQueue.add(stringRequest);
    }

    private void processRequest_ReadMovieList(String response) {
        Gson gson = new Gson();
        ReadMovieList readMoiveList = gson.fromJson(response, ReadMovieList.class);
        if (readMoiveList != null) {
            mMovieList = readMoiveList.getResult();
            loadViewPagerView(mMovieList);
            Toast.makeText(this, getString(R.string.server_request_success), Toast.LENGTH_SHORT).show();
            InsertTable.updateMovieListTable(this, mMovieList);
        }
        else {
            Log.d("test3","들어왔니..");
        }
    }

    private void processRequest_ReadMovie(String response, String id) {
        Gson gson = new Gson();
        ReadMovie readMovie = gson.fromJson(response, ReadMovie.class);
        if (readMovie != null) {
            ArrayList<MovieDetail> readMovieResult = readMovie.getResult();
            if (!readMovieResult.isEmpty()) {
                loadDetailView(readMovieResult.get(0), id);
                InsertTable.updateMovieDetailTable(this, Integer.parseInt(id), readMovieResult.get(0));
            }
        }
    }

    //ViewPager Fragment들에 영화 정보를 넘겨 줌으로써 View를 생성한다.
    //그 후 ViewPager를 띄워준다.
    private void loadViewPagerView(ArrayList<MovieMain> movieList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_LIST, movieList);
        mainViewPagerFragment.setArguments(bundle);
        FragmentManager FM = getSupportFragmentManager();
        if(FM != null) {
            FM.beginTransaction().add(R.id.flContainer_Main, mainViewPagerFragment).commit();
        }
    }


    //영화 목록에서 상세보기 버튼이 클릭 됐을 때,
    //서버에서 불러온 정보를 번들에 담아 fragment를 생성해 준다.
    private void loadDetailView(MovieDetail movieDetail, String id) {
        movieDetailViewFragment = new MovieDetailViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, movieDetail);
        bundle.putString(ID, id);
        movieDetailViewFragment.setArguments(bundle);
        FragmentManager FM = getSupportFragmentManager();
        if(FM != null)
            FM.beginTransaction().replace(R.id.flContainer_Main, movieDetailViewFragment).commit();

        toolbar.setTitle(getString(R.string.main_toolbar_detail));
    }

    //영화 정보를 넘겨준다.
    //영화 상세 화면에 내용을 담아 띄워준다.
    public void goToDetailView(int id) {
        if(NetworkHelper.getNetWorkStatus(this) == TYPE_NOT_CONNECTED){
            Toast.makeText(this, getString(R.string.load_from_db), Toast.LENGTH_SHORT).show();
            MovieDetail movieDetail = SelectTable.selectMovieDetailTable(this, id);
            if(movieDetail != null) {
                loadDetailView(movieDetail, String.valueOf(id));
            } else {
                Toast.makeText(this, getString(R.string.no_movie_detail_in_db), Toast.LENGTH_SHORT).show();
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(PARAMS_ID, String.valueOf(id));
            sendRequest(READ_MOVIE, map, 2);
        }
    }


    public void backToMainView(int order) {
        //메인 뷰로 돌아온다.
        mainViewPagerFragment = new MainViewPagerFragment();
        ////////////////////////////////////////////////////
        //mainViewPagerFragment를 새로 생성하지 않고 기존의 것을 그대로 사용한 경우
        //ViewPager에서 View가 보이지 않는 현상이 발생합니다.
        //ViewPager의 Swipe를 통해 OffScreenPageLimit로 인해 새로 생성된 뷰로 이동하면 그때서야 View가 생성됩니다.
        ////////////////////////////////////////////////////
        //bundle에 영화 상세 화면 순서를 넣어주어서 돌아왔을 때 viewpager가 해당 영화 페이지를 보여주게 한다.
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_LIST, mMovieList);
        bundle.putInt(MOVIE_ORDER, order);
        mainViewPagerFragment.setArguments(bundle);
        FragmentManager FM = getSupportFragmentManager();
        if(FM != null)
            FM.beginTransaction().replace(R.id.flContainer_Main, mainViewPagerFragment).commit();
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
        //인터넷 접속 권한이 있는지 확인한다.
        if (requestCode == 1 && grantResults.length == 1) {
            boolean check = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check = false;
                    break;
                }
            }

            if (check) {
                //권한을 확인 받은 경우 요청을 보낸다.
                loadMovieList();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
