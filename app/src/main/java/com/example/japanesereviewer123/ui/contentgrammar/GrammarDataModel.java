package com.example.japanesereviewer123.ui.contentgrammar;

import android.os.Parcelable;
import android.os.Parcel;

import androidx.annotation.NonNull;


public class GrammarDataModel implements Parcelable {


    private String id;
    private String topic;
    private String content;

    public GrammarDataModel(String id, String topic, String content) {
        this.id = id;
        this.topic = topic;
        this.content = content;

    }

    public static final Creator<GrammarDataModel> CREATOR = new Creator<GrammarDataModel>() {
        @Override
        public GrammarDataModel createFromParcel(Parcel in) {
            return new GrammarDataModel(in);
        }

        @Override
        public GrammarDataModel[] newArray(int size) {
            return new GrammarDataModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    protected GrammarDataModel(Parcel in) {
        id = in.readString();
        topic = in.readString();
        content = in.readString();

    }



    public String toString() {
        // return "Kanji: " + kanji + "";
     //   int labelWidth = 10; // Adjust the label width as needed.

        return topic;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(topic);
        dest.writeString(content);
    }
}
