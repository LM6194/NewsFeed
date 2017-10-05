package com.example.luis.newsfeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Luis on 10/2/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter( Context context, List<News> latestNews) {
        super(context, 0, latestNews);
    }
    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list earthquake
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }
        // find earthquake at a given position in the earthquake list
        News currentNews = getItem(position);

        // find TextView for id news_title
        TextView titleTextView = listItemView.findViewById(R.id.news_title);
        String stringTitle = currentNews.getTitle();
        titleTextView.setText(stringTitle);

        // find TextView for id news_section
        TextView sectionTextView = listItemView.findViewById(R.id.news_section);
        String stringSection = currentNews.getSection();
        sectionTextView.setText(stringSection);

        // find TextView for id news_author
        TextView authorTextView = listItemView.findViewById(R.id.news_author);
        String stringAuthor = currentNews.getAuthor();
        authorTextView.setText(stringAuthor);

        // find TextView for id news_date
        TextView dateTextView = listItemView.findViewById(R.id.news_date);
        String stringDate = currentNews.getDate();

        // separate date from time
        String[] part = stringDate.split("T");
        Log.i(LOG_TAG, "Test: value of part[0] = "+part[0]);

        dateTextView.setText(part[0]);


        return listItemView;
    }
}
