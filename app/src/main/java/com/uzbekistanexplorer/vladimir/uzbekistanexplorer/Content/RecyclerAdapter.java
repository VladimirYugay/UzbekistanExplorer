package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Content;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Article.ArticleFragment;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Constants;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.MainActivity;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.entity.ContentItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<ContentItem> mList = new ArrayList<>();
    String mLanguage;

    public void addAll(ArrayList<ContentItem> arrayList) {
        mList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SharedPreferences mPreferences = parent.getContext().getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
        mLanguage = mPreferences.getString(Constants.LANGUAGE, null);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mPlace.setText(mList.get(position).getAddress());
        holder.mDescription.setText(mList.get(position).getDescription());
        final String imageAddress = "file:///android_asset/images_for_content/" + mList.get(position).getImage() + ".jpg";
        Picasso.with(holder.mImage.getContext()).load(imageAddress).into(holder.mImage);

        if (mLanguage.equals("rus")){
            holder.mShare.setText(holder.mShare.getContext().getResources().getString(R.string.rus_share));
            holder.mExplore.setText(holder.mShare.getContext().getResources().getString(R.string.rus_explore));
        }else{
            holder.mShare.setText(holder.mShare.getContext().getResources().getString(R.string.eng_share));;
            holder.mExplore.setText(holder.mShare.getContext().getResources().getString(R.string.eng_explore));
        }


        holder.mPlace.setText("Город");


        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg/text");
                share.putExtra(Intent.EXTRA_STREAM, imageAddress);
                share.putExtra(Intent.EXTRA_STREAM, mList.get(position).getName());
                v.getContext().startActivity(share);
            }
        });

        holder.mExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                Bundle bundle = new Bundle();
                bundle.putString("IMAGES", mList.get(position).getImages());
                bundle.putString("DESCRIPTION", mList.get(position).getDescription());
                bundle.putString("NAME", mList.get(position).getName());
                bundle.putDouble(Constants.LATITUDE, mList.get(position).getLat());
                bundle.putDouble(Constants.LONGITUDE, mList.get(position).getLon());
                ArticleFragment fragment = new ArticleFragment();
                fragment.setArguments(bundle);
                activity.changeFragment(fragment);
            }
        });
    }

    public void swap(int firstPosition, int secondPosition){
        Collections.swap(mList, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mPlace, mDescription;
        public Button mExplore, mShare;
        public ImageView mImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            mDescription = (TextView)itemView.findViewById(R.id.description);
            mName = (TextView) itemView.findViewById(R.id.name);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mPlace = (TextView) itemView.findViewById(R.id.place);
            mExplore = (Button) itemView.findViewById(R.id.explore);
            mShare = (Button)itemView.findViewById(R.id.share);
        }
    }
}
