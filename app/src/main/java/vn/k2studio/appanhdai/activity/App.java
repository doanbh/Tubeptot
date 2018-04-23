package vn.k2studio.appanhdai.activity;

import android.app.Application;
import com.google.gson.Gson;

/**
 * Created by Admin
 */

public class App extends Application {
    private static App mSelf;
    private Gson mGSon;

    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGSon = new Gson();
    }

    public Gson getGSon() {
        return mGSon;
    }
}