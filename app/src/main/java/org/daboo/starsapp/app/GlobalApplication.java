package org.daboo.starsapp.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GlobalApplication extends Application {
    /**
     * Log or request TAG
     */
    public static final String TAG = GlobalApplication.class.getSimpleName();
    private static GlobalApplication mInstance;
    private static Context mAppContext;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.mAppContext = getApplicationContext();
        // initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    public static synchronized GlobalApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public boolean isProductionEnvironment() {
        return (AppConfig.ENVIRONMENT == CoreAppEnums.Environment.PRODUCTION) ? true : false;
    }
}
