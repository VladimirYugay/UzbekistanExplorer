package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Content;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<ContentItem> mList = new ArrayList<>();

    public void addAll(ArrayList<ContentItem> arrayList) {
        mList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mAddress.setText(mList.get(position).getAddress());
        String imageAddress = "file:///android_asset/images_for_content/" + mList.get(position).getImage() + ".jpg";
        Picasso.with(holder.mImage.getContext()).load(imageAddress).into(holder.mImage);

        holder.mImage.setOnClickListener(new View.OnClickListener() {
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

        holder.mName.setOnClickListener(new View.OnClickListener() {
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

        holder.mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri;
                if (mList.get(position).getLat() != 0) {
                    uri = "geo:0, 0?q=" + mList.get(position).getLat() + " " + mList.get(position).getLon();
                } else {
                    uri = "geo:0,0?q=" + mList.get(position).getName();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), R.string.missing_google_maps, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mAddress;
        public ImageView mImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.text);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mAddress = (TextView) itemView.findViewById(R.id.address);
        }
    }
}
