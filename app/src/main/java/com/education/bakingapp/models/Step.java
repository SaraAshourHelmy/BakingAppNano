package com.education.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sara on 7/17/2017.
 */

public class Step implements Parcelable {

    private int id;
    private String description;
    private String shortDescription;
    private String thumbnailURL;
    private String videoURL;

    protected Step(Parcel in) {
        id = in.readInt();
        description = in.readString();
        shortDescription = in.readString();
        thumbnailURL = in.readString();
        videoURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(thumbnailURL);
        dest.writeString(videoURL);
    }
}
