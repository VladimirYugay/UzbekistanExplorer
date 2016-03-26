package com.example.vladimir.uzbekistanexplorer.Intro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.vladimir.uzbekistanexplorer.Constants;
import com.example.vladimir.uzbekistanexplorer.R;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {

    SharedPreferences mPreferences;

    @Override
    public void init(Bundle savedInstanceState) {
        mPreferences = this.getSharedPreferences(Constants.APP_SETTINGS, MODE_PRIVATE);

        addSlide(IntroSlide.newInstance(R.layout.intro));
        addSlide(IntroSlide.newInstance(R.layout.intro2));
        addSlide(IntroSlide.newInstance(R.layout.intro3));
        addSlide(IntroSlide.newInstance(R.layout.intro4));
    }

    @Override
    public void onSkipPressed() {
        mPreferences.edit().putString(Constants.IS_FIRST, "no").apply();
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        mPreferences.edit().putString(Constants.IS_FIRST, "no").apply();
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
