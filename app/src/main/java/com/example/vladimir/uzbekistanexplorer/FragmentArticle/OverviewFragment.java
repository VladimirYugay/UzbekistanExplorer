package com.example.vladimir.uzbekistanexplorer.FragmentArticle;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.ZoomOutPageTransformer;

public class OverviewFragment extends Fragment{

    private String[] images = null;
    private FixedViewPager mPager;
    private int position;

    public OverviewFragment(){}

    @SuppressLint("ValidFragment")
    public OverviewFragment(String[] images, int position){
        this.images = images;
        this.position = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("Overview", images);
        outState.putInt("OverviewPosition", position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {images = savedInstanceState.getStringArray("Overview");
            position = savedInstanceState.getInt("OverviewPosition");
        }

        mPager = (FixedViewPager)view.findViewById(R.id.view_pager);
        mPager.setPageTransformer(false, new ZoomOutPageTransformer());
        mPager.setAdapter(new OverviewPagerAdapter(images));
        mPager.setCurrentItem(position);
        mPager.setOffscreenPageLimit(3);
    }
}
