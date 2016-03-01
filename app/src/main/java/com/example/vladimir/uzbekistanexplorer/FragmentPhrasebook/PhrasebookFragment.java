package com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladimir.uzbekistanexplorer.R;


public class PhrasebookFragment extends Fragment {

    String language;

    public PhrasebookFragment(){}

    @SuppressLint("ValidFragment")
    public PhrasebookFragment(String language){
        this.language = language;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Phrase", language);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_phrases, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) language = savedInstanceState.getString("Phrase");

        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setTitle(getHeader(language));
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        RecyclerView mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecycler.setAdapter(new PhaseRecyclerAdapter(language, getPlaces(language)));
    }


    public String[] getPlaces(String language){
        switch (language){
            case "rus":
                return getActivity().getResources().getStringArray(R.array.rus_places);
            default:
                return getActivity().getResources().getStringArray(R.array.eng_places);
        }
    }

    public String getHeader(String language){
        switch (language){
            case "rus":
                return getActivity().getResources().getString(R.string.rus_phrasebook);
            default:
                return getActivity().getResources().getString(R.string.eng_phrasebook);
        }
    }
}
