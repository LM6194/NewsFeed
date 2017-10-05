package com.example.luis.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>{

    public static final String LOG_TAG = NewsActivity.class.getName();
    /** URL for the news data from The Guardian*/
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search";

    // Constant for the API search key
    private static final String API_KEY = "api-key";
    // Constant value for the API KEY
    private static final String KEY = "428fc2bd-2a0f-428b-860d-a5c9c66ebe9b";
    /**
     * Constant value for the news loader ID. We can choose an integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /** Adapter for the list of News */
    private NewsAdapter mAdapter;

    /** TextView that is displayed when the list is empty*/
    private TextView mEmptyStateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView)findViewById(R.id.list);

        // Create a new adapter that take an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        mEmptyStateView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateView);

        // onClicklistener to detect if the user wants more info from website
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // find the current news that was click on
                News newsUrl = mAdapter.getItem(i);
                // Convert the String URL into URI object (to pass into
                // the intent constructor)
                Uri newsUri = Uri.parse(newsUrl.getURL());
                // creat a new intent to view the news URI
                Intent urlIntent = new Intent(Intent.ACTION_VIEW,newsUri);
                startActivity(urlIntent);
            }
        });

        // get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get  details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // if there is a network connection, fetch data
        if(networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in ordet to interact with loaders
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above
            // and pass in null for the bundle. Pass in this activity for the LoaderCallBacks parameter
            // (which is valid because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }else{
            //Otherwise, display error
            // first, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // updat empty state with no connection error message
            mEmptyStateView.setText(R.string.no_internet_connection);
        }
    }
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args){
        Log.i(LOG_TAG, "started loader check");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchSection = sharedPreferences.getString(
                getString(R.string.setting_search_by_key),
                getString(R.string.setting_search_by_label));


        // Create an URI
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Log.i(LOG_TAG, "url before parsing: "+GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //Append parameter to the search URL
        uriBuilder.appendQueryParameter("q", searchSection);
        uriBuilder.appendQueryParameter(API_KEY,KEY);

        Log.i(LOG_TAG, "Test value of uriBuilder.toString: " + uriBuilder.toString());
        // Create a new loader for the given URL
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> latestNews) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquake found."
        mEmptyStateView.setText(R.string.no_news);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        //If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // date set. This will trigger the ListView to update.
        if(latestNews != null && !latestNews.isEmpty()){
            mAdapter.addAll(latestNews);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.i(LOG_TAG, "TEST: calling onLoaderReset() called...");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
