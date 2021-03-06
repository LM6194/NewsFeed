package com.example.luis.newsfeed;

/**
 * Created by Luis on 10/2/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Luis on 3/2/2017.
 */

/**
 * Loads a list of newss by using an AsyncTask to perform the network request
 * to the given URL.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url    to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        Log.i(LOG_TAG, "TEST: onStartLoading() called...");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        Log.i(LOG_TAG, "TEST: loadInBackground() called...");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response,
        //and extract a list of newss.
        List<News> latestNews = QueryUtils.fetchNewsData(mUrl);
        return latestNews;
    }

}
