package com.example.vladimir.uzbekistanexplorer.FragmentArticle;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;
import com.squareup.picasso.Picasso;


public class ArticlePagerAdapter extends PagerAdapter {

    Context context;
    String[] mList;
    LayoutInflater inflater;

    public ArticlePagerAdapter(){}

    public ArticlePagerAdapter(String images, Context context){
        if(images != null) mList = images.split(" ");
        else mList = new String[]{"amir_square1"};

        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.article_pager_item, container, false);
        ImageView mImage = (ImageView)view.findViewById(R.id.image);
        String imageAddress = "file:///android_asset/images_for_article/" + mList[position] + ".jpg";
        Glide.with(context).load(imageAddress).fitCenter().into(mImage);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.changeFragment(new OverviewFragment(mList, position));
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
