package com.example.vladimir.uzbekistanexplorer.FragmentPhrasebook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.R;
import com.example.vladimir.uzbekistanexplorer.entity.Phrases;

import java.util.ArrayList;
import java.util.List;

class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.BinderHolder>{

    List<Phrases> arrayList = new ArrayList<>();
    int type;

    public PhraseAdapter(String[] strings) {
        if(strings.length == 3) type = 1;
        else type = 0;
    }

    public void addAll(ArrayList<Phrases> list){
        arrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
     public BinderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == 1) return new ThreeHolder(inflater.inflate(R.layout.phrases_double, parent, false));
        else return new TwoHolder(inflater.inflate(R.layout.phrases_double, parent, false));
     }

     @Override
     public void onBindViewHolder(BinderHolder holder, int position) {
         holder.onBind(arrayList.get(position));
     }

     @Override
     public int getItemCount() {
         return arrayList.size();
     }

     public abstract  class BinderHolder extends RecyclerView.ViewHolder{
         public BinderHolder(View itemView) {
             super(itemView);
         }
         public abstract void onBind(Phrases phrases);
     }

    public class TwoHolder extends BinderHolder{
        TextView mUzbek, mRussian, mRussianTrans;
        public TwoHolder(View itemView) {
            super(itemView);
            mUzbek = (TextView)itemView.findViewById(R.id.uzbek);
            mRussian = (TextView)itemView.findViewById(R.id.russian);
            mRussianTrans = (TextView)itemView.findViewById(R.id.russian_trans);
        }

        @Override
        public void onBind(Phrases phrases) {

        }
    }

    public class ThreeHolder extends BinderHolder{
        TextView mForeign, mUzbek, mRussian, mRussianTrans;
        public ThreeHolder(View itemView) {
            super(itemView);
            mForeign = (TextView)itemView.findViewById(R.id.foreign);
            mUzbek = (TextView)itemView.findViewById(R.id.uzbek);
            mRussian = (TextView)itemView.findViewById(R.id.russian);
            mRussianTrans = (TextView)itemView.findViewById(R.id.russian_trans);
        }

        @Override
        public void onBind(Phrases phrases) {

        }
    }


 }
