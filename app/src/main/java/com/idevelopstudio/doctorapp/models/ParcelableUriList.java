package com.idevelopstudio.doctorapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParcelableUriList implements Parcelable{

    private ArrayList<Uri> imageUris = new ArrayList<>();

    public ParcelableUriList(ArrayList<Uri> imageUris) {
        this.imageUris = imageUris;
    }

    protected ParcelableUriList(Parcel in) {
        imageUris = in.createTypedArrayList(Uri.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(imageUris);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelableUriList> CREATOR = new Creator<ParcelableUriList>() {
        @Override
        public ParcelableUriList createFromParcel(Parcel in) {
            return new ParcelableUriList(in);
        }

        @Override
        public ParcelableUriList[] newArray(int size) {
            return new ParcelableUriList[size];
        }
    };

    public ArrayList<Uri> getImageUris() {
        return imageUris;
    }

    public void setImageUris(ArrayList<Uri> imageUris) {
        this.imageUris = imageUris;
    }
}
