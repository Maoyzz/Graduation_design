package myz.graduation_design.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.Model.AmapModel.LocationModel;
import myz.graduation_design.Model.CompanyModel;
import myz.graduation_design.Model.DriverModel;
import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Model.GoodsRequest;
import myz.graduation_design.Model.updateTransModel;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.weight.CheckDialog;
import myz.graduation_design.weight.PhoneDialog;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 10246 on 2018/4/13.
 */

public class GoodsDetailActivity extends BaseActivity implements AMapLocationListener,AMap.CancelableCallback,View.OnClickListener{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.tv_start_address)
    TextView mTvStartAddress;
    @BindView(R.id.tv_end_address)
    TextView mTvEndAddress;
    @BindView(R.id.btn_goods_detail)
    Button mBtnGoodsDetail;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;



    @BindView(R.id.tv_money_user)
    TextView mTvMoneyUser;
    @BindView(R.id.et_money_user)
    EditText mEtMoneyUser;
    @BindView(R.id.tv_money_driver)
    TextView mTvMoneyDriver;
    @BindView(R.id.et_money_driver)
    EditText mEtMoneyDriver;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;

    @BindView(R.id.tv_img_start_address)
    TextView mTvImgStartAddress;
    @BindView(R.id.tv_img_end_address)
    TextView mTvImgEndaddress;
    @BindView(R.id.tv_img_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_img_end_date)
    TextView mTvImgEndDate;
    @BindView(R.id.tv_img_lanker)
    TextView mTvImgLanker;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_img_phone)
    TextView mTvImgPhone;
    @BindView(R.id.tv_img_money_user)
    TextView mTvImgMoneyUser;
    @BindView(R.id.tv_img_money_driver)
    TextView mTvImgMoneyDriver;
    @BindView(R.id.tv_img_company)
    TextView mTvImgCompany;
    @BindView(R.id.tv_img_phone_company)
    TextView mTvImgPhoneCompany;
    @BindView(R.id.tv_img_driver)
    TextView mTvImgDriver;
    @BindView(R.id.tv_img_phone_driver)
    TextView mTvImgPhoneDriver;
    @BindView(R.id.tv_phone_driver)
    TextView mTvPhoneDriver;
    @BindView(R.id.tv_phone_company)
    TextView mTvPhoneCompany;




    @BindView(R.id.tv_driver)
    TextView mTvDriver;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_lanker)
    TextView mTvLanker;

    @BindView(R.id.img_navigation)
    ImageView mImgNavi;
    @BindView(R.id.btn_goods_refuse)
    Button mBtnGoodsRefuse;
    @BindView(R.id.btn_goods_agree)
    Button mBtnGoodsAgree;
    @BindView(R.id.layout_goods_btn)
    LinearLayout mLayoutGoodsBtn;
    @BindView(R.id.tv_goodsName)
    TextView mTvgoodsName;
    @BindView(R.id.tv_goodsDescribe)
    TextView mTvgoodsDescribe;
    @BindView(R.id.tv_goodsAttribute)
    TextView mTvgoodsAttribute;
    @BindView(R.id.tv_goodsRemark)
    TextView mTvgoodsRemark;


    private String urlStart;
    private String urlEnd;
    private String LocationStart;
    private String LocationEnd;
    DriverModel driverModel;
    CompanyModel companyModel;

    private final static int TYPE_DETAIL = 0;
    private final static int TYPE_PAIDAN = 1;
    private final static int TYPE_CARRIAGE = 2;
    private final static int TYPE_WAIT = 3;
    private final static int TYPE_REFUSE = 4;


    private final static int ROLE_USER = 0;
    private final static int ROLE_CARRIER = 1;
    private final static int ROLE_DRIVER = 2;

    private final static int ACTION_PAIDAN = 0;
    private final static int ACTION_JUJUEPAIDAN = 1;
    private final static int ACTION_JIEDAN = 2;
    private final static int ACTION_JUJUEJIEDAN = 3;
    private final static int ACTION_DAODA = 4;
    private final static int ACTION_QUEREN = 5;
    private final static int ACTION_RECREATE = 6;
    private final static int ACTION_REPAIDAN = 7;


    int type = 0;
    int state = 0;
    int role = 0;
    PhoneDialog mDialog;
    CheckDialog mCheckDialog;

    AMapLocationClient mLocationClient;
    AMapLocationClientOption mLocationOption;
    GoodsListResult GoodsResult;
    updateTransModel updateModel;


    @Override
    protected int getContentLayout() {
        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClient.setLocationOption(mLocationOption);
        //mLocationClient.startLocation();
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvHeaderLeft.setTypeface(iconfont);
        setBack(R.id.tv_header_left);
        type = getIntent().getIntExtra("type",0);
        GoodsResult = (GoodsListResult)getIntent().getSerializableExtra("result");
        state = GoodsResult.state;
        role = Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERROLE_KEY,""));
        setView();
        switch (type){
            case TYPE_DETAIL:
                mTvHeaderCenter.setText("货品详情");
                break;
            case TYPE_PAIDAN:
                mTvHeaderCenter.setText("派单");
                break;
            case TYPE_CARRIAGE:
                mTvHeaderCenter.setText("发运");
                break;
            case TYPE_WAIT:
                mTvHeaderCenter.setText("待确认");
                break;
            case TYPE_REFUSE:
                mTvHeaderCenter.setText("被拒订单");
                break;
            default:
                break;
        }
        mTvHeaderRight.setTypeface(iconfont);
        switch (type){
            case TYPE_DETAIL:
                mBtnGoodsDetail.setVisibility(View.GONE);
                mLayoutGoodsBtn.setVisibility(View.GONE);
                mTvMoneyUser.setVisibility(View.VISIBLE);
                mTvMoneyDriver.setVisibility(View.VISIBLE);
                mEtMoneyUser.setVisibility(View.GONE);
                mEtMoneyDriver.setVisibility(View.GONE);
                break;
            case TYPE_PAIDAN:
                mBtnGoodsDetail.setVisibility(View.GONE);
                mLayoutGoodsBtn.setVisibility(View.VISIBLE);
                mBtnGoodsAgree.setText("确认派单");
                mTvMoneyUser.setVisibility(View.GONE);
                mTvMoneyDriver.setVisibility(View.GONE);
                mEtMoneyUser.setVisibility(View.VISIBLE);
                mEtMoneyDriver.setVisibility(View.VISIBLE);
                mTvDriver.setText("请添加司机");
                mTvPhoneDriver.setText("");
                mTvDriver.setTextColor(Color.parseColor("#ff0000"));
                mTvDriver.setOnClickListener(this);
                break;
            case TYPE_CARRIAGE:
                mBtnGoodsDetail.setVisibility(View.GONE);
                mLayoutGoodsBtn.setVisibility(View.VISIBLE);
                mBtnGoodsAgree.setText("确认发运");
                mTvMoneyUser.setVisibility(View.VISIBLE);
                mTvMoneyDriver.setVisibility(View.VISIBLE);
                mEtMoneyUser.setVisibility(View.GONE);
                mEtMoneyDriver.setVisibility(View.GONE);
                break;
            case TYPE_REFUSE:
                if(role == ROLE_USER){
                    if(state == -1){
                        mTvCompany.setText("请添加物流公司");
                        mTvCompany.setTextColor(Color.parseColor("#ff0000"));
                        mTvCompany.setOnClickListener(this);
                        mBtnGoodsDetail.setVisibility(View.VISIBLE);
                        mLayoutGoodsBtn.setVisibility(View.GONE);
                        mTvMoneyUser.setVisibility(View.VISIBLE);
                        mTvMoneyDriver.setVisibility(View.VISIBLE);
                        mEtMoneyUser.setVisibility(View.GONE);
                        mEtMoneyDriver.setVisibility(View.GONE);
                        mBtnGoodsDetail.setText("创建订单");
                        mTvPhoneCompany.setText("");
//                        mEtMoneyUser.setVisibility(View.VISIBLE);
//                        mEtMoneyDriver.setVisibility(View.VISIBLE);
//                        mTvMoneyDriver.setVisibility(View.GONE);
//                        mTvMoneyUser.setVisibility(View.GONE);
                    }
                }else if(role == ROLE_CARRIER){
                    if(state == -2){
                        mBtnGoodsDetail.setVisibility(View.VISIBLE);
                        mLayoutGoodsBtn.setVisibility(View.GONE);
                        mBtnGoodsDetail.setText("确认派单");
                        mTvMoneyUser.setVisibility(View.GONE);
                        mTvMoneyDriver.setVisibility(View.GONE);
                        mEtMoneyUser.setVisibility(View.VISIBLE);
                        mEtMoneyDriver.setVisibility(View.VISIBLE);
                        mTvDriver.setText("请添加司机");
                        mTvPhoneDriver.setText("");
                        mTvDriver.setTextColor(Color.parseColor("#ff0000"));
                        mTvDriver.setOnClickListener(this);
                    }
                }else {

                }
                break;
            case TYPE_WAIT:
                if(role == ROLE_USER){
                    if(state == 3){
                        mBtnGoodsDetail.setVisibility(View.VISIBLE);
                        mLayoutGoodsBtn.setVisibility(View.GONE);
                        mTvMoneyUser.setVisibility(View.VISIBLE);
                        mTvMoneyDriver.setVisibility(View.VISIBLE);
                        mEtMoneyUser.setVisibility(View.GONE);
                        mEtMoneyDriver.setVisibility(View.GONE);
                        mBtnGoodsDetail.setText("确认到达");
                    }
                }else if(role == ROLE_CARRIER){
                    if(state == 0){
                        mBtnGoodsDetail.setVisibility(View.GONE);
                        mLayoutGoodsBtn.setVisibility(View.VISIBLE);
                        mBtnGoodsAgree.setText("确认派单");
                        mTvMoneyUser.setVisibility(View.GONE);
                        mTvMoneyDriver.setVisibility(View.GONE);
                        mEtMoneyUser.setVisibility(View.VISIBLE);
                        mEtMoneyDriver.setVisibility(View.VISIBLE);
                        mTvDriver.setText("请添加司机");
                        mTvPhoneDriver.setText("");
                        mTvDriver.setTextColor(Color.parseColor("#ff0000"));
                        mTvDriver.setOnClickListener(this);
                    }else if(state == -2){
                        mBtnGoodsDetail.setVisibility(View.VISIBLE);
                        mLayoutGoodsBtn.setVisibility(View.GONE);
                        mTvMoneyUser.setVisibility(View.GONE);
                        mTvMoneyDriver.setVisibility(View.GONE);
                        mEtMoneyUser.setVisibility(View.VISIBLE);
                        mEtMoneyDriver.setVisibility(View.VISIBLE);
                        mTvDriver.setText("请添加司机");
                        mTvPhoneDriver.setText("");
                        mTvDriver.setTextColor(Color.parseColor("#ff0000"));
                        mTvDriver.setOnClickListener(this);
                        mBtnGoodsDetail.setText("确认派单");
                    }
                }else {
                    if(state == 1){
                        mBtnGoodsDetail.setVisibility(View.GONE);
                        mLayoutGoodsBtn.setVisibility(View.VISIBLE);
                        mTvMoneyUser.setVisibility(View.VISIBLE);
                        mTvMoneyDriver.setVisibility(View.VISIBLE);
                        mEtMoneyUser.setVisibility(View.GONE);
                        mEtMoneyDriver.setVisibility(View.GONE);
                        mBtnGoodsAgree.setText("确认发运");
                    }else if(state == 2){
                        mBtnGoodsDetail.setVisibility(View.VISIBLE);
                        mLayoutGoodsBtn.setVisibility(View.GONE);
                        mTvMoneyUser.setVisibility(View.VISIBLE);
                        mTvMoneyDriver.setVisibility(View.VISIBLE);
                        mEtMoneyUser.setVisibility(View.GONE);
                        mEtMoneyDriver.setVisibility(View.GONE);
                        mBtnGoodsDetail.setText("确认到达");
                    }
                }
                break;
            default:
                break;

        }
        mTvImgStartAddress.setTypeface(iconfont);
        mTvImgEndaddress.setTypeface(iconfont);
        mTvStartDate.setTypeface(iconfont);
        mTvImgEndDate.setTypeface(iconfont);
        mTvImgLanker.setTypeface(iconfont);
        mTvImgPhone.setTypeface(iconfont);
        mTvImgMoneyUser.setTypeface(iconfont);
        mTvImgMoneyDriver.setTypeface(iconfont);
        mTvImgCompany.setTypeface(iconfont);
        mTvImgPhoneCompany.setTypeface(iconfont);
        mTvImgDriver.setTypeface(iconfont);
        mTvImgPhoneDriver.setTypeface(iconfont);

        mTvPhone.setOnClickListener(this);
        mTvPhoneDriver.setOnClickListener(this);
        mTvPhoneCompany.setOnClickListener(this);
    }

    /**
     *  设置不同状态下的订单的基础信息
     */
    private void setView(){
        switch (state){
            case 0:
                mTvState.setText("状态：等待派单");
                break;
            case 1:
                mTvState.setText("状态：等待接单");
                break;
            case -1:
                mTvState.setText("状态：物流公司拒单");
                break;
            case 2:
                mTvState.setText("状态：司机接单");
                break;
            case -2:
                mTvState.setText("状态：司机拒单");
                break;
            case 3:
                mTvState.setText("状态：司机到达");
                break;
            case 4:
                mTvState.setText("状态：用户确认");
                break;
            default:
        }
        if(GoodsResult.goodsList.size() > 0){
            mTvgoodsName.setText(StringUtils.getFormatString(getResources(),R.string.goodsName,GoodsResult.goodsList.get(0).goodsName));
            mTvgoodsDescribe.setText(StringUtils.getFormatString(getResources(),R.string.goodsDescribe,GoodsResult.goodsList.get(0).goodsDescribe));
            mTvgoodsAttribute.setText(StringUtils.getFormatString(getResources(),R.string.goodsAttribute,GoodsResult.goodsList.get(0).goodsAttribute));
            mTvgoodsRemark.setText(StringUtils.getFormatString(getResources(),R.string.goodsRemark,GoodsResult.goodsList.get(0).goodsRemark));
        }

        mTvStartAddress.setText(GoodsResult.startAddress);
        mTvEndAddress.setText(GoodsResult.endAddress);
        mTvStartTime.setText(GoodsResult.startTime);
        mTvEndTime.setText(GoodsResult.endTime);
        mTvLanker.setText(GoodsResult.receiverName);
        mTvPhone.setText(GoodsResult.receiverPhone);
        mTvMoneyUser.setText("¥"+GoodsResult.goodsMoney);
        mTvMoneyDriver.setText("¥"+GoodsResult.reward);
        mTvCompany.setText(GoodsResult.carrierName);
        mTvPhoneCompany.setText(GoodsResult.carrierPhone);
        mTvDriver.setText(GoodsResult.driverName);
        mTvPhoneDriver.setText(GoodsResult.driverPhone);
        mTvDriver.setText(GoodsResult.driverName);
        mTvRemark.setText(GoodsResult.remark);
        mEtMoneyUser.clearFocus();
        mEtMoneyDriver.clearFocus();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        urlStart = "http://restapi.amap.com/v3/geocode/geo?address="+mTvStartAddress.getText().toString()+"&key=f85fb3519bb821437196610c16617d5a";
        urlEnd = "http://restapi.amap.com/v3/geocode/geo?address="+mTvEndAddress.getText().toString()+"&key=f85fb3519bb821437196610c16617d5a";
        getLocation(urlStart);
        getLocation(urlEnd);
        updateModel = new updateTransModel();
    }

    @Override
    protected void initEvent() {

    }

    /**
     * call phone
     * @param Phone
     */

    private void call(String Phone){
        mDialog = new PhoneDialog(this, Phone, new PhoneDialog.callPhone() {
            @SuppressLint("MissingPermission")
            @Override
            public void call(final String Phone) {
                if (PermissionsUtil.hasPermission(GoodsDetailActivity.this, Manifest.permission.CALL_PHONE)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ Phone));
                    startActivity(intent);
                    mDialog.dismiss();
                } else {
                    PermissionsUtil.requestPermission(GoodsDetailActivity.this, new PermissionListener() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Phone));
                            startActivity(intent);
                            mDialog.dismiss();
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            Toast.makeText(GoodsDetailActivity.this, "请同意电话权限", Toast.LENGTH_SHORT).show();
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
     * get loaction from gaode web api
     * @param url
     */

    private void getLocation(final String url){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    try{
                        Gson gson = new Gson();
                        LocationModel params = gson.fromJson(response.body().string(), LocationModel.class);
                        Log.e(TAG, "location: "+ params.geocodes.get(0).location);
                        if(params.geocodes != null){
                            if(url.equals(urlStart)){
                                LocationStart = params.geocodes.get(0).location;
                            }else {
                                LocationEnd = params.geocodes.get(0).location;

                            }
                        }
                    }catch (Exception e){
                        Log.e(TAG, "LocationErr: "+e.toString() );
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /**
     * use gaode web api to navi
     */
    @OnClick(R.id.img_navigation)
    void Navi(){
        Uri uri = null;
        String s = null;
        s = "http://m.amap.com/navi/?start="+LocationStart+"&dest="+LocationEnd+"&destName="+ mTvEndAddress.getText().toString()+"&naviBy=car&key=6b8c445642db9b33e0f9952fba1baff3&platform=mobile";
        uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }

    @OnClick(R.id.tv_start_address)
    void gotomap(){
        gotoMap(mTvStartAddress);
    }

    @OnClick(R.id.tv_end_address)
    void gotomapE(){
        gotoMap(mTvEndAddress);
    }

    /**
     * goto map
     * @param view
     */
    private void gotoMap(View view){
        Uri uri = null;
        String s = null;
        if(view == mTvStartAddress){
            s = "http://m.amap.com/navi/?dest="+LocationStart+"&destName="+mTvStartAddress.getText().toString()+"&key=6b8c445642db9b33e0f9952fba1baff3";
        }else if(view == mTvEndAddress){
            s = "http://m.amap.com/navi/?dest="+LocationEnd+"&destName="+mTvEndAddress.getText().toString()+"&key=6b8c445642db9b33e0f9952fba1baff3";
        }
        uri = Uri.parse(s);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if(resultCode == RESULT_OK){
                    driverModel = (DriverModel) data.getSerializableExtra("data");
                    mTvDriver.setText(driverModel.DriverName);
                    mTvPhoneDriver.setText(driverModel.DriverPhone);
                    mTvDriver.setTextColor(Color.parseColor("#333333"));
                }
                break;
            case 101:
                if(resultCode == RESULT_OK){
                    companyModel = (CompanyModel)data.getSerializableExtra("CompanyModel");
                    mTvCompany.setText(companyModel.CompanyName);
                    mTvPhoneCompany.setText(companyModel.CompanyPhone);
                    mTvCompany.setTextColor(Color.parseColor("#333333"));
                }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e(TAG, "onLocationChanged: "+aMapLocation.getLatitude()+aMapLocation.getLongitude() );
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_phone:
                call(mTvPhone.getText().toString());
                break;
            case  R.id.tv_phone_driver:
                call(mTvPhoneDriver.getText().toString());
                break;
            case  R.id.tv_phone_company:
                call(mTvPhoneCompany.getText().toString());
                break;
            case R.id.tv_company:
                Intent intent = new Intent(GoodsDetailActivity.this,CompanyListActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,101);
                break;
            case R.id.tv_driver:
                switch (type){
                    case TYPE_PAIDAN:
                    case TYPE_WAIT:
                        Intent intentDriver = new Intent(this,DriverListActivity.class);
                        intentDriver.putExtra("type",1);
                        startActivityForResult(intentDriver,100);
                        break;
                    case TYPE_DETAIL:
                        break;
                }
                break;



            default:
                break;
        }
    }

    /**
     * 更新状态需要上传的model
     * @param updateType
     * @param state
     * @return updateTransModel
     */
    private updateTransModel changeinfo(int updateType,int state){
        Gson gson = new Gson();
        List<GoodsRequest> goodsList = new ArrayList<GoodsRequest>();
        String str = gson.toJson(goodsList);

        updateModel.sendId = GoodsResult.sendId;
        updateModel.goodsList = str;
        updateModel.remark = GoodsResult.remark;
        updateModel.carrierPhone = GoodsResult.carrierPhone;
        updateModel.receiverPhone = GoodsResult.receiverPhone;
        updateModel.carrierName = GoodsResult.carrierName;
        updateModel.startTime = GoodsResult.startTime;
        updateModel.state = state;
        updateModel.goodsName = GoodsResult.goodsName;
        updateModel.lat = GoodsResult.lat;
        updateModel.lng = GoodsResult.lng;
        updateModel.sendName = GoodsResult.sendName;
        updateModel.receiverName = GoodsResult.sendName;
        updateModel.startAddress = GoodsResult.startAddress;
        updateModel.goodsMoney = GoodsResult.goodsMoney;
        updateModel.driverId = GoodsResult.driverId;
        updateModel.sendPhone = GoodsResult.sendPhone;
        updateModel.driverName = GoodsResult.driverName;
        updateModel.driverPhone = GoodsResult.driverPhone;
        updateModel.endTime = GoodsResult.endTime;
        updateModel.moneyState = GoodsResult.moneyState;
        updateModel.carrierId = GoodsResult.carrierId;
        updateModel.endAddress = GoodsResult.endAddress;
        updateModel.id = GoodsResult.id;

        switch (updateType){
            case ACTION_PAIDAN:
                updateModel.goodsMoney = Integer.parseInt(mEtMoneyUser.getText().toString());
                updateModel.reward = Integer.parseInt(mEtMoneyDriver.getText().toString());
                updateModel.driverId = driverModel.id;
                updateModel.driverName = driverModel.DriverName;
                updateModel.driverPhone = driverModel.DriverPhone;
                break;
            case ACTION_JUJUEPAIDAN:
                break;
            case ACTION_JIEDAN:
                break;
            case ACTION_JUJUEJIEDAN:
                break;
            case ACTION_DAODA:
                break;
            case ACTION_QUEREN:
                break;
            case ACTION_RECREATE:
                updateModel.carrierId = companyModel.CompanyID;
                updateModel.carrierName = companyModel.CompanyName;
                updateModel.carrierPhone = companyModel.CompanyPhone;
                break;
            case ACTION_REPAIDAN:
                updateModel.goodsMoney = Integer.parseInt(mEtMoneyUser.getText().toString());
                updateModel.reward = Integer.parseInt(mEtMoneyDriver.getText().toString());
                updateModel.driverId = driverModel.id;
                updateModel.driverName = driverModel.DriverName;
                updateModel.driverPhone = driverModel.DriverPhone;
                break;
            default:

        }
        return updateModel;
    }

    /**
     * 更新状态的接口
     * @param state
     * @param updateType
     */
    private void updateState(final int state, int updateType){
        BaseRequest request = new BaseRequest(this);
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        Object[] objects = {state,changeinfo(updateType,state)};
        request.startRequest(url, "updateTranslateInfo", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Log.e(TAG, "success: "+ state );
                setResult(RESULT_OK);
                finish();
                mCheckDialog.dismiss();
            }

            @Override
            public void error(String string) {
                mCheckDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.btn_goods_detail)
    void action(){
        switch (type){
            case TYPE_PAIDAN:
                //updateState(1,ACTION_PAIDAN);
                break;
            case TYPE_CARRIAGE:
                //updateState(2,ACTION_JIEDAN);
                break;
            case TYPE_REFUSE:
                if(role == ROLE_USER){
                    if(state == -1){
                        setCheckDialog(0,ACTION_RECREATE);
                    }
                }else if(role == ROLE_CARRIER){
                    if(state == -2){
                        setCheckDialog(1,ACTION_REPAIDAN);
                    }
                }else {

                }
                break;
            case TYPE_WAIT:
                if(role == ROLE_USER){
                    if(state == 3){
                        setCheckDialog(4,ACTION_QUEREN);
                    }
                }else if(role == ROLE_CARRIER){
                    if(state == 0){
                        //updateState(1,ACTION_PAIDAN);
                    }else {
                        setCheckDialog(1,ACTION_REPAIDAN);
                    }
                }else {
                    if(state == 1){
                        //updateState(2,ACTION_JIEDAN);
                    }else {
                        setCheckDialog(3,ACTION_DAODA);
                    }
                }
                break;
            default:

        }
    }
    @OnClick(R.id.btn_goods_agree)
    void GoodsAgree(){
        switch (type){
            case TYPE_PAIDAN:
                setCheckDialog(1,ACTION_PAIDAN);
                break;
            case TYPE_CARRIAGE:
                setCheckDialog(2,ACTION_JIEDAN);
                break;
            case TYPE_REFUSE:
                break;
            case TYPE_WAIT:
                if(role == ROLE_USER){

                }else if(role == ROLE_CARRIER){
                    if(state == 0){
                        setCheckDialog(1,ACTION_PAIDAN);
                    }else {

                    }
                }else {
                    if(state == 1){
                        setCheckDialog(2,ACTION_JIEDAN);
                    }else {

                    }
                }
                break;
            default:

        }
    }
    @OnClick(R.id.btn_goods_refuse)
    void GoodsRefuse(){
        switch (type){
            case TYPE_PAIDAN:
                setCheckDialog(-1,ACTION_JUJUEPAIDAN);
                break;
            case TYPE_CARRIAGE:
                setCheckDialog(-2,ACTION_JUJUEJIEDAN);
                break;
            case TYPE_REFUSE:
                break;
            case TYPE_WAIT:
                if(role == ROLE_USER){

                }else if(role == ROLE_CARRIER){
                    if(state == 0){
                        setCheckDialog(-1,ACTION_JUJUEPAIDAN);
                    }else {

                    }
                }else {
                    if(state == 1){
                        updateState(-2,ACTION_JUJUEJIEDAN);
                    }else {

                    }
                }
                break;
            default:
        }
    }

    /**
     * update dialog
     * @param state
     * @param ActionType
     */
    private void setCheckDialog(final int state,final int ActionType){
        if(mCheckDialog == null){
            mCheckDialog = new CheckDialog(this);
        }

        switch (ActionType){
            case ACTION_PAIDAN:
                mCheckDialog.setDialogTitle("确定派单？");
                break;
            case ACTION_JUJUEPAIDAN:
                mCheckDialog.setDialogTitle("确定拒绝派单？");
                break;
            case ACTION_JIEDAN:
                mCheckDialog.setDialogTitle("确定接单？");
                break;
            case ACTION_JUJUEJIEDAN:
                mCheckDialog.setDialogTitle("确定拒绝接单？");
                break;
            case ACTION_DAODA:
                mCheckDialog.setDialogTitle("确定到达？");
                break;
            case ACTION_QUEREN:
                mCheckDialog.setDialogTitle("确定确认？");
                break;
            case ACTION_RECREATE:
                mCheckDialog.setDialogTitle("确定重新创建订单？");
                break;
            case ACTION_REPAIDAN:
                mCheckDialog.setDialogTitle("确定重新派单？");
                break;
            default:
                break;
        }
        mCheckDialog.show();


        mCheckDialog.setDialogListener(new CheckDialog.DialogListener() {
            @Override
            public void clickOk() {
                updateState(state,ActionType);
            }

            @Override
            public void clickCancel() {
                mCheckDialog.dismiss();
            }
        });

    }


}
