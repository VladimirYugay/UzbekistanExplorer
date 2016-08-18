package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Content;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Constants;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.entity.ContentItem;
import java.util.ArrayList;

public class ContentPagerItem extends Fragment{

    String mLanguage;
    String mCity;
    String mPrefix;
    RecyclerAdapter mAdapter;


    public ContentPagerItem(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences mPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mPrefix = getArguments().getString(Constants.PREFIX);
        mCity = getArguments().getString(Constants.CITY);
        mLanguage = mPreferences.getString(Constants.LANGUAGE, null);

        RecyclerView mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerAdapter();
        new LoadData(mPrefix, mCity, mLanguage).execute();
        mRecycler.setAdapter(mAdapter);
    }


    private class LoadData extends AsyncTask<Void, Void, ArrayList<ContentItem>>{

        String dbPrefix, city, current_lang;
        protected LoadData(String dbPrefix, String city, String current_lang){
            this.dbPrefix = dbPrefix;
            this.city = city;
            this.current_lang = current_lang;
        }

        private ArrayList<ContentItem> getData(String dbPrefix, String city, String current_lang){
            ArrayList<ContentItem> arrayList = new ArrayList<>();
            DatabaseContent database = new DatabaseContent(getActivity(), dbPrefix, city, current_lang);
            Cursor names = database.getNames();
            while (!names.isAfterLast()){
                ContentItem item = new ContentItem();
                item.setName(names.getString(1));
                item.setDescription(names.getString(2));
                item.setAddress(names.getString(3));
                item.setImage(names.getString(4));
                item.setImages(names.getString(5));
                item.setLat(names.getDouble(6));
                item.setLon(names.getDouble(7));
                arrayList.add(item);
                names.moveToNext();
            }
            database.close();
            names.close();
            return arrayList;
        }

        @Override
        protected ArrayList<ContentItem> doInBackground(Void... params) {
            return getData(dbPrefix, city, current_lang);
        }

        @Override
        protected void onPostExecute(ArrayList<ContentItem> arrayList) {
            mAdapter.addAll(arrayList);
        }
    }

}
