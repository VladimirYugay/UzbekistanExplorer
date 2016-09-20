package com.uzbekistanexplorer.vladimir.uzbekistanexplorer;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import java.io.FileNotFoundException;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.IOException;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;

public class AssetsProvider extends ContentProvider {

    @Override
    public AssetFileDescriptor openAssetFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        Context context = getContext();
        if (context != null) {
            String path = uri.getPath();
            if (path != null) {
                try {
                    return context.getAssets().openFd(path.substring(1));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileNotFoundException(path);
                }
            }
        }
        return null;
    }

    @Override
    public String getType(@NonNull Uri p1) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri p1, String p2, String[] p3) {
        return 0;
    }

    @Override
    public Cursor query(@NonNull Uri p1, String[] p2, String p3, String[] p4, String p5) {
        return null;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Override
    public Uri insert(@NonNull Uri p1, ContentValues p2) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public int update(@NonNull Uri p1, ContentValues p2, String p3, String[] p4) {
        return 0;
    }
}
