package com.example.vladimir.uzbekistanexplorer.FragmentArticle;


import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.DepthPageTransformer;
import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class GalleryItemFragment extends Fragment{

    private String[] mImages;
    private int mPosition;

    public GalleryItemFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImages = getArguments().getStringArray("IMAGES");
        mPosition = getArguments().getInt("POSITION");
        return inflater.inflate(R.layout.fragment_gallery_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        }

        ViewPager viewPager = (ViewPager)view.findViewById(R.id.container);
        viewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager(), mImages));
        viewPager.setCurrentItem(mPosition);
        viewPager.setPageTransformer(false, new DepthPageTransformer());
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        String[] mImages;

        public SectionsPagerAdapter(FragmentManager fm, String[] images) {
            super(fm);
            mImages = images;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.POSITION, position);
            bundle.putString(Constants.IMAGE, mImages[position]);
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mImages.length;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        String url;
        int pos;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            url = getArguments().getString(Constants.IMAGE);
            pos = getArguments().getInt(Constants.POSITION);
            return inflater.inflate(R.layout.image_view, container, false);
        }


        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final ImageView imageView = (ImageView) view.findViewById(R.id.image);
            String imageAddress = "file:///android_asset/images_for_article/" + url + ".jpg";
            Picasso.with(getActivity()).load(imageAddress).into(imageView);
        }
    }
}
