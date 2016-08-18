package com.uzbekistanexplorer.vladimir.uzbekistanexplorer.Phrasebook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.R;
import com.uzbekistanexplorer.vladimir.uzbekistanexplorer.entity.Phrases;

import java.util.ArrayList;
import java.util.List;

class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.BinderHolder>{

    List<Phrases> arrayList = new ArrayList<>();
    boolean isNative;

    public PhraseAdapter(boolean isNative) {
        this.isNative = isNative;
    }

    public void addAll(ArrayList<Phrases> list){
        arrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
     public BinderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(!isNative) return new ForeignHolder(inflater.inflate(R.layout.phrases_foreign, parent, false));
        else return new NativeHolder(inflater.inflate(R.layout.phrases_native, parent, false));
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

    public class NativeHolder extends BinderHolder{
        TextView mUzbek, mRussian, mRussianTrans;
        public NativeHolder(View itemView) {
            super(itemView);
            mUzbek = (TextView)itemView.findViewById(R.id.uzbek);
            mRussian = (TextView)itemView.findViewById(R.id.russian);
            mRussianTrans = (TextView)itemView.findViewById(R.id.russian_trans);
        }

        @Override
        public void onBind(Phrases phrases) {
            mRussian.setText(phrases.getRussian());
            mRussianTrans.setText(phrases.getRussian_transcription());
            mUzbek.setText(phrases.getUzbek());
        }
    }

    public class ForeignHolder extends BinderHolder{
        TextView mForeign, mUzbek, mRussian, mRussianTrans;
        public ForeignHolder(View itemView) {
            super(itemView);
            mForeign = (TextView)itemView.findViewById(R.id.foreign);
            mUzbek = (TextView)itemView.findViewById(R.id.uzbek);
            mRussian = (TextView)itemView.findViewById(R.id.russian);
            mRussianTrans = (TextView)itemView.findViewById(R.id.russian_trans);
        }

        @Override
        public void onBind(Phrases phrases) {
            mForeign.setText(phrases.getForeign());
            mRussian.setText(phrases.getRussian());
            mRussianTrans.setText(phrases.getRussian_transcription());
            mUzbek.setText(phrases.getUzbek());
        }
    }


 }
