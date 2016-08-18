package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Main;

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

import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Constants;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Content.ContentFragment;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.MainActivity;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.entity.MainItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainPagerFragment extends Fragment {

    String mLanguage;
    int mCityCode;
    BroadcastReceiver mBroadcastReceiver;
    TextView mText;
    final String[] mImages = {"file:///android_asset/images_for_content/tashkent.jpg",
            "file:///android_asset/images_for_content/samarkand.jpg",
            "file:///android_asset/images_for_content/tashkent.jpg",
            "file:///android_asset/images_for_content/tashkent.jpg", };

    public MainPagerFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mLanguage = sharedPreferences.getString(Constants.LANGUAGE, null);
        mCityCode = getArguments().getInt(Constants.CITY_CODE);

        mText = (TextView)view.findViewById(R.id.text);
        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                ContentFragment fragment = new ContentFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.CITY_CODE, mCityCode);
                fragment.setArguments(bundle);
                mainActivity.changeFragment(fragment);

            }
        });
        Picasso.with(getActivity()).load(mImages[mCityCode]).fit().into(imageView);
        new LoadData(mLanguage, mCityCode).execute();
    }

    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter(Constants.UPDATE);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String lang = intent.getStringExtra(Constants.LANGUAGE);
                    mLanguage = lang;
                    new LoadData(mLanguage, mCityCode).execute();
                }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadcastReceiver, intentFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }


    private class LoadData extends AsyncTask<Void, Void, ArrayList<MainItem>>{
        String lang;
        int signature;
        protected LoadData(String lang, int signature){
            this.lang = lang;
            this.signature = signature;
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
