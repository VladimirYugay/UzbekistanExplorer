package com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook;

import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.FragmentContent.RecyclerAdapter;
import com.example.vladimir.uzbekistanexplorer.R;


public class PhaseRecyclerAdapter extends RecyclerView.Adapter<PhaseRecyclerAdapter.ViewHolder>{

    String language;
    String[] places;

    public PhaseRecyclerAdapter(String language, String[] places){
        this.language = language;
        this.places = places;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mText.setText(places[position]);
        holder.mImage.setColorFilter(R.color.colorPrimary);
    }

    @Override
    public int getItemCount() {
        return places.length;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        ImageView mImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView)itemView.findViewById(R.id.text);
            mImage = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}
