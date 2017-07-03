package ec.medinamobile.bakify.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erick on 30/6/17.
 */

public class Step implements Parcelable{

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    private int serverId;
    private int recipeId;


    public Step(){


    }

    public Step(Parcel in) {
        setId(in.readInt());
        setShortDescription(in.readString());
        setDescription(in.readString());
        setVideoURL(in.readString());
        setThumbnailURL(in.readString());
        setServerId(in.readInt());
        setRecipeId(in.readInt());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getShortDescription());
        parcel.writeString(getDescription());
        parcel.writeString(getVideoURL());
        parcel.writeString(getThumbnailURL());
        parcel.writeInt(getServerId());
        parcel.writeInt(getRecipeId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
