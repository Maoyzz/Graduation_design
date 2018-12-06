package myz.graduation_design.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.autonavi.amap.mapcore.interfaces.IMarker;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Model.LocationModel;
import myz.graduation_design.Model.OnwayModel;
import myz.graduation_design.Presender.TabFragmentListener;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.GpsUtils;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.Utils.SystemUtils;
import myz.graduation_design.adapter.OnwayAdapter;
import myz.graduation_design.weight.CheckDialog;
import myz.graduation_design.weight.PhoneDialog;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 10246 on 2018/4/3.
 */

public class OnWayFragment extends Fragment implements BaseQuickAdapter.OnItemChildClickListener,View.OnClickListener,AMapLocationListener,AMap.CancelableCallback{

    private TabFragmentListener fragmentListener;
    private TextView mTvHeaderLeft;
    private TextView mTvHeaderCenter;
    private TextView mTvHeaderRight;

    private MapView mMapView;
    private AMap aMap;
    private ImageView mImgArrow;
    private RecyclerView mRvOnway;


    private OnwayAdapter adapter;
    private Marker marker1;
    private Marker marker2;
    private Marker marker3;
    private PhoneDialog mDialog;
    private CheckDialog mDialogCheck;
    private String Phone;
    //getLocation
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLng mLatLngNow;
    private Boolean isCheckedLocate = false;
    private OnwayModel mOnwayModel;
    private Double latitudeS;
    private Double longitudeS;
    private Double latitudeE;
    private Double longitudeE;
    private int role;
    private int naviState = 0;

    private TextView mTvPopText1;
    private TextView mTvPopText2;
    private TextView mTvPopText3;


    public void setTabFragmentListener(TabFragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public static OnWayFragment newInstance(TabFragmentListener listener) {
        OnWayFragment fragment = new OnWayFragment();
        Bundle args = new Bundle();
        fragment.setTabFragmentListener(listener);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mMapView = (MapView)view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);


        initView(view);
        initData();
        return view;
    }

    private void initData() {
        GpsUtils.checkLocation(getContext());
        role = Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERROLE_KEY,""));

        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(10000);
        mLocationClient.setLocationOption(mLocationOption);
//        mLocationOption.setOnceLocation(true);
//        mLocationOption.setOnceLocationLatest(true);

        adapter = new OnwayAdapter(new ArrayList<OnwayModel>(),((MainActivity)getActivity()).iconfont);
        mRvOnway.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOnway.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        //TODO fake model
        List<OnwayModel> list = new ArrayList<OnwayModel>();
        OnwayModel model1 = new OnwayModel();
        OnwayModel model2 = new OnwayModel();
        model1.driverName = "毛司机";
        model1.drvierPhone = "10086";
        model1.endAddress = "宁波理工学院";
        model1.endDate = "2018年4月21日18时";
        model1.goodsName = "沙石20吨";
        model1.startAddress = "宁波天一广场";
        model1.isChecked = true;
        model1.startLatLng = new LatLng(29.814989,121.574466);
        model1.endLatLng = new LatLng(29.869457,121.554194);
        model1.onwayLating = new LatLng(29.867867,121.596812);
        model2.driverName = "陈司机";
        model2.drvierPhone = "10086";
        model2.endAddress = "宁波理工学院";
        model2.endDate = "2018年4月21日18时";
        model2.goodsName = "沙石20吨";
        model2.startAddress = "宁波天一广场";
        model2.isChecked = false;
        model2.startLatLng = new LatLng(29.814989,121.574466);
        model2.endLatLng = new LatLng(29.869457,121.554194);
        model2.onwayLating = new LatLng(29.867867,122.596812);
        list.add(model1);
        list.add(model2);
        adapter.setNewData(list);
        mOnwayModel = adapter.getData().get(0);

