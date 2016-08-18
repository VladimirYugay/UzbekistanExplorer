package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Hints;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.entity.Hint;

import java.util.ArrayList;


public class HintsAdapter extends RecyclerView.Adapter<HintsAdapter.ViewHolder> {

    ArrayList<Hint> mList = new ArrayList<>();

    public void addAll(ArrayList<Hint> arrayList){
        mList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_tips, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mTip.setText(mList.get(position).getTip());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mTip;
        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.name);
            mTip = (TextView)itemView.findViewById(R.id.tip);
        }
    }
}
