package com.example.myapplication.ui.stations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import java.util.List;

public class StationViewModel extends AndroidViewModel {

    private static final String TAG = "station";

    private String[] station_line = new String[]{"机场线","一号线", "二号线", "三号线", "四号线", "五号线", "六号线","九号线"};

    private int currentStation = 1;

    private int stationNumber;

    private String imageUrl;

    /*
    * 数据源
    * */



    public StationViewModel(@NonNull Application application) {
        super(application);
    }

    /*
    * 拼接API（正式部署后为：http://49.234.126.247:4000/ + 返回的url
    * */
    private String getUrl() {
        return "http://10.18.209.35:4000/home/stationlogo" + imageUrl;
    }

}
