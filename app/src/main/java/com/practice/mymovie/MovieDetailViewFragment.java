package com.practice.mymovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
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

import com.bumptech.glide.Glide;
import com.practice.mymovie.Adapter.CommentAdapter;
import com.practice.mymovie.DataClass.CommentItem;
import com.practice.mymovie.DataClass.ReadMovie.MovieDetail;
import com.practice.mymovie.DataClass.ReadMovie.ReadMovie;
import com.practice.mymovie.Interface.DataKey;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MovieDetailViewFragment extends Fragment
        implements DataKey, MainActivity.onKeyBackPressedListener {
    private MainActivity mActivity;
    private MovieDetail mMovie;
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
            mMovie = bundle.getParcelable(MOVIE);
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
        if (mMovie != null) {

            Glide.with(mActivity).load(mMovie.getImage()).into(ivMoviePoster);
            tvMovieTitle.setText(mMovie.getTitle());
//            ivMovieRating.setBackgroundResource(mMovieRatingRes);
            tvMovieRelease.setText(mMovie.getDate());
            tvMovieGenre.setText(mMovie.getGenre());
            tvMovieShowTime.setText(String.format(getString(R.string.detail_view_show_time),mMovie.getDuration()));
            tvTicketRank.setText(String.format(getString(R.string.detail_view_ticket_rank), mMovie.getReservation_grade()));
            tvTicketSalesPer.setText(String.format(getString(R.string.detail_view_ticket_sales_per), String.valueOf(mMovie.getReservation_rate())));
            tvMovieCredits.setText(String.valueOf(mMovie.getAudience_rating()));
            tvMovieTotalAttendance.setText(String.format(getString(R.string.detail_view_total_attendance_val),mMovie.getAudience()));
            tvStoryLine.setText(mMovie.getSynopsis());
            tvDirector.setText(mMovie.getDirector());
            tvActor.setText(mMovie.getActor());
            rbMovieCredits.setRating((float)(mMovie.getAudience_rating()) / 2.0F);
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
//        intent.putExtra(MOVIE_TITLE, mMovieTitle);
//        intent.putExtra(RATING, mMovieRatingRes);
        startActivityForResult(intent, WRITE_COMMENT_FROM_MOVIE_DETAIL_VIEW);
    }

    private void goToAllComment() {
//        한줄평 목록 액티비티로 이동.
//        영화 정보를 intent에 담아준다.
        Intent intent = new Intent(getContext(), CommentListActivity.class);
//        intent.putExtra(MOVIE_TITLE, mMovieTitle);
//        intent.putExtra(RATING, mMovieRatingRes);
        intent.putParcelableArrayListExtra(MOVIE_COMMENT_LIST,mCommentList);
        startActivity(intent);
    }

}
