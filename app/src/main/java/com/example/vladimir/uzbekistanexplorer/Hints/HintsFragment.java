package com.example.vladimir.uzbekistanexplorer.Hints;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.Hint;

import java.util.ArrayList;

public class HintsFragment extends Fragment{

    String mLanguage;
    HintsAdapter mAdapter;

    public HintsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tips, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mLanguage = sharedPreferences.getString(Constants.LANGUAGE, null);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        toolbar.setTitle(getTitle(mLanguage));
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HintsAdapter();
        new LoadTips(mLanguage).execute();
        recyclerView.setAdapter(mAdapter);
    }

    public String getTitle(String language){
        switch (language){
            case "rus":
                return getActivity().getResources().getString(R.string.rus_tips);
            default:
                return getActivity().getResources().getString(R.string.eng_tips);
        }
    }

    public class LoadTips extends AsyncTask<Void, Void, ArrayList<Hint>> {

        String mLanguage;

        public LoadTips(String language){
            mLanguage = language;
        }

        @Override
        protected ArrayList<Hint> doInBackground(Void... params) {
            return getData(mLanguage);
        }

        @Override
        protected void onPostExecute(ArrayList<Hint> tips) {
            mAdapter.addAll(tips);
        }

        public ArrayList<Hint> getData(String language){
            ArrayList<Hint> arrayList = new ArrayList<>();
            DatabaseHints database = new DatabaseHints(getActivity(), language);
            Cursor names = database.getNames();
            while (!names.isAfterLast()){
                Hint tip = new Hint();
                tip.setName(names.getString(1));
                tip.setTip(names.getString(2));
                arrayList.add(tip);
                names.moveToNext();
            }
            database.close();
            names.close();
            return arrayList;
        }
    }
}
