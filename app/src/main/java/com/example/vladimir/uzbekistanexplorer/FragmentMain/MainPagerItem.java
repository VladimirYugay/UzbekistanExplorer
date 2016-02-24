package com.example.vladimir.uzbekistanexplorer.FragmentMain;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.vladimir.uzbekistanexplorer.FragmentContent.ContentFragment;
import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.MainItem;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainPagerItem extends Fragment {

    String current_language = "rus";
    int signature = 0;
    BroadcastReceiver broadcastReceiver;
    TextView mText;

    public MainPagerItem(){}

    @SuppressLint("ValidFragment")
    public MainPagerItem(String current_language, int signature){
        this.current_language = current_language;
        this.signature = signature;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_pager_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = (TextView)view.findViewById(R.id.text);
        ImageView mImage = (ImageView)view.findViewById(R.id.image_anchor);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeFragment(new ContentFragment(current_language, signature));

            }
        });

        updateFragment(current_language, signature);
    }

    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("MAIN_UPDATE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String lang = intent.getStringExtra("Language");
                    updateFragment(lang, signature);
                }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    public void updateFragment(final String lang, final int signature){
        current_language = lang;
        rx.Observable<ArrayList<MainItem>> arrayListObservable = rx.Observable.create(new rx.Observable.OnSubscribe<ArrayList<MainItem>>() {
            @Override
            public void call(Subscriber<? super ArrayList<MainItem>> subscriber) {
                try {
                    ArrayList<MainItem> arrayList = getData(lang);
                    subscriber.onNext(arrayList);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        arrayListObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<MainItem>>() {
                    @Override
                    public void call(ArrayList<MainItem> mainItems) {
                        mText.setText(mainItems.get(signature).getDescription());
                    }
                });
    }

    public ArrayList<MainItem> getData(String language){
        String code = "main_" + language;
        ArrayList<MainItem> arrayList = new ArrayList<>();
        DatabaseMain database = new DatabaseMain(getActivity(), code);
        Cursor names = database.getNames();
        while (!names.isAfterLast()){
            MainItem item = new MainItem();
            item.setCity(names.getString(1));
            item.setDescription(names.getString(2));
            item.setNick(names.getString(3));
            arrayList.add(item);
            names.moveToNext();
        }
        database.close();
        names.close();
        return arrayList;
    }
}
