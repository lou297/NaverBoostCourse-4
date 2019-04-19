package com.practice.mymovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.practice.mymovie.CommentList.CommentAdapter;
import com.practice.mymovie.DataClass.ReadCommentList.Comment;
import com.practice.mymovie.DataClass.ReadCommentList.ReadCommentList;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ResponseResult.ResponseResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.practice.mymovie.ConstantKey.ParamsKey.*;
import static com.practice.mymovie.ConstantKey.ConstantKey.*;
import static com.practice.mymovie.ConstantKey.ServerUrl.*;

import static android.app.Activity.RESULT_OK;

public class MovieDetailViewFragment extends Fragment
        implements MainActivity.onKeyBackPressedListener {
    private MainActivity mActivity;
    private MovieDetail mMovie;
    private String mMovieId;
    private ArrayList<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;
    private final static int WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW = 1000;

    private ScrollView svMainContainer;

    private ImageView ivMoviePoster;
    private TextView tvMovieTitle;
    private ImageView ivMovieRating;
    private TextView tvMovieRelease;
    private TextView tvMovieGenre;
    private TextView tvMovieShowTime;
    private TextView tvTicketRank;
    private TextView tvTicketSalesPer;
    private TextView tvMovieCredits;
    private RatingBar rbMovieCredits;
    private TextView tvMovieTotalAttendance;
    private TextView tvStoryLine;
    private TextView tvDirector;
    private TextView tvActor;

    private Button btnThumbUp;
    private TextView tvThumbUpRate;
    private Button btnThumbDown;
    private TextView tvThumbDownRate;

    private int iThumbUpRate = 0;
    private int iThumbDownRate = 0;
    private boolean bThumbUpCond = false;
    private boolean bThumbDownCond = false;


    private TextView btnWriteComment;
    private Button btnAllComment;

    @Override
    public void onAttach(Context context) {
        mActivity = (MainActivity) getActivity();
        mActivity.setOnKeyBackPressedListener(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if (mActivity != null)
            mActivity = null;
        super.onDetach();
    }

    @Override
    public void onResume() {
        loadComment();
        super.onResume();
    }

    @Override
    public void onBackKey() {
//        메인 화면으로 돌아가기 위해 activity에서 interface 생성
        mActivity.backToMainView(mMovie.getReservation_grade());
        mActivity.setOnKeyBackPressedListener(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail_view, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mCommentList = new ArrayList<>();
        Bundle bundle = getArguments();
        readArgument(bundle);
        loadInfo();
        loadComment();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW) {
            if (resultCode == RESULT_OK)
                Toast.makeText(getContext(), "한줄평 작성 저장 버튼 눌림", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "한줄평 작성 취소됨", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readArgument(Bundle bundle) {
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
            mMovieId = bundle.getString(ID);
        }
    }

    //Thumb버튼 클릭시 상황에 따른 parameter 값 설정
    private void checkThumbUpCond() {
        Map<String, String> params = new HashMap();
        if (bThumbUpCond) {
            params.put(PARAMS_LIKEYN, "N");
        } else {
            params.put(PARAMS_LIKEYN, "Y");
            if (bThumbDownCond) {
                checkThumbDownCond();
            }
        }
        params.put(PARAMS_ID, mMovieId);
        sendRequest(INCREASE_LIKE_DISLIKE, params, 1);
    }

    private void checkThumbDownCond() {
        Map<String, String> params = new HashMap<>();
        if (bThumbDownCond) {
            params.put(PARAMS_DISLIKEYN, "N");
        } else {
            params.put(PARAMS_DISLIKEYN, "Y");
            if (bThumbUpCond) {
                checkThumbUpCond();
            }
        }
        params.put(PARAMS_ID, mMovieId);
        sendRequest(INCREASE_LIKE_DISLIKE, params, 2);
    }

    //ThumbUp 체크 상태 저장
    private void controlThumbUpRate() {
        if (bThumbUpCond) {
            iThumbUpRate--;
            bThumbUpCond = false;
            btnThumbUp.setBackgroundResource(R.drawable.ic_thumb_up);
        } else {
            iThumbUpRate++;
            bThumbUpCond = true;
            btnThumbUp.setBackgroundResource(R.drawable.ic_thumb_up_selected);
            if (bThumbDownCond)
                controlThumbDownRate();
        }
        tvThumbUpRate.setText(String.valueOf(iThumbUpRate));
    }

    //ThumbDown 체크 상태 저장
    private void controlThumbDownRate() {
        if (bThumbDownCond) {
            iThumbDownRate--;
            bThumbDownCond = false;
            btnThumbDown.setBackgroundResource(R.drawable.ic_thumb_down);
        } else {
            iThumbDownRate++;
            bThumbDownCond = true;
            btnThumbDown.setBackgroundResource(R.drawable.ic_thumb_down_selected);
            if (bThumbUpCond)
                controlThumbUpRate();
        }
        tvThumbDownRate.setText(String.valueOf(iThumbDownRate));
    }

    private void initView(View view) {
        svMainContainer = view.findViewById(R.id.svMainContainer_DetailView);
        ivMoviePoster = view.findViewById(R.id.ivMoviePoster_DetailView);
        tvMovieTitle = view.findViewById(R.id.tvMovieTitle_DetailView);
        ivMovieRating = view.findViewById(R.id.ivMovieRating_DetailView);
        tvMovieRelease = view.findViewById(R.id.tvMovieRelease_DetailView);
        tvMovieGenre = view.findViewById(R.id.tvMovieGenre_DetailView);
        tvMovieShowTime = view.findViewById(R.id.tvMovieShowTime_DetailView);
        tvTicketRank = view.findViewById(R.id.tvTicketRank_DetailView);
        tvTicketSalesPer = view.findViewById(R.id.tvTicketSalesPer_DetailView);
        tvMovieCredits = view.findViewById(R.id.tvMovieCredits_DetailView);
        tvMovieTotalAttendance = view.findViewById(R.id.tvMovieTotalAttendance_DetailView);
        tvStoryLine = view.findViewById(R.id.tvStoryLine_DetailView);
        tvDirector = view.findViewById(R.id.tvDirector_DetailView);
        tvActor = view.findViewById(R.id.tvActor_DetailView);
        rbMovieCredits = view.findViewById(R.id.rbMovieCredits_DetailView);

        btnThumbUp = view.findViewById(R.id.btnThumbUp);
        tvThumbUpRate = view.findViewById(R.id.tvThumbUpRate);
        btnThumbDown = view.findViewById(R.id.btnThumbDown);
        tvThumbDownRate = view.findViewById(R.id.tvThumbDownRate);

        btnWriteComment = view.findViewById(R.id.btnWriteComment_DetailView);
        btnAllComment = view.findViewById(R.id.btnAllComment);

        btnThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkThumbUpCond();
            }
        });
        btnThumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkThumbDownCond();
            }
        });

        btnWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWriteComment();
            }
        });
        btnAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCommentList();
            }
        });
    }

    private void loadInfo() {
        if (mMovie != null) {

            iThumbUpRate = mMovie.getLike();
            iThumbDownRate = mMovie.getDislike();

            Glide.with(mActivity).load(mMovie.getImage()).into(ivMoviePoster);
            tvMovieTitle.setText(mMovie.getTitle());
            switch (mMovie.getGrade()) {
                case 12:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_12);
                    break;
                case 15:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_15);
                    break;
                case 19:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_19);
                    break;
                default:
                    ivMovieRating.setBackgroundResource(R.drawable.ic_all);
                    break;
            }
            tvMovieRelease.setText(mMovie.getDate());
            tvMovieGenre.setText(mMovie.getGenre());
            tvMovieShowTime.setText(String.format(getString(R.string.detail_view_show_time), mMovie.getDuration()));
            tvTicketRank.setText(String.format(getString(R.string.detail_view_ticket_rank), mMovie.getReservation_grade()));
            tvTicketSalesPer.setText(String.format(getString(R.string.detail_view_ticket_sales_per), String.valueOf(mMovie.getReservation_rate())));
            tvMovieCredits.setText(String.valueOf(mMovie.getAudience_rating()));
            tvMovieTotalAttendance.setText(String.format(getString(R.string.detail_view_total_attendance_val), mMovie.getAudience()));
            tvStoryLine.setText(mMovie.getSynopsis());
            tvDirector.setText(mMovie.getDirector());
            tvActor.setText(mMovie.getActor());
            rbMovieCredits.setRating((float) (mMovie.getAudience_rating()) / 2.0F);

            tvThumbUpRate.setText(String.valueOf(iThumbUpRate));
            tvThumbDownRate.setText(String.valueOf(iThumbDownRate));
        }
    }

    private void sendRequest(String addUrl, final Map<String, String> params, final int index) {
        String url = "http://boostcourse-appapi.connect.or.kr:10000/" + addUrl;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (index) {
                            case 1:
                                processResquest_Increase_Like_DisLike(response, params, 1);
                                break;
                            case 2:
                                processResquest_Increase_Like_DisLike(response, params, 2);
                                break;
                            case 3:
                                processResquest_ReadCommentList(response);
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        switch (index) {
                            case 3:
                                Toast.makeText(mActivity, "리뷰 목록 불러오기 실패", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(mActivity, "좋아요/ 싫어요 버튼 적용 실패", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                } else {
                    return new HashMap<>();
                }
            }
        };

        AppHelper.requestQueue.add(stringRequest);
    }

    private void processResquest_ReadCommentList(String response) {
        Gson gson = new Gson();
        ReadCommentList readCommentList = gson.fromJson(response, ReadCommentList.class);

        if (readCommentList != null) {
            mCommentList = readCommentList.getResult();
            if (mCommentList != null) {
                mCommentAdapter = new CommentAdapter(getContext(), mCommentList);
                ListView listView = getView().findViewById(R.id.commentListView_Main);
                listView.setAdapter(mCommentAdapter);
            }
        }
        if (svMainContainer != null) {
            // 리스트 뷰가 생성되면서 스크롤이 내려간다.
            //다시 올려주는 과정
            svMainContainer.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void processResquest_Increase_Like_DisLike(String response, Map<String, String> params, int index) {
        Gson gson = new Gson();
        ResponseResult result = gson.fromJson(response, ResponseResult.class);

        if (result != null && result.getCode() == 200) {
            Toast.makeText(mActivity, "좋아요/ 싫어요 설정이 적용됐습니다.", Toast.LENGTH_SHORT).show();
            switch (index) {
                case 1:
                    controlThumbUpRate();
                    break;
                case 2:
                    controlThumbDownRate();
                    break;
            }
        } else {
            Toast.makeText(mActivity, "좋아요/ 싫어요 설정이 적용되지 못했습니다.", Toast.LENGTH_SHORT).show();
        }


    }


    private void loadComment() {
//        임의의 한줄평 목록을 생성해 준다.
        if (mMovieId != null) {
            Map<String, String> params = new HashMap<>();
            params.put(PARAMS_ID, mMovieId);
            params.put(PARAMS_LIMIT, "all");
            sendRequest(READ_COMMENT_LIST, params, 3);
        }
    }


    private void goToWriteComment() {
//        한줄평 작성 액티비티로 이동.
//        영화 정보를 intent에 담아준다.
        Intent intent = new Intent(getContext(), CommentWriteActivity.class);
        intent.putExtra(MOVIE, mMovie);
        intent.putExtra(ID, mMovieId);
        startActivityForResult(intent, WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW);
    }

    private void goToCommentList() {
//        한줄평 목록 액티비티로 이동.
//        영화 정보를 intent에 담아준다.
        Intent intent = new Intent(getContext(), CommentListActivity.class);
        intent.putExtra(MOVIE, mMovie);
        intent.putExtra(ID, mMovieId);
        intent.putParcelableArrayListExtra(MOVIE_COMMENT_LIST, mCommentList);
        startActivity(intent);
    }

}
