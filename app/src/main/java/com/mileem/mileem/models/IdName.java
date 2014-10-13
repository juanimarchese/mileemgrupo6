package com.mileem.mileem.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ramirodiaz on 08/09/14.
 */
public class IdName implements Parcelable{
    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdName(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
