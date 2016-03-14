package com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;


public class PhrasebookAdapter extends RecyclerView.Adapter<PhrasebookAdapter.ViewHolder>{

    String language;
    String[] places, places_codes;

    public PhrasebookAdapter(String language, String[] places){
        this.language = language;
        this.places = places;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mText.setText(places[position]);
        holder.mImage.setColorFilter(R.color.colorPrimary);
        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhrasesFragment fragment = new PhrasesFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.PLACE, places_codes[position]);
                bundle.putString(Constants.TITLE, places[position]);
                fragment.setArguments(bundle);
                MainActivity activity = (MainActivity) v.getContext();
                activity.changeFragment(fragment);
            }
        });
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
            places_codes = itemView.getContext().getResources().getStringArray(R.array.codes_places);
        }
    }
}
