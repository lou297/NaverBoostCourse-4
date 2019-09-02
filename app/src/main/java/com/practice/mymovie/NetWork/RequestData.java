package com.practice.mymovie.NetWork;

import android.util.Log;

import java.util.Map;

public class RequestData {
    private int requestType;
    private String requestUrl;
    private Map<String, String> requestParams;

    public RequestData(int requestType, String requestUrl, Map<String, String> requestParams) {
        this.requestType = requestType;
        this.requestUrl = requestUrl;
        this.requestParams = requestParams;
    }

    public int getRequestType() {
        return requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public static class Builder {
        private int requestType;
        private String requestUrl;
        private Map<String, String> requestParams;

        public void setRequestType(int requestType) {
            this.requestType = requestType;
        }

        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public void setRequestParams(Map<String, String> requestParams) {
            this.requestParams = requestParams;
        }

        public RequestData build() {
            if(requestType < 0) {
                throw new IllegalStateException("requestType is null");
            }
            if(requestUrl == null) {
                throw new IllegalStateException("requeestUrl is null");
            }

            return new RequestData(requestType, requestUrl, requestParams);
        }
    }
}