//dialog
        switch (role){
            case 0:
                getGoodsList();
                break;
            case 1:
                getGoodsListByCarrierid();
                break;
            case 2:
                getGoodsListByDriverid();
                break;
        }

    }

    private void initView(View view){
        mTvHeaderLeft = (TextView)view.findViewById(R.id.tv_header_left);
        mTvHeaderRight = (TextView)view.findViewById(R.id.tv_header_right);
        mTvHeaderCenter = (TextView)view.findViewById(R.id.tv_header_center);
        mTvHeaderLeft.setVisibility(View.GONE);
        mTvHeaderRight.setText("导航");
        mTvHeaderRight.setTextColor(getResources().getColor(R.color.white));
        mTvHeaderRight.setOnClickListener(this);
        mTvHeaderCenter.setText("在途");

        mImgArrow = (ImageView)view.findViewById(R.id.img_arrow);
        mRvOnway = (RecyclerView)view.findViewById(R.id.rv_onway);
        mImgArrow.setOnClickListener(this);
        view.findViewById(R.id.img_dingwei).setOnClickListener(this);


        aMap = mMapView.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        LatLng latLng1 = new LatLng(29.868852,121.596449);
        LatLng latLng2 = new LatLng(29.868852,121.696449);
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
        boundsBuilder.include(latLng1).include(latLng2);
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 10));

        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));


    }


    /**
     * 获得用户订单列表
     */
    private void getGoodsList(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(getContext());
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoBySendid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                //TODO json test
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );

            }

            @Override
            public void error(String string) {

            }
        });
    }

    /**
     * 获得物流公司的订单
     */
    private void getGoodsListByCarrierid(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(getContext());
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoByCarrierid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );
                for(int i=0 ;i < GoodsListResult.size() ;i++){

                }
            }


            @Override
            public void error(String string) {

            }
        });
    }

    /**
     * 获得司机的订单
     */
    private void getGoodsListByDriverid(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(getContext());
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoByDriverid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );

            }

            @Override
            public void error(String string) {

            }
        });
    }

    /**
     * 标注点
     * @param model
     */
    private void setMarker(OnwayModel model){

        aMap.clear();
        Log.e("mao", "marker1: " );
        marker1 = aMap.addMarker(new MarkerOptions().position(model.startLatLng).title("起点").snippet(model.startAddress).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_onway_location)));
        marker2 = aMap.addMarker(new MarkerOptions().position(model.onwayLating).title("司机").snippet(model.driverName).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_onway_location_on)));
        marker3 = aMap.addMarker(new MarkerOptions().position(model.endLatLng).title("终点").snippet(model.endAddress).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_onway_location_end)));

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        OnwayModel model = (OnwayModel)adapter.getData().get(position);
        mOnwayModel = model;
//        getLocation("http://restapi.amap.com/v3/geocode/geo?address="+model.startAddress+"&key=f85fb3519bb821437196610c16617d5a",0);
//        getLocation("http://restapi.amap.com/v3/geocode/geo?address="+model.endAddress+"&key=f85fb3519bb821437196610c16617d5a",1);
        Log.e("mao", "img_location: " );
        switch (view.getId()){
            case R.id.img_location:
                setMarker(model);
                break;
            case R.id.tv_phone:
                createDialog(model.drvierPhone);
                break;
            default:
                break;

        }
    }

    /**
     * 前往导航
     * @param type
     */
    private void gotoNavi(final int type){
        Intent intent = new Intent(getActivity(),GaodeNaviActivity.class);
        LocationModel model = new LocationModel();
        switch (type){
            case 0:
                model.startLatitude = mLatLngNow.latitude;
                model.startLongitude = mLatLngNow.longitude;
                model.endLatitude = mOnwayModel.startLatLng.latitude;
                model.endLongitude = mOnwayModel.startLatLng.longitude;
                break;
            case 1:
                model.startLatitude = mLatLngNow.latitude;
                model.startLongitude = mLatLngNow.longitude;
                model.endLatitude = mOnwayModel.endLatLng.latitude;
                model.endLongitude = mOnwayModel.endLatLng.longitude;
                break;
            case 2:
                model.startLatitude = mOnwayModel.startLatLng.latitude;
                model.startLongitude = mOnwayModel.startLatLng.longitude;
                model.endLatitude = mOnwayModel.endLatLng.latitude;
                model.endLongitude = mOnwayModel.endLatLng.longitude;
                break;
        }
        intent.putExtra("LocationModel",model);
        startActivity(intent);
    }

