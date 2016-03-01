package com.example.vladimir.uzbekistanexplorer.FragmentContent;

import android.annotation.SuppressLint;
import android.database.Cursor;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("current_lang", current_lang);
        outState.putString("city", city);
        outState.putString("dbPrefix", dbPrefix);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            dbPrefix = savedInstanceState.getString("dbPrefix");
            city = savedInstanceState.getString("city");
            current_lang = savedInstanceState.getString("current_lang");
        }

        RecyclerView mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerAdapter();
        rx.Observable<ArrayList<ContentItem>> arrayListObservable = rx.Observable.create(new rx.Observable.OnSubscribe<ArrayList<ContentItem>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ContentItem>> subscriber) {
                try {
                    ArrayList<ContentItem> arrayList = getData(dbPrefix, city, current_lang);
                    subscriber.onNext(arrayList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        arrayListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<ContentItem>>() {
                    @Override
                    public void call(ArrayList<ContentItem> mainItems) {
                        mAdapter.addAll(mainItems);
                    }
                });
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
}
