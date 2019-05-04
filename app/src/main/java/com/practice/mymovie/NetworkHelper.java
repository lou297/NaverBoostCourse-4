package com.practice.mymovie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;

import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_MOBILE;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_NOT_CONNECTED;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_WIFI;

public class NetworkHelper {

    public static RequestQueue requestQueue;

    public static int getNetWorkStatus (Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo != null) {
                int type = networkInfo.getType();
                if(type == ConnectivityManager.TYPE_MOBILE) {
                    return TYPE_MOBILE;
                } else if(type == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_WIFI;
                }
            }
        }
        return TYPE_NOT_CONNECTED;
    }
}
