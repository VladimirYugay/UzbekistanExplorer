package com.example.vladimir.uzbekistanexplorer.FragmentContent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.R;
import com.squareup.picasso.Picasso;

public class ContentFragment extends Fragment {

    String mLanguage, mCity;
    int mCityCode;
    TabLayout mTabLayout;
    Toolbar mToolbar;
    ImageView mImage;
    SharedPreferences mPreferences;

    public ContentFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPreferences = getActivity().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mLanguage = mPreferences.getString(Constants.LANGUAGE, null);
        mCityCode = getArguments().getInt(Constants.CITY_CODE);
        mCity = getCityName(mCityCode);

        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        updateToolbar(mCityCode, mLanguage);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        ViewPager mPager = (ViewPager)view.findViewById(R.id.view_pager);
        setupViewPager(mPager);

        mImage = (ImageView)view.findViewById(R.id.header);
        updateImage(mCityCode);

        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mPager);
        setupTabLayout(mTabLayout);
    }


    public void setupViewPager(ViewPager viewPager) {
        ContentPagerAdapter adapter = new ContentPagerAdapter(getChildFragmentManager());
        String[] array = getActivity().getResources().getStringArray(R.array.codes_activities);
        for(int i = 0; i < 3; i++){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CITY, mCity);
            bundle.putString(Constants.PREFIX, array[i]);
            ContentPagerItem fragment = new ContentPagerItem();
            fragment.setArguments(bundle);
            adapter.addFrag(fragment);
        }
        viewPager.setAdapter(adapter);
    }

    public void setupTabLayout(TabLayout tabLayout){
        if(tabLayout != null) {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_map_marker_48);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_food_48);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_hotel_48);
        }
    }

    public String getCityName(int i){
        switch (i){
            case 0:
                return "tashkent";
            case 1:
                return "samarkand";
            case 2:
                return "buxara";
            default:
                return "xiva";
        }
    }

    public void updateToolbar(int position, String lang){
        String[] array = getActivity().getResources().getStringArray(R.array.tabs_rus);
        if(lang != null) {
            switch (lang) {
                case "rus":
                    array = getActivity().getResources().getStringArray(R.array.tabs_rus);
                    break;
                default:
                    array = getActivity().getResources().getStringArray(R.array.tabs_eng);
            }
        }
        mToolbar.setTitle(array[position]);
    }

    public void updateImage(int position){
        String imageAddress = null;
        switch (position){
            case 0:
                imageAddress = "file:///android_asset/images_for_article/amir_square4.jpg";
                break;
            case 1:
                break;
            case 2:
                break;
            default:
        }
        Picasso.with(getContext()).load(imageAddress).into(mImage);
    }



}
