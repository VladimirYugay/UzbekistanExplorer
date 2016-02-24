package com.example.vladimir.uzbekistanexplorer.FragmentContent;

import android.annotation.SuppressLint;
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

import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.ContentItem;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ContentPagerItem extends Fragment{

    String current_lang;
    String city;
    String dbPrefix;
    RecyclerAdapter mAdapter;

    public ContentPagerItem(){}

    @SuppressLint("ValidFragment")
    public ContentPagerItem(String dbPrefix, String city, String current_lang){
        this.dbPrefix = dbPrefix;
        this.city = city;
        this.current_lang = current_lang;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_pager_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerAdapter();
        new LoadContent(dbPrefix, city, current_lang).execute();
        mRecycler.setAdapter(mAdapter);


    }

    public ArrayList<ContentItem> getData(String dbPrefix, String city, String current_lang){
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
            arrayList.add(item);
            names.moveToNext();
        }
        database.close();
        names.close();
        return arrayList;
    }


    private class LoadContent extends AsyncTask<Void, Void, ArrayList<ContentItem>>{

        String dbPrefix, city, current_lang;
        protected LoadContent(String dbPrefix, String city, String current_lang){
            this.dbPrefix = dbPrefix;
            this.city = city;
            this.current_lang = current_lang;
        }

        @Override
        protected ArrayList<ContentItem> doInBackground(Void... params) {
            ArrayList<ContentItem> arrayList = new ArrayList<>();
            arrayList = getData(dbPrefix, city, current_lang);
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ContentItem> arrayList) {
            mAdapter.addAll(arrayList);
        }
    }
}
