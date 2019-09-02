package com.practice.mymovie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import static com.practice.mymovie.ConstantKey.ConstantKey.*;
public class PhotoViewAcitivity extends AppCompatActivity {

    PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        initView();
        setPhotoView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //        toolbar에서 뒤로가기 버튼 클릭 시 동작
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        photoView = findViewById(R.id.photoView);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setPhotoView() {
        Intent intent = getIntent();

        String Url = intent.getStringExtra(PHOTO_URL);

        Glide.with(getApplicationContext()).load(Url).into(photoView);
    }
}
