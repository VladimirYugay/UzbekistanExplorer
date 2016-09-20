package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Content;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
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


        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://play.google.com/store/apps/details?id=com.uzbekistanexplorer.vladimir.uzbekistanexplorer&hl=eng";
                String message = mList.get(position).getName();
                String globalPath = "content://com.uzbekistanexplorer.vladimir.uzbekistanexplorer/images_for_content/";
                Uri uri = Uri.parse(globalPath + mList.get(position).getImage() + ".jpg");
                Log.v("MISTAKE", String.valueOf(uri));
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("*/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_SUBJECT, message);
                share.putExtra(Intent.EXTRA_TEXT, link);
                v.getContext().startActivity(Intent.createChooser(share, "Share This Image"));
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

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mDescription;
        public Button mExplore, mShare;
        public ImageView mImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            mDescription = (TextView)itemView.findViewById(R.id.description);
            mName = (TextView) itemView.findViewById(R.id.name);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mExplore = (Button) itemView.findViewById(R.id.explore);
            mShare = (Button)itemView.findViewById(R.id.share);
        }
    }
}
