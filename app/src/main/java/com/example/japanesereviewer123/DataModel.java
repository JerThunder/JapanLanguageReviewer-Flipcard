package com.example.japanesereviewer123;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {
    private String id;
    private String firstname;
    private String lastname;
    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    protected DataModel(Parcel in) {
        id = in.readString();
        firstname = in.readString();
        lastname = in.readString();
    }


    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
    }


    public DataModel(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }




    public String toString() {
        return firstname;
    }
}
