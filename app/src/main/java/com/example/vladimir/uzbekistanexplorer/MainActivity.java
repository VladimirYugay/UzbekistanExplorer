package com.example.vladimir.uzbekistanexplorer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.vladimir.uzbekistanexplorer.Main.MainFragment;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver mReceiver;
    String mLanguage;
    MaterialDialog mDialog;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] language_codes = getResources().getStringArray(R.array.languages_codes);
        final String[] languages = getResources().getStringArray(R.array.languages);

        mPreferences = getSharedPreferences(Constants.APP_SETTINGS, MODE_PRIVATE);
        mLanguage  = mPreferences.getString(Constants.LANGUAGE, null);

        if(mLanguage == null && savedInstanceState == null){
            mDialog = new MaterialDialog.Builder(this)
                    .title("Choose the language")
                    .items(languages)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                            mPreferences.edit().putString(Constants.LANGUAGE, language_codes[i]).apply();
                            MainFragment fragment = new MainFragment();
                            changeFragment(fragment);
                        }
                    })
                    .build();
            mDialog.setCancelable(false);
            mDialog.show();
        }else if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            MainFragment fragment = new MainFragment();
            changeFragment(fragment);
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter(Constants.UPDATE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mLanguage = intent.getStringExtra(Constants.LANGUAGE);
                mPreferences.edit().putString(Constants.LANGUAGE, mLanguage).apply();
            }
        };
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mReceiver, intentFilter);
        super.onResume();
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStackImmediate();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) super.onBackPressed();
    }
}
