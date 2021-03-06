package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Article;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Constants;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.squareup.picasso.Picasso;

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

        HackyViewPager viewPager = (HackyViewPager)view.findViewById(R.id.container);
        viewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager(), mImages));
        viewPager.setCurrentItem(mPosition);
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
//            final String imageAddress = "file:///android_asset/images_for_article/" + url + ".jpg";
            final String imageAddress = "http://vladimiryugay.square7.ch/UzbekistanExplorer/" + url + ".jpg";
            PhotoView imageView = (PhotoView)view.findViewById(R.id.imageView);
            final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//            Picasso.with(getActivity()).load(imageAddress).into(imageView);
            Glide.with(getActivity())
                    .load(imageAddress)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView);

        }
    }
}
