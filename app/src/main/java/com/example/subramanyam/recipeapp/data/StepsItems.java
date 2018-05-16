package com.example.subramanyam.recipeapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StepsItems implements Parcelable {
    private Integer id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailURL;



    public Integer getId() {

        return id;

    }



    public void setId(Integer id) {

        this.id = id;

    }



    public String getShortDescription() {

        return shortDescription;

    }



    public void setShortDescription(String shortDescription) {

        this.shortDescription = shortDescription;

    }



    public String getDescription() {

        return description;

    }



    public void setDescription(String description) {

        this.description = description;

    }



    public String getVideoURL() {

        return videoURL;

    }



    public void setVideoURL(String videoURL) {

        this.videoURL = videoURL;

    }



    public String getThumbnailURL() {

        return thumbnailURL;

    }



    public void setThumbnailURL(String thumbnailURL) {

        this.thumbnailURL = thumbnailURL;

    }





    protected StepsItems(Parcel in) {

        id = in.readByte() == 0x00 ? null : in.readInt();

        shortDescription = in.readString();

        description = in.readString();

        videoURL = in.readString();

        thumbnailURL = in.readString();

    }



    @Override

    public int describeContents() {

        return 0;

    }



    @Override

    public void writeToParcel(Parcel dest, int flags) {

        if (id == null) {

            dest.writeByte((byte) (0x00));

        } else {

            dest.writeByte((byte) (0x01));

            dest.writeInt(id);

        }

        dest.writeString(shortDescription);

        dest.writeString(description);

        dest.writeString(videoURL);

        dest.writeString(thumbnailURL);

    }



    @SuppressWarnings("unused")

    public static final Parcelable.Creator<StepsItems> CREATOR = new Parcelable.Creator<StepsItems>() {

        @Override

        public StepsItems createFromParcel(Parcel in) {

            return new StepsItems(in);

        }



        @Override

        public StepsItems[] newArray(int size) {

            return new StepsItems[size];

        }

    };
}
