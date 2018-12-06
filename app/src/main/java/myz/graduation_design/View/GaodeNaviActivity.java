package myz.graduation_design.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import org.simpleframework.xml.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myz.graduation_design.Model.LocationModel;
import myz.graduation_design.R;

public class GaodeNaviActivity extends BaseActivity implements AMapNaviViewListener,AMapNaviListener{
    AMapNaviView mAMapNaviView;
    AMapNavi mAMapNavi;
    private TextView mTvHeaderLeft;
    private TextView mTvHeaderCenter;
    private TextView mTvHeaderRight;
    LocationModel model;

    @Override
    protected int getContentLayout() {
        model = (LocationModel)getIntent().getSerializableExtra("LocationModel");
        Log.e(TAG, "getContentLayout: "+model.startLongitude+","+model.startLatitude+model.onwayLatitude);
        return R.layout.activity_gaodenavi;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //获取 AMapNaviView 实例
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mTvHeaderLeft = (TextView)findViewById(R.id.tv_header_left);
        mTvHeaderCenter = (TextView)findViewById(R.id.tv_header_center);
        mTvHeaderRight = (TextView)findViewById(R.id.tv_header_right);
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("实时导航");
        mTvHeaderRight.setVisibility(View.GONE);
        setBack(R.id.tv_header_left);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        Log.e(TAG, "onResume: " );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
        Log.e(TAG, "onPause: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        mAMapNavi.destroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        Log.e(TAG, "onInitNaviSuccess: " );
        List<NaviLatLng> sList = new ArrayList<>();
        List<NaviLatLng> eList = new ArrayList<>();
        List<NaviLatLng> oList = new ArrayList<>();
        NaviLatLng model1 = new NaviLatLng();
        NaviLatLng model2 = new NaviLatLng();
        NaviLatLng model3 = new NaviLatLng();
        model1.setLatitude(model.startLatitude);
        model1.setLongitude(model.startLongitude);
        model2.setLatitude(model.endLatitude);
        model2.setLongitude(model.endLongitude);
        if(model.onwayLatitude != null && model.onwayLongitude !=null){
            model3.setLatitude(model.onwayLatitude);
            model3.setLongitude(model.onwayLongitude);
            oList.add(model3);
        }else {
            oList = eList;
        }

        sList.add(model1);
        eList.add(model2);
        int strategy=0;
        try {
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, oList, strategy);
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        mAMapNavi.startNavi(NaviType.GPS);
        //清空地图上上次计算的路径列表。
        Log.e("mao", "onCalculateRouteSuccess: "+ints[0]+"" );
    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }
}
