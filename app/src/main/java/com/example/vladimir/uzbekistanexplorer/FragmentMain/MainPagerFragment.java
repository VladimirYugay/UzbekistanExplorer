package com.example.vladimir.uzbekistanexplorer.FragmentMain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.FragmentContent.ContentFragment;
import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.MainItem;

import java.util.ArrayList;

public class MainPagerFragment extends Fragment {

    String current_language;
    int signature;
    BroadcastReceiver broadcastReceiver;
    TextView mText;

    public MainPagerFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_main_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        current_language = sharedPreferences.getString(Constants.LANGUAGE, null);
        signature = getArguments().getInt(Constants.CITY_CODE);

        mText = (TextView)view.findViewById(R.id.text);
        ImageView mImage = (ImageView)view.findViewById(R.id.image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                ContentFragment fragment = new ContentFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.CITY_CODE, signature);
                fragment.setArguments(bundle);
                mainActivity.changeFragment(fragment);

            }
        });

        new LoadData(current_language, signature).execute();
    }

    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter(Constants.UPDATE);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String lang = intent.getStringExtra(Constants.LANGUAGE);
                    current_language = lang;
                    new LoadData(current_language, signature).execute();
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

    private class LoadData extends AsyncTask<Void, Void, ArrayList<MainItem>>{
        String lang;
        int signature;
        protected LoadData(String lang, int signature){
            this.lang = lang;
            this.signature = signature;
        }

        @Override
        protected ArrayList<MainItem> doInBackground(Void... params) {
            return getData(lang);
        }

        @Override
        protected void onPostExecute(ArrayList<MainItem> arrayList) {
            mText.setText(arrayList.get(signature).getDescription());
        }
    }
}
