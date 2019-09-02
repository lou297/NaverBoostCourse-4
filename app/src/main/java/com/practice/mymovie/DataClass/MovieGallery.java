package com.practice.mymovie.DataClass;

public class MovieGallery {
    //썸네일 이미지 주소
    private String Imageurl;
    //비디오 영상 주소, 그냥 이미지인 경우는 null
    private String videoUrl;

    public MovieGallery(String imageurl, String videoUrl) {
        this.Imageurl = imageurl;
        this.videoUrl = videoUrl;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
