package com.example.vladimir.uzbekistanexplorer.FragmentContent;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.vladimir.uzbekistanexplorer.FragmentMain.MainPagerAdapter;
import com.example.vladimir.uzbekistanexplorer.FragmentMain.MainPagerItem;
import com.example.vladimir.uzbekistanexplorer.R;

public class ContentFragment extends Fragment {

    String current_lang = "rus";
    String city = "tashkent";

    public ContentFragment(){}

    @SuppressLint("ValidFragment")
    public ContentFragment(String current_lang, int city_code) {
        this.current_lang = current_lang;
        this.city = getCityName(city_code);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        final ViewPager mPager = (ViewPager)view.findViewById(R.id.view_pager);
        mPager.setOffscreenPageLimit(3);
        setupViewPager(mPager);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    public void setupViewPager(ViewPager viewPager) {
        ContentPagerAdapter adapter = new ContentPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new ContentPagerItem("places_", city, current_lang), "Достопримечательности");
        adapter.addFrag(new ContentPagerItem("food_", city, current_lang), "Рестораны");
        adapter.addFrag(new ContentPagerItem("hotels_", city, current_lang), "Отели");
        viewPager.setAdapter(adapter);
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
}
