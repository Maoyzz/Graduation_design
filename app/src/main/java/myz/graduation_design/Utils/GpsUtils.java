package myz.graduation_design.Utils;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import myz.graduation_design.app.app;


/**
 * Created by yiting.lu on 2017/12/23.
 */

public class GpsUtils {
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.e("GpsUtils", "gps: " + gps  + " network:" + network);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    //    public static final void openLocationService1(){
//        Service1.trigger(app.getInstance());
//    }

    /**
     * 检查定位
     * @param context
     */
    public static final void checkLocation(Context context){
        //check for location permission
        if(context instanceof Activity){
            if (PermissionsUtil.hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                PermissionsUtil.requestPermission((Activity)context, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {

                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                    }
                }, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
            }
        }

    }

}
