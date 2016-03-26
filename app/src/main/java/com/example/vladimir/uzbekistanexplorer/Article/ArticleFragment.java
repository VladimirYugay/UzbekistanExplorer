package com.example.vladimir.uzbekistanexplorer.Article;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vladimir.uzbekistanexplorer.MainActivity;
import com.example.vladimir.uzbekistanexplorer.R;
import com.squareup.picasso.Picasso;


public class ArticleFragment extends Fragment {

    String mName, mImages, mDescription;
    String[] mList;

    public ArticleFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mName = getArguments().getString("NAME");
        mDescription = getArguments().getString("DESCRIPTION");
        mImages = getArguments().getString("IMAGES");

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        toolbar.setTitle(mName);
        toolbar.inflateMenu(R.menu.item_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.location){
                    String uri = "geo:0,0?q=" + mName;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    getActivity().startActivity(intent);
                }
                return false;
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.transparent));
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.backdrop);
        if(mImages != null) mList = mImages.split(" ");
        else mList = new String[]{"amir_square1", "amir_square1"};
        String imageAddress = "file:///android_asset/images_for_article/" + mList[1] + ".jpg";
        Picasso.with(getContext()).load(imageAddress).into(imageView);

        TextView mText = (TextView)view.findViewById(R.id.text);
        mText.setText(mDescription);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                GalleryFragment fragment = new GalleryFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArray("IMAGES", mList);
                bundle.putString("NAME", mName);
                fragment.setArguments(bundle);
                activity.changeFragment(fragment);
            }
        });
    }
}
