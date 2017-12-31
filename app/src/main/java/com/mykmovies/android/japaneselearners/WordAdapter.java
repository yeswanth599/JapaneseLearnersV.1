package com.mykmovies.android.japaneselearners;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yeswa on 12-09-2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    private static final String LOG_TAG = WordAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param words A List of AndroidFlavor objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceID) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourceId=colorResourceID;
    } @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Word currentWord=getItem(position);
        TextView textViewJapanese=(TextView)listItemView.findViewById(R.id.list_item_adapter_support_japanese);
        textViewJapanese.setText(currentWord.getJapaneseTranslation());
        TextView textViewEnglish=(TextView)listItemView.findViewById(R.id.list_item_adapter_support_english);
        textViewEnglish.setText(currentWord.getEnglishTranslation());
        ImageView imageView=(ImageView)listItemView.findViewById(R.id.list_item_adapter_support_image);
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImage_translation());
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }
        View textContainer=listItemView.findViewById(R.id.list_item_adapter_support_innerview);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
