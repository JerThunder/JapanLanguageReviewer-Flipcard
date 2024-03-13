package com.example.japanesereviewer123.ui.contentvocab;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

@SuppressLint("ParcelCreator")
public class GoiDataModel implements Parcelable {

    private String id;
    private String vocab;
    private String hiragana;
    private String imageurl;

    public GoiDataModel(String id, String vocab, String hiragana, String imageurl) {
        this.id = id;
        this.vocab = vocab;
        this.hiragana = hiragana;
        this.imageurl = imageurl;
    }

    protected GoiDataModel(Parcel in)
    {
        id = in.readString();
        vocab = in.readString();
        hiragana = in.readString();
        imageurl = in.readString();
    }


    public static final Parcelable.Creator<GoiDataModel> CREATOR = new Parcelable.Creator<GoiDataModel>() {
        @Override
        public GoiDataModel createFromParcel(Parcel in) {
            return new GoiDataModel(in);
        }

        @Override
        public GoiDataModel[] newArray(int size) {
            return new GoiDataModel[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(vocab);
        dest.writeString(hiragana);
        dest.writeString(imageurl);
    }

    public String toString() {
        // return "Kanji: " + kanji + "";
        int labelWidth = 10; // Adjust the label width as needed.

        return "Words: " + vocab + "\n" + "Goi: " + hiragana;
    }

}
