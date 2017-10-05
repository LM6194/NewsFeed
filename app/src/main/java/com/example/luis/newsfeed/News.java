package com.example.luis.newsfeed;

/**
 * Created by Luis on 10/1/2017.
 */

public class News {
    // Title of the article
    private String mTitle;
    // Section name
    private String mSection;
    // Author name
    private String mAuthor;
    // Date published
    private String mDate;
    // URL of article
    private String mURL;

    /**
     * Create a new object
     * @param title is the Title of the article.
     * @param  section is the section where the article is.
     * @param author is the author of the article.
     * @param date is the date the article was publish
     */
    public News(String title, String section, String author, String date, String url){
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mDate = date;
        mURL = url;
    }
    /**@return the title*/
    public String getTitle(){ return  mTitle;}

    /**@return the section*/
    public String getSection(){ return  mSection;}

    /** @return the author*/
    public String getAuthor(){ return mAuthor;}

    /** @return the date*/
    public String getDate(){ return mDate;}

    /** @return the URL*/
    public String getURL(){ return mURL;}

}
