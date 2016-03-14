package com.example.vladimir.uzbekistanexplorer.FragmentMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.FABScrollBehavior;
import com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook.PhrasebookFragment;
import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;


public class MainFragment extends Fragment {

    String mLanguage;
    MaterialDialog mDialog;
    TabLayout mTabLayout;
    String[] mArray;

    public MainFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mLanguage = sharedPreferences.getString(Constants.LANGUAGE, null);

        mArray = getActivity().getResources().getStringArray(R.array.tabs_rus);
        final String[] language_codes = getActivity().getResources().getStringArray(R.array.languages_codes);
        final String[] languages = getActivity().getResources().getStringArray(R.array.languages);

        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        final ViewPager mPager = (ViewPager)view.findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(3);
        setupViewPager(mPager);

        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        updateTabs(mLanguage);

        mDialog = new MaterialDialog.Builder(getActivity())
                .title("Choose the language")
                .items(languages)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        mLanguage = language_codes[i];
                        updateTabs(mLanguage);
                        SharedPreferences sp = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
                        sp.edit().putString(Constants.LANGUAGE, language_codes[i]).apply();

                        Intent intent = new Intent();
                        intent.setAction(Constants.UPDATE);
                        intent.putExtra(Constants.LANGUAGE , mLanguage);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }
                })
                .build();



        FABScrollBehavior behavior = new FABScrollBehavior();
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
        params.setBehavior(behavior);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                PhrasebookFragment fragment = new PhrasebookFragment();
                activity.changeFragment(fragment);
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mian_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_language:
                mDialog.show();
                break;
            default:
                Toast.makeText(getContext(), "TIPS", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void setupViewPager(ViewPager viewPager) {
        MainPagerAdapter adapter = new MainPagerAdapter(getChildFragmentManager());
        for(int i = 0; i < 4; i++){
            MainPagerItem fragment = new MainPagerItem();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.LANGUAGE, mLanguage);
            bundle.putInt(Constants.CITY_CODE, i);
            fragment.setArguments(bundle);
            adapter.addFrag(fragment, mArray[i]);
        }
        viewPager.setAdapter(adapter);
    }

    public void updateTabs(String lang){
        switch (lang){
            case "rus":
                mArray = getActivity().getResources().getStringArray(R.array.tabs_rus);
                break;
            case "eng":
                mArray = getActivity().getResources().getStringArray(R.array.tabs_eng);
        }
        for(int i = 0; i < mArray.length; i++){
            mTabLayout.getTabAt(i).setText(mArray[i]);
        }
    }
}
