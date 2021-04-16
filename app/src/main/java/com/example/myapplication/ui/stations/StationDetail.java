package com.example.myapplication.ui.stations;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.entity.StationBean;
import com.example.myapplication.utils.UITools;
import com.google.android.material.button.MaterialButton;

import uk.co.senab.photoview.IPhotoView;

public class StationDetail extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "stationDetail";

    ConstraintLayout constraintLayout;
    ImageView mPhotoView;
    TextView stationName,stationPinyin,timetable,positive,reverse;
    TextView toilet,toiletLoc,entranceElevator,entranceElevatorLoc,hallElevator,hallElevatorLoc;
    ImageView stationLogo;


    /*
    * 数据源*/
    private StationBean stationBean;

    /*
    * 当前用户*/
    private static String USER_NAME;

    /*
    * Token*/
    private String isToken;

    /*
    * 选择的图片尺寸*/
    private String ImageSize = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acticity_station_detail);
        initView();
    }

    private void initView() {
        constraintLayout = findViewById(R.id.stationDetail);
        mPhotoView = findViewById(R.id.photoView);
        stationName = findViewById(R.id.stationInfo);
        stationPinyin = findViewById(R.id.stationPinyin);
        stationLogo = findViewById(R.id.stationLogoImg);
        timetable = findViewById(R.id.timetable);
        positive = findViewById(R.id.positive);
        reverse = findViewById(R.id.reverse);
        toilet = findViewById(R.id.toilet);
        toiletLoc = findViewById(R.id.toiletLoc);
        entranceElevator = findViewById(R.id.entranceElevator);
        entranceElevatorLoc = findViewById(R.id.entranceElevatorLoc);
        hallElevator = findViewById(R.id.hallElevator);
        hallElevatorLoc = findViewById(R.id.hallElevatorLoc);
        UITools.makeStatusBarTransparent(this);
        UITools.MIUISetStatusBarLightMode(this,true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initStationDetailView();
        initAllOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initStationDetailView() {

        stationBean = getIntent().getExtras().getParcelable("stationInfo");

        Glide.with(getApplicationContext())
                .load("http://10.18.209.35:4000" + stationBean.getStationDetail())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(mPhotoView);

        Glide.with(this)
                .load("http://10.18.209.35:4000" + stationBean.getStationLogo())
                .into(stationLogo);

        stationName.setText(stationBean.getName());
        stationPinyin.setText(stationBean.getPinyin());
        positive.setText(stationBean.getPositive());
        reverse.setText(stationBean.getReverse());
        toiletLoc.setText(stationBean.getToiletLoc());
        entranceElevatorLoc.setText(stationBean.getEntranceElevator());
        hallElevatorLoc.setText(stationBean.getHallElevator());
    }

    private void initAllOnClick() {

    }


    public void onClick(View v) {

    }

    /**
     * 重写onBackPressed，用于配对进入动画
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

}
