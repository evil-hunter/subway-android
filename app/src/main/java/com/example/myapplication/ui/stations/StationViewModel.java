package com.example.myapplication.ui.stations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entity.Station;

import java.util.List;

public class StationViewModel extends AndroidViewModel {
    private static final String TAG = "station";

    private String[] station_line = new String[]{"一号线", "二号线", "三号线", "四号线", "五号线", "六号线"};

    /**
     * 数据源
     */
    MutableLiveData<List<Station.HitsBean>> hitsBean = new MutableLiveData<>();

    public StationViewModel(@NonNull Application application) {
        super(application);
    }
}
