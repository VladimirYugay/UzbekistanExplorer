package com.uzbekistanexplorer.vladimir.uzbekistanexplorer;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.yandex.metrica.YandexMetrica;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        YandexMetrica.activate(getApplicationContext(), "9033ae54-ed0f-4da9-8d70-a23e44641256");
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetrica.setReportCrashesEnabled(true);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
