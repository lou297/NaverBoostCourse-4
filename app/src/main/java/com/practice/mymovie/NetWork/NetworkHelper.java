package com.practice.mymovie.NetWork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_MOBILE;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_NOT_CONNECTED;
import static com.practice.mymovie.ConstantKey.NetWorkStatusKey.TYPE_WIFI;
import static com.practice.mymovie.ConstantKey.ServerUrl.MAIN_URL;

public class NetworkHelper {

    public static RequestQueue requestQueue;

    public void makeRequest(final RequestData requestData, Response.Listener listener, Response.ErrorListener errorListener) {
        String requestUrl = MAIN_URL + requestData.getRequestUrl();
        StringRequest request = new StringRequest(requestData.getRequestType(), requestUrl, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //파라미터가 null인 경우 빈 해시맵을 전달해준다.
                if (requestData.getRequestParams() != null) {
                    return requestData.getRequestParams();
                } else {
                    return new HashMap<>();
                }
            }
        };

        requestQueue.add(request);
    }

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
