package com.example.vladimir.uzbekistanexplorer.FragmentArticle;


import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;


public class OverviewPagerAdapter extends PagerAdapter {

    private String[] images;

    public OverviewPagerAdapter(String[] images){
        this.images = images;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView mPhoto = new PhotoView(container.getContext());
        String imageAddress = "file:///android_asset/images_for_article/" + images[position] + ".jpg";
        Glide.with(container.getContext()).load(imageAddress).into(mPhoto);
        container.addView(mPhoto, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return mPhoto;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
