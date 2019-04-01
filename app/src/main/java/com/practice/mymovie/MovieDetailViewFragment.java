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
import android.widget.TextView;
import android.widget.Toast;

import com.practice.mymovie.Adapter.CommentAdapter;
import com.practice.mymovie.DataClass.CommentItem;
import com.practice.mymovie.Interface.DataKey;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MovieDetailViewFragment extends Fragment
        implements DataKey, MainActivity.onKeyBackPressedListener {
    private MainActivity mActivity;
    private String mMovieTitle;
    private int mMovieRatingRes;
    private ArrayList<CommentItem> mCommentList;
    private final static int WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW = 1000;

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

    private int movieRank = 1;

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
    public void onBackKey() {
//        메인 화면으로 돌아가기 위해 activity에서 interface 생성
        mActivity.backToMainView(movieRank);
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
        if(requestCode == WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW){
            if(resultCode == RESULT_OK)
                Toast.makeText(getContext(), "한줄평 작성 저장 버튼 눌림",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "한줄평 작성 취소됨",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readArgument(Bundle bundle) {
        if (bundle != null) {
            mMovieTitle = bundle.getString(MOVIE_TITLE);
        }
    }

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
                controlThumbUpRate();
            }
        });
        btnThumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlThumbDownRate();
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
                goToAllComment();
            }
        });
    }

    private void loadInfo() {
        //서버에서 불러올 영화 정보가 없으므로 임의의 영화 정보를 넣어준다.
        int posterResId = 0;
        mMovieRatingRes = 0;
        String movieCredits = "";
        String movieRelease = "";
        String movieGenre = "";
        String movieShowTime = "";
        String movieSalesPer = "";
        String movieTotalAttendance = "";
        String storyLine = "";
        String movieDirector = "";
        String movieActor = "";
        if (mMovieTitle != null) {
            switch (mMovieTitle) {
                case "군도":
                    posterResId = R.drawable.image1;
                    mMovieRatingRes = R.drawable.ic_all;
                    movieCredits = getString(R.string.movie1_credits);
                    movieRelease = getString(R.string.movie1_release);
                    movieGenre = getString(R.string.movie1_genre);
                    movieShowTime = getString(R.string.movie1_show_time);
                    movieRank = Integer.parseInt(getString(R.string.movie1_rank));
                    movieSalesPer = getString(R.string.movie1_sales_per);
                    movieTotalAttendance = getString(R.string.movie1_total_attendance);
                    storyLine = getString(R.string.movie1_story_line);
                    movieDirector = getString(R.string.movie1_director);
                    movieActor = getString(R.string.movie1_actor);
                    break;
                case "공조":
                    posterResId = R.drawable.image2;
                    mMovieRatingRes = R.drawable.ic_15;
                    movieCredits = getString(R.string.movie2_credits);
                    movieRelease = getString(R.string.movie2_release);
                    movieGenre = getString(R.string.movie2_genre);
                    movieShowTime = getString(R.string.movie2_show_time);
                    movieRank = Integer.parseInt(getString(R.string.movie2_rank));
                    movieSalesPer = getString(R.string.movie2_sales_per);
                    movieTotalAttendance = getString(R.string.movie2_total_attendance);
                    storyLine = getString(R.string.movie2_story_line);
                    movieDirector = getString(R.string.movie2_director);
                    movieActor = getString(R.string.movie2_actor);
                    break;
                case "더 킹":
                    posterResId = R.drawable.image3;
                    mMovieRatingRes = R.drawable.ic_19;
                    movieCredits = getString(R.string.movie3_credits);
                    movieRelease = getString(R.string.movie3_release);
                    movieGenre = getString(R.string.movie3_genre);
                    movieShowTime = getString(R.string.movie3_show_time);
                    movieRank = Integer.parseInt(getString(R.string.movie3_rank));
                    movieSalesPer = getString(R.string.movie3_sales_per);
                    movieTotalAttendance = getString(R.string.movie3_total_attendance);
                    storyLine = getString(R.string.movie3_story_line);
                    movieDirector = getString(R.string.movie3_director);
                    movieActor = getString(R.string.movie3_actor);
                    break;
                case "레지던트 이블":
                    posterResId = R.drawable.image4;
                    mMovieRatingRes = R.drawable.ic_15;
                    movieCredits = getString(R.string.movie4_credits);
                    movieRelease = getString(R.string.movie4_release);
                    movieGenre = getString(R.string.movie4_genre);
                    movieShowTime = getString(R.string.movie4_show_time);
                    movieRank = Integer.parseInt(getString(R.string.movie4_rank));
                    movieSalesPer = getString(R.string.movie4_sales_per);
                    movieTotalAttendance = getString(R.string.movie4_total_attendance);
                    storyLine = getString(R.string.movie4_story_line);
                    movieDirector = getString(R.string.movie4_director);
                    movieActor = getString(R.string.movie4_actor);
                    break;
                case "럭키":
                    posterResId = R.drawable.image5;
                    mMovieRatingRes = R.drawable.ic_12;
                    movieCredits = getString(R.string.movie5_credits);
                    movieRelease = getString(R.string.movie5_release);
                    movieGenre = getString(R.string.movie5_genre);
                    movieShowTime = getString(R.string.movie5_show_time);
                    movieRank = Integer.parseInt(getString(R.string.movie5_rank));
                    movieSalesPer = getString(R.string.movie5_sales_per);
                    movieTotalAttendance = getString(R.string.movie5_total_attendance);
                    storyLine = getString(R.string.movie5_story_line);
                    movieDirector = getString(R.string.movie5_director);
                    movieActor = getString(R.string.movie5_actor);
                    break;
                case "아수라":
                    posterResId = R.drawable.image6;
                    mMovieRatingRes = R.drawable.ic_all;
                    movieCredits = getString(R.string.movie6_credits);
                    movieRelease = getString(R.string.movie6_release);
                    movieGenre = getString(R.string.movie6_genre);
                    movieShowTime = getString(R.string.movie6_show_time);
                    movieRank = Integer.parseInt(getString(R.string.movie6_rank));
                    movieSalesPer = getString(R.string.movie6_sales_per);
                    movieTotalAttendance = getString(R.string.movie6_total_attendance);
                    storyLine = getString(R.string.movie6_story_line);
                    movieDirector = getString(R.string.movie6_director);
                    movieActor = getString(R.string.movie6_actor);
                    break;
                default:
                    break;
            }
            ivMoviePoster.setBackgroundResource(posterResId);
            tvMovieTitle.setText(mMovieTitle);
            ivMovieRating.setBackgroundResource(mMovieRatingRes);
            tvMovieRelease.setText(movieRelease);
            tvMovieGenre.setText(movieGenre);
            tvMovieShowTime.setText(movieShowTime);
            tvTicketRank.setText(String.format(getString(R.string.detail_view_ticket_rank), movieRank));
            tvTicketSalesPer.setText(String.format(getString(R.string.detail_view_ticket_sales_per), movieSalesPer));
            tvMovieCredits.setText(String.format(getString(R.string.detail_view_movie_credits_val), movieCredits));
            tvMovieTotalAttendance.setText(movieTotalAttendance);
            tvStoryLine.setText(storyLine);
            tvDirector.setText(movieDirector);
            tvActor.setText(movieActor);
            rbMovieCredits.setRating(Float.parseFloat(movieCredits) / 2.0F);
        }
    }

    private void loadComment() {
//        임의의 한줄평 목록을 생성해 준다.
        if (mCommentList != null) {
            mCommentList.add(new CommentItem("kim71**", "10분 전", 4.0F, "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요."));
            mCommentList.add(new CommentItem("aaa123**", "1시간 전", 0.5F, "너무 재미없다"));
            mCommentList.add(new CommentItem("kim71**", "10분 전", 3.5F, "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요."));
            mCommentList.add(new CommentItem("kim71**", "10분 전", 2.1F, "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요."));

            CommentAdapter adapter = new CommentAdapter(getContext(), mCommentList);
            ListView listView = getView().findViewById(R.id.commentListView_Main);
            listView.setAdapter(adapter);
        }
    }

    private void goToWriteComment() {
//        한줄평 작성 액티비티로 이동.
//        영화 정보를 intent에 담아준다.
        Intent intent = new Intent(getContext(), CommentWriteActivity.class);
        intent.putExtra(MOVIE_TITLE, mMovieTitle);
        intent.putExtra(RATING, mMovieRatingRes);
        startActivityForResult(intent, WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW);
    }

    private void goToAllComment() {
//        한줄평 목록 액티비티로 이동.
//        영화 정보를 intent에 담아준다.
        Intent intent = new Intent(getContext(), CommentListActivity.class);
        intent.putExtra(MOVIE_TITLE, mMovieTitle);
        intent.putExtra(RATING, mMovieRatingRes);
        intent.putParcelableArrayListExtra(MOVIE_COMMENT_LIST,mCommentList);
        startActivity(intent);
    }

}
