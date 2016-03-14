package com.example.vladimir.uzbekistanexplorer.FragmentArticle;


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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vladimir.uzbekistanexplorer.DepthPageTransformer;
import com.example.vladimir.uzbekistanexplorer.R;

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
        FixedViewPager viewPager = (FixedViewPager)view.findViewById(R.id.container);
        viewPager.setAdapter(new SectionPagerAdapter(getChildFragmentManager(), mImages));
        viewPager.setCurrentItem(mPosition);
        viewPager.setPageTransformer(false, new DepthPageTransformer());
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter{

        private String[] mImages;

        public SectionPagerAdapter(FragmentManager fm, String[] images) {
            super(fm);
            mImages = images;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position, mImages[position]);
        }

        @Override
        public int getCount() {
            return mImages.length;
        }
    }

    private static class PlaceholderFragment extends Fragment {

        String url;
        int position;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG_URL = "image_url";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.image_view, container, false);
            final PhotoView imageView = (PhotoView) rootView.findViewById(R.id.detail_image);
            String imageAddress = "file:///android_asset/images_for_article/" + url + ".jpg";
            Glide.with(getActivity()).load(imageAddress).thumbnail(0.1f).into(imageView);
            return rootView;
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.position = args.getInt(ARG_SECTION_NUMBER);
            this.url = args.getString(ARG_IMG_URL);
        }

        public static PlaceholderFragment newInstance(int sectionNumber, String url) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMG_URL, url);
            fragment.setArguments(args);
            return fragment;
        }
    }
}
