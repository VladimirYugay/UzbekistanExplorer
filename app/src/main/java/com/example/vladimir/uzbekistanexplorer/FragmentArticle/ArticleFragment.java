package com.example.vladimir.uzbekistanexplorer.FragmentArticle;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

    String name, images, description;
    String[] mList;

    public ArticleFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        name = getArguments().getString("NAME");
        description = getArguments().getString("DESCRIPTION");
        images = getArguments().getString("IMAGES");
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        toolbar.setTitle(name);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.transparent));
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.backdrop);
        if(images != null) mList = images.split(" ");
        else mList = new String[]{"amir_square1"};
        String imageAddress = "file:///android_asset/images_for_article/" + mList[1] + ".jpg";
        Picasso.with(getContext()).load(imageAddress).into(imageView);

        TextView mText = (TextView)view.findViewById(R.id.text);
        mText.setText(description);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                GalleryFragment fragment = new GalleryFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArray("IMAGES", mList);
                bundle.putString("NAME", name);
                fragment.setArguments(bundle);
                activity.changeFragment(fragment);
            }
        });

    }
}
