package myz.graduation_design.Utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import myz.graduation_design.app.app;

/**
 * Created by Mao on 2018/4/15.
 */

public class TestUtils {
    public static String getVersion() {
        try {
            PackageManager manager = app.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(app.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
