package com.reddit.bs.reddit_client;

import android.graphics.Bitmap;

/**
 * Created by Gustavo Puche on 1/08/16.
 */
public class Card {
    private Bitmap image;
    private String thumbnail;
    private String date;
    private String title;
    private String author;
    private String score;
    private String comments;
    private String url;

    public Card(String thumbnail,String date, String title, String author,
                String score, String comments, String url) {
        this.thumbnail = thumbnail;
        this.date = date;
        this.title = title;
        this.author = author;
        this.score = score;
        this.comments = comments;
        this.url = url;
        this.image = null;

    }

    public void setImage(Bitmap image){ this.image = image;}

    public Bitmap getImage(){ return image;}

    public String getThumbnail(){ return  thumbnail;}

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getScore() {
        return score;
    }

    public String getComments() {
        return comments;
    }

    public String getUrl()  {return url;}
}
