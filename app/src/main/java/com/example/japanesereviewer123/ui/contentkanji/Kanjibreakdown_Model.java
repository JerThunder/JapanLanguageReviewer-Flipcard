package com.example.japanesereviewer123.ui.contentkanji;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Kanjibreakdown_Model implements Parcelable {
    String id;
    String KanjiChar;
    String KanjiMeaning;
    String KanjiDesc;
    String ImageUrl;
    String Kunyomi;
    String Onyomi;
    String Level;
    public Kanjibreakdown_Model(String id, String kanjiChar, String kanjiMeaning, String kanjiDesc, String imageUrl, String kunyomi, String onyomi, String level) {
        this.id = id;
        KanjiChar = kanjiChar;
        KanjiDesc = kanjiDesc;
        ImageUrl = imageUrl;
        KanjiMeaning = kanjiMeaning;
        Kunyomi = kunyomi;
        Onyomi = onyomi;
        Level = level;
    }

    protected Kanjibreakdown_Model(Parcel in) {
        id = in.readString();
        KanjiChar = in.readString();
        KanjiMeaning = in.readString();
        KanjiDesc = in.readString();
        ImageUrl = in.readString();
        Kunyomi = in.readString();
        Onyomi = in.readString();
        Level = in.readString();
    }

    public static final Creator<Kanjibreakdown_Model> CREATOR = new Creator<Kanjibreakdown_Model>() {
        @Override
        public Kanjibreakdown_Model createFromParcel(Parcel in) {
            return new Kanjibreakdown_Model(in);
        }

        @Override
        public Kanjibreakdown_Model[] newArray(int size) {
            return new Kanjibreakdown_Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKanjiChar() {
        return KanjiChar;
    }

    public void setKanjiChar(String kanjiChar) {
        KanjiChar = kanjiChar;
    }

    public String getKanjiMeaning() {
        return KanjiMeaning;
    }

    public void setKanjiMeaning(String kanjiMeaning) {
        KanjiMeaning = kanjiMeaning;
    }

    public String getKanjiDesc() {
        return KanjiDesc;
    }

    public void setKanjiDesc(String kanjiDesc) {
        KanjiDesc = kanjiDesc;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getKunyomi() {return Kunyomi;}

    public void setKunyomi(String kunyomi) {Kunyomi = kunyomi;}

    public String getOnyomi() {return Onyomi;}

    public void setOnyomi(String onyomi) {Onyomi = onyomi;}

    public String getLevel() {return Level;}

    public void setLevel(String level) {Level = level;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(KanjiChar);
        dest.writeString(KanjiMeaning);
        dest.writeString(KanjiDesc);
        dest.writeString(ImageUrl);
        dest.writeString(Kunyomi);
        dest.writeString(Onyomi);
        dest.writeString(Level);
    }
}
