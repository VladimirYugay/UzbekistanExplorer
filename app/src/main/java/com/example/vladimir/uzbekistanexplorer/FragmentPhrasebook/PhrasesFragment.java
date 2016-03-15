package com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook;

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
import com.example.vladimir.uzbekistanexplorer.FragmentMain.DatabaseMain;
import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.MainItem;
import com.example.vladimir.uzbekistanexplorer.entity.Phrases;

import java.util.ArrayList;

public class
        PhrasesFragment extends Fragment {

    String mPlace, mTitle, mLanguage;
    PhraseAdapter mAdapter;
    SharedPreferences mPreferences;

    public PhrasesFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mLanguage = mPreferences.getString(Constants.LANGUAGE, null);
        mTitle = getArguments().getString(Constants.TITLE);
        mPlace = getArguments().getString(Constants.PLACE);
        return inflater.inflate(R.layout.recycler_phrases, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        mAdapter = new PhraseAdapter(isNative(mLanguage));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        new LoadPhrases(mPlace).execute();

    }

    public boolean isNative(String language){
        if (language.equals("rus") || language.equals("uzb")) return true;
        return false;
    }

    public ArrayList<Phrases> getData(String table){
        ArrayList<Phrases> arrayList = new ArrayList<>();
        DatabasePhrases database = new DatabasePhrases(getActivity(), table);
        Cursor names = database.getNames();
        while (!names.isAfterLast()){
            Phrases item = new Phrases();
            item.setForeign(names.getString(1));
            item.setRussian(names.getString(2));
            item.setRussian_transcription(names.getString(3));
            item.setUzbek(names.getString(4));
            arrayList.add(item);
            names.moveToNext();
        }
        database.close();
        names.close();
        return arrayList;
    }

    private class LoadPhrases extends AsyncTask<Void, Void, ArrayList<Phrases>>{

        private String table;

        public LoadPhrases(String table){
            this.table = table;
        }

        @Override
        protected ArrayList<Phrases> doInBackground(Void... params) {
            return getData(table);
        }

        @Override
        protected void onPostExecute(ArrayList<Phrases> arrayList) {
            mAdapter.addAll(arrayList);
        }
    }

}
