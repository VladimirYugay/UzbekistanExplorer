package com.example.vladimir.uzbekistanexplorer.FragmentContent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.ContentItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<ContentItem> mList = new ArrayList<>();

    public void addAll(ArrayList<ContentItem> arrayList){
        mList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mAddress.setText(mList.get(position).getAddress());
        String imageAddress = "file:///android_asset/images/" + mList.get(position).getImage() + ".jpg";
        Picasso.with(holder.mImage.getContext()).load(imageAddress).into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mAddress;
        public ImageView mImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.text);
            mImage = (ImageView)itemView.findViewById(R.id.image_anchor);
            mAddress = (TextView)itemView.findViewById(R.id.address);
        }
    }
}
