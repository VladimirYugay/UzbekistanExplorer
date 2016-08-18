package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Constants;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.FABScrollBehavior;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Phrasebook.PhrasebookFragment;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Hints.HintsFragment;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.MainActivity;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.ToolbarActionItemTarget;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;


public class MainFragment extends Fragment{

    String mLanguage;
    MaterialDialog mDialog;
    TabLayout mTabLayout;
    String[] mArray;
    ShowcaseView mShowCase;
    int mCounter = 0;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        }

        mArray = getActivity().getResources().getStringArray(R.array.tabs_rus);
        final String[] language_codes = getActivity().getResources().getStringArray(R.array.languages_codes);
        final String[] languages = getActivity().getResources().getStringArray(R.array.languages);

        final Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitle(getActivity().getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_language:
                        mDialog.setTitle(updateDialog(mLanguage));
                        mDialog.show();
                        break;
                    default:
                        HintsFragment fragment = new HintsFragment();
                        MainActivity activity = (MainActivity) getActivity();
                        activity.changeFragment(fragment);
                }
                return true;
            }
        });


        final ViewPager mPager = (ViewPager)view.findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(3);
        setupViewPager(mPager);

        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);
        updateTabs(mLanguage);



        mDialog = new MaterialDialog.Builder(getActivity())
                .title(updateDialog(mLanguage))
                .items(languages)
                .backgroundColor(getActivity().getResources().getColor(R.color.colorPrimary))
                .itemsColor(getActivity().getResources().getColor(R.color.white))
                .titleColor(getActivity().getResources().getColor(R.color.white))
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



        FABScrollBehavior behavior = new FABScrollBehavior(getActivity(), null);
        final FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
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

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 16)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        final String[] titles = getIntroTitles(mLanguage);
        final String[] descriptions = getIntroDescriptions(mLanguage);
        mCounter = 0;
        mShowCase = new ShowcaseView.Builder(getActivity(), true)
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(new ToolbarActionItemTarget(toolbar, R.id.action_language))
                .singleShot(888)
                .setContentTitle(titles[mCounter])
                .setContentText(descriptions[mCounter++])
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (mCounter) {
                            case 1:
                                mShowCase.setShowcase(new ToolbarActionItemTarget(toolbar, R.id.action_tips), true);
                                mShowCase.setContentTitle(titles[mCounter]);
                                mShowCase.setContentText(descriptions[mCounter]);
                                break;
                            case 2:
                                mShowCase.setShowcase(new ViewTarget(fab), true);
                                mShowCase.setContentTitle(titles[mCounter]);
                                mShowCase.setContentText(descriptions[mCounter]);
                                break;
                            case 3:
                                mShowCase.setShowcase(new ViewTarget(mPager), true);
                                mShowCase.setContentTitle(titles[mCounter]);
                                mShowCase.setContentText(descriptions[mCounter]);
                                break;
                            case 4:
                                mShowCase.hide();
                        }
                        mCounter++;
                    }
                })
                .build();
        mShowCase.setButtonPosition(lps);
        mShowCase.setButtonText(getIntroButtonName(mLanguage));
        mShowCase.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
    }

    private String getIntroButtonName(String language){
        switch (language){
            case "rus":
                return "Есть!";
            default:
                return "Got it!";
        }
    }

    private String[] getIntroTitles(String language){
        switch (language){
            case "rus":
                return getActivity().getResources().getStringArray(R.array.rus_intro_title);
            default:
                return getActivity().getResources().getStringArray(R.array.eng_intro_title);
        }
    }

    private String[] getIntroDescriptions(String language){
        switch (language){
            case "rus":
                return getActivity().getResources().getStringArray(R.array.rus_intro_description);
            default:
                return getActivity().getResources().getStringArray(R.array.eng_intro_description);
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        MainPagerAdapter adapter = new MainPagerAdapter(getChildFragmentManager());

        MainPagerFragment fragment = new MainPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.LANGUAGE, mLanguage);
        bundle.putInt(Constants.CITY_CODE, 0);
        fragment.setArguments(bundle);
        adapter.addFrag(fragment, mArray[0]);

        MainPagerFragment fragment2 = new MainPagerFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.LANGUAGE, mLanguage);
        bundle2.putInt(Constants.CITY_CODE, 1);
        fragment2.setArguments(bundle2);
        adapter.addFrag(fragment2, mArray[1]);

        viewPager.setAdapter(adapter);
    }

    private void updateTabs(String lang){
        switch (lang){
            case "rus":
                mArray = getActivity().getResources().getStringArray(R.array.tabs_rus);
                break;
            case "eng":
                mArray = getActivity().getResources().getStringArray(R.array.tabs_eng);
        }
        for(int i = 0; i < 2; i++){
            mTabLayout.getTabAt(i).setText(mArray[i]);
        }
    }


    String updateDialog(String language){
        switch (language){
            case "rus":
                return "Выберите язык";
            case "eng":
                return "Choose the language";
            default:
                return "Choose the language";
        }
    }
}