//    private LatLng getLocation(final String url, final int type){
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//                Call call = okHttpClient.newCall(request);
//                try {
//                    Response response = call.execute();
//                    Gson gson = new Gson();
//                    myz.graduation_design.Model.AmapModel.LocationModel params = gson.fromJson(response.body().string(), myz.graduation_design.Model.AmapModel.LocationModel.class);
//                    Log.e("mao", "OkHttpClient: "+ params.geocodes.get(0).location);
//
//                    String lngX = params.geocodes.get(0).location.split(",")[0];
//                    String latY = params.geocodes.get(0).location.split(",")[1];
//                    if(type == 0){
//                        latitudeS = Double.parseDouble(latY);
//                        longitudeS = Double.parseDouble(lngX);
//                    }else {
//                        latitudeE = Double.parseDouble(latY);
//                        longitudeE = Double.parseDouble(lngX);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();
//    }

    private void createDialogCheck(final int type){
        if(mDialogCheck == null){
            mDialogCheck = new CheckDialog(getContext());
        }
        if(type == 0){
            mDialogCheck.setDialogTitle("前往下载百度地图？");
        }else if(type == 1){
            mDialogCheck.setDialogTitle("前往下载高德地图？");
        }
        mDialogCheck.show();
        mDialogCheck.setDialogListener(new CheckDialog.DialogListener() {
            @Override
            public void clickOk() {
                if(type == 0){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://map.baidu.com/zt/client/index/")));
                }else if(type == 1){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://wap.amap.com/?from=m&type=m")));
                }
            }

            @Override
            public void clickCancel() {
                mDialogCheck.dismiss();
            }
        });
    }

    /**
     * gaode map
     * @param type
     * @param address
     */
    private void intentMap(int type,String address){
        Boolean hasBaidu = SystemUtils.isAvilible(getContext(), "com.baidu.BaiduMap");
        Boolean hasGaode = SystemUtils.isAvilible(getContext(), "com.autonavi.minimap");
        if(type == 0){
            if(hasBaidu){
                Intent intent = new Intent();
                intent.setData(Uri.parse("baidumap://map/direction?destination=" + address + "&mode=driving"));
                startActivity(intent);
                naviState = 0;
                setPopText(0);
            }else {
                createDialogCheck(0);
                //Toast.makeText(getContext(),"不好意思，您没有安装百度地图",Toast.LENGTH_SHORT).show();
            }
        }else {
            if(hasGaode){
                Intent intent = new Intent();
                intent.setData(Uri.parse("androidamap://route?sourceApplication=amap" + "&dname=" + address + "&t=0"));
                startActivity(intent);
                naviState = 0;
                setPopText(0);
            }else {
                createDialogCheck(1);
                //Toast.makeText(getContext(),"不好意思，您没有安装高德地图",Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * set pop text
     * @param type
     */
    private void setPopText(int type){
        switch (type){
            case 0:
                mTvPopText1.setText("内置导航");
                mTvPopText2.setText("我有高德");
                mTvPopText3.setText("我有百度");
                break;
            case 1:
                mTvPopText1.setText("当前到起点");
                mTvPopText2.setText("当前到终点");
                mTvPopText3.setText("返回");
                break;
            case 2:
                mTvPopText1.setText("当前到起点");
                mTvPopText2.setText("当前到终点");
                mTvPopText3.setText("起点到终点");
                break;
        }
    }

    /**
     * 创建导航选项
     */
    private void createPopupWindow(){
        naviState = 0;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.popup_navi, null);
        final PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //外部是否可以点击
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setOutsideTouchable(false);
        popup.setFocusable(true);
        popup.showAsDropDown(mTvHeaderRight, 20, -20);
        popup.setOutsideTouchable(true);
        LinearLayout mLayoutNavi1 = (LinearLayout)view.findViewById(R.id.layout_navi1);
        LinearLayout mLayoutNavi2 = (LinearLayout)view.findViewById(R.id.layout_navi2);
        LinearLayout mLayoutNavi3 = (LinearLayout)view.findViewById(R.id.layout_navi3);
        mTvPopText1 = (TextView) view.findViewById(R.id.tv_navi_start);
        mTvPopText2 = (TextView) view.findViewById(R.id.tv_navi_end);
        mTvPopText3 = (TextView) view.findViewById(R.id.tv_navi_starttoend);
        mLayoutNavi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (naviState){
                    case 0:
                        setPopText(2);
                        naviState = 1;
                        break;
                    case 1:
                        gotoNavi(0);
                        popup.dismiss();
                        mLocationClient.stopLocation();
                        break;
                    case 2:
                        intentMap(1,mOnwayModel.startAddress);
                        break;
                    case 3:
                        intentMap(0,mOnwayModel.startAddress);
                        break;

                }

            }
        });
        mLayoutNavi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (naviState){
                    case 0:
                        setPopText(1);
                        naviState = 2;
                        break;
                    case 1:
                        gotoNavi(1);
                        popup.dismiss();
                        mLocationClient.stopLocation();
                        break;
                    case 2:
                        intentMap(1,mOnwayModel.endAddress);
                        break;
                    case 3:
                        intentMap(0,mOnwayModel.endAddress);
                        break;

                }
            }
        });
        mLayoutNavi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (naviState){
                    case 0:
                        setPopText(1);
                        naviState = 3;
                        break;
                    case 1:
                        gotoNavi(2);
                        popup.dismiss();
                        mLocationClient.stopLocation();
                        break;
                    case 2:
                    case 3:
                        setPopText(0);
                        naviState = 0;
                        break;

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_arrow:
                if(mRvOnway.getVisibility() == View.VISIBLE){
                    mImgArrow.setImageResource(R.mipmap.icon_arrow_top);
                    mRvOnway.setVisibility(View.GONE);
                }else {
                    mImgArrow.setImageResource(R.mipmap.icon_arrow_bottom);
                    mRvOnway.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tv_header_right:
                mLocationClient.startLocation();
                createPopupWindow();
                break;
            case R.id.img_dingwei:
                if(!isCheckedLocate){
                    mLocationClient.startLocation();
                    isCheckedLocate = true;
                }else {
                    mLocationClient.stopLocation();
                    isCheckedLocate = false;
                }
                break;
        }
    }

    /**
     * 创建call dialog
     * @param phone
     */
    private void createDialog(final String phone){
        Phone = phone;
        mDialog = new PhoneDialog(getContext(), Phone, new PhoneDialog.callPhone() {
            @Override
            public void call(final String Phone) {
                if (PermissionsUtil.hasPermission(getContext(), Manifest.permission.CALL_PHONE)) {
                    Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ Phone));
                    startActivity(intent);
                    mDialog.dismiss();
                } else {
                    PermissionsUtil.requestPermission((MainActivity)getActivity(), new PermissionListener() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Phone));
                            startActivity(intent);
                            mDialog.dismiss();
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            Toast.makeText(getContext(), "请同意电话权限", Toast.LENGTH_SHORT).show();
                        }
                    }, new String[]{Manifest.permission.CALL_PHONE});
                }
            }

            @Override
            public void cancel() {
                mDialog.dismiss();
            }
        });
    }

    /**
     * 高德定位的回掉
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLatLngNow = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
        Log.e("mao", "onLocationChanged: "+aMapLocation.getLongitude() );
        Marker marker4 = aMap.addMarker(new MarkerOptions().position(mLatLngNow).title("当前位置").icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_dingwei)));



    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
