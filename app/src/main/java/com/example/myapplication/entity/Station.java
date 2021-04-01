package com.example.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Station implements Parcelable {

    private int total;
    private int totalHits;
    private List<HitsBean> hits;

    private Station(Parcel in) {
        total = in.readInt();
        totalHits = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
        dest.writeInt(totalHits);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    public int getTotalHits() {
        return totalHits;
    }

    public List<HitsBean> getHits() {
        return hits;
    }

    public void setHits(List<HitsBean> hits) {
        this.hits = hits;
    }

    public static class HitsBean implements Parcelable{

        private int id;
        private String pageURL;
        private String type;
        private String tags;
        private String previewURL;
        private int previewWidth;
        private int previewHeight;
        private String webformatURL;
        private int webformatWidth;
        private int webformatHeight;
        private String largeImageURL;
        private int imageWidth;
        private int imageHeight;
        private int imageSize;
        private int views;
        private int downloads;
        private int favorites;
        private int likes;
        private int comments;
        private int user_id;
        private String user;
        private String userImageURL;

        HitsBean(Parcel in) {
            id = in.readInt();
            pageURL = in.readString();
            type = in.readString();
            tags = in.readString();
            previewURL = in.readString();
            previewWidth = in.readInt();
            previewHeight = in.readInt();
            webformatURL = in.readString();
            webformatWidth = in.readInt();
            webformatHeight = in.readInt();
            largeImageURL = in.readString();
            imageWidth = in.readInt();
            imageHeight = in.readInt();
            imageSize = in.readInt();
            views = in.readInt();
            downloads = in.readInt();
            favorites = in.readInt();
            likes = in.readInt();
            comments = in.readInt();
            user_id = in.readInt();
            user = in.readString();
            userImageURL = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(pageURL);
            dest.writeString(type);
            dest.writeString(tags);
            dest.writeString(previewURL);
            dest.writeInt(previewWidth);
            dest.writeInt(previewHeight);
            dest.writeString(webformatURL);
            dest.writeInt(webformatWidth);
            dest.writeInt(webformatHeight);
            dest.writeString(largeImageURL);
            dest.writeInt(imageWidth);
            dest.writeInt(imageHeight);
            dest.writeInt(imageSize);
            dest.writeInt(views);
            dest.writeInt(downloads);
            dest.writeInt(favorites);
            dest.writeInt(likes);
            dest.writeInt(comments);
            dest.writeInt(user_id);
            dest.writeString(user);
            dest.writeString(userImageURL);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<HitsBean> CREATOR = new Creator<HitsBean>() {
            @Override
            public HitsBean createFromParcel(Parcel in) {
                return new HitsBean(in);
            }

            @Override
            public HitsBean[] newArray(int size) {
                return new HitsBean[size];
            }
        };

        public String getWebformatURL() {
            return webformatURL;
        }
    }
}
