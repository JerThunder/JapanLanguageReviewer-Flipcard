package com.example.japanesereviewer123;

public class Kanjibreakdown_Model {
    String id;
    String KanjiChar;
    String KanjiMeaning;
    String KanjiDesc;
    String ImageUrl;

    public Kanjibreakdown_Model(String id, String kanjiChar, String kanjiMeaning, String kanjiDesc, String imageUrl) {
        this.id = id;
        KanjiChar = kanjiChar;
        KanjiDesc = kanjiDesc;
        ImageUrl = imageUrl;
        KanjiMeaning = kanjiMeaning;
    }

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
}
