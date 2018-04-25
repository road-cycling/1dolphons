package com.example.btcpro.dolphons;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tyler Rice on 4/9/2018.
 */

public class FireLearn extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if(!FirebaseApp.getApps(this).isEmpty())
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
