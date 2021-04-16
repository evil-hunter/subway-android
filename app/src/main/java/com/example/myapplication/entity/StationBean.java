package com.example.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.ui.stations.StationDetail;

import java.util.List;

public class StationBean implements Parcelable {

    private int code;
    private int total;
    private String name;
    private String line;
    private String pinyin;
    private String stationLogo;
    private String positive;
    private String reverse;
    private String toiletLoc;
    private String entranceElevator;
    private String hallElevator;
    private String stationDetail;

    StationBean(Parcel in) {
        code = in.readInt();
        total = in.readInt();
        name = in.readString();
        line = in.readString();
        pinyin = in.readString();
        stationLogo = in.readString();
        positive = in.readString();
        reverse = in.readString();
        toiletLoc = in.readString();
        entranceElevator = in.readString();
        hallElevator = in.readString();
        stationDetail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeInt(total);
        dest.writeString(name);
        dest.writeString(line);
        dest.writeString(pinyin);
        dest.writeString(stationLogo);
        dest.writeString(positive);
        dest.writeString(reverse);
        dest.writeString(toiletLoc);
        dest.writeString(entranceElevator);
        dest.writeString(hallElevator);
        dest.writeString(stationDetail);
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public static final Creator<StationBean> CREATOR = new Creator<StationBean>() {

        @Override
        public StationBean createFromParcel(Parcel in) {
            return new StationBean(in);
        }

        @Override
        public StationBean[] newArray(int size) {
            return new StationBean[size];
        }

    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPinyin(){
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getStationLogo() {
        return stationLogo;
    }

    public void setStationLogo(String stationLogo) {
        this.stationLogo = stationLogo;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getReverse() {
        return reverse;
    }

    public void setReverse(String reverse) {
        this.reverse = reverse;
    }

    public String getToiletLoc() {
        return toiletLoc;
    }

    public void setToiletLoc(String toiletLoc) {
        this.toiletLoc = toiletLoc;
    }

    public String getEntranceElevator() {
        return entranceElevator;
    }

    public void setEntranceElevator(String entranceElevator) {
        this.entranceElevator = entranceElevator;
    }

    public String getHallElevator() {
        return hallElevator;
    }

    public void setHallElevator(String hallElevator) {
        this.hallElevator = hallElevator;
    }

    public String getStationDetail() {
        return stationDetail;
    }

    public void setStationDetail(String stationDetail) {
        this.stationDetail = stationDetail;
    }

}
