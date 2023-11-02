package com.example.japanesereviewer123;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {
    private String id;

    public DataModel(String id, String kanji, String hiragana, String meaning) {
        this.id = id;
        this.kanji = kanji;
        this.hiragana = hiragana;
        this.meaning = meaning;
    }

    private String kanji;
    private String hiragana;
    private String meaning;



    public String getMeaning() {return meaning; }

    public String getId() {
        return id;
    }

    public String getkanji() {
        return kanji;
    }

    public String gethiragana() {
        return hiragana;
    }


    protected DataModel(Parcel in) {
        id = in.readString();
        kanji = in.readString();
        hiragana = in.readString();
        meaning = in.readString();
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
        parcel.writeString(kanji);
        parcel.writeString(hiragana);
        parcel.writeString(meaning);
    }



    public String toString() {
       // return "Kanji: " + kanji + "";
        int labelWidth = 10; // Adjust the label width as needed.

        // Create the Kanji and Hiragana strings without any additional text
        String kanjiString = String.format("%-" + labelWidth + "s: %-" + labelWidth + "s", "Kanji", kanji);
        String hiraganaString = String.format("%-" + labelWidth + "s: %-" + labelWidth + "s", "Hiragana", hiragana);

        return kanjiString + hiraganaString;
    }
}
