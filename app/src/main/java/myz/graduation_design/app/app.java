package myz.graduation_design.app;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by 10246 on 2018/4/12.
 */

public class app extends Application {

    public static app INSTANCE;

    public static app getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
