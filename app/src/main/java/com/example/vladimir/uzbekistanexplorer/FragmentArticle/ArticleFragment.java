package com.example.vladimir.uzbekistanexplorer.FragmentArticle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.FABScrollBehavior;
import com.example.vladimir.uzbekistanexplorer.R;


public class ArticleFragment extends Fragment {

    Toolbar mToolbar;
    String name, images, description;

    public ArticleFragment(){}

    @SuppressLint("ValidFragment")
    public ArticleFragment(String images, String description, String name){
        this.name = name;
        this.description = description;
        this.images = images;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name);
        outState.putString("description", description);
        outState.putString("images", images);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            name = savedInstanceState.getString("name");
            description = savedInstanceState.getString("description");
            images = savedInstanceState.getString("images");
        }

        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        mToolbar.setTitle(name);

        ViewPager mPager = (ViewPager)view.findViewById(R.id.view_pager);
        ArticlePagerAdapter mAdapter = new ArticlePagerAdapter(images, getActivity());
        mPager.setPageTransformer(false, new ZoomOutPageTransformer());
        mPager.setAdapter(mAdapter);

        TextView mText = (TextView)view.findViewById(R.id.text);
        mText.setText(description);

        FloatingActionButton mFab = (FloatingActionButton)view.findViewById(R.id.fab);
        FABScrollBehavior behavior = new FABScrollBehavior();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mFab.getLayoutParams();
        params.setBehavior(behavior);
    }
}
