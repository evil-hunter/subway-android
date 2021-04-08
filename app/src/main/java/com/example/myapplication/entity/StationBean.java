package com.example.myapplication.entity;

import java.util.List;

public class StationBean {

    private int code;
    private String name;
    private String line;
    private String pinyin;
    private String stationLogo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

}
