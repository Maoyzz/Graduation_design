package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Model.GoodsRequest;
import myz.graduation_design.Model.updateTransModel;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;

public class MyService extends Service implements AMapLocationListener{

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationOption;
    int role;
    BaseRequest request;
    List<GoodsListResult> GoodsListResult;
    String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
    String Latitude;
    String Longitude;


    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGaodeLocation();
    }

    private void initGaodeLocation(){
        //初始化定位
        request = new BaseRequest(this);
        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(600000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
        Log.e("mao", "initGaodeLocation:1 " );
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e("mao", "driverservice: "+ aMapLocation.getLongitude()+aMapLocation.getLatitude());
        Latitude = aMapLocation.getLatitude()+"";
        Longitude = aMapLocation.getLongitude()+"";
        SharedPreferenceUtils.putString(this,SharedPreferenceUtils.USERLATITUDE_KEY,aMapLocation.getLatitude()+"");
        SharedPreferenceUtils.putString(this,SharedPreferenceUtils.USERLONGITUDE_KEY,aMapLocation.getLongitude()+"");
        if(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERROLE_KEY,"").equals("")){
            role = 0;
        }else {
            role = Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERROLE_KEY,""));
        }
        if(role == 2 && !SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,"").equals("")){
            getGoodsListByDriverid(Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,"")));
        }

    }

    private void getGoodsListByDriverid(int id){
        Object[] objects = {id};
        request.startRequest(url, "findTranslateInfoByDriverid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );
                for (int i = 0; i < GoodsListResult.size(); i++){
                   if(GoodsListResult.get(i).state != 2){
                       GoodsListResult.remove(i);
                   }
                }
                for(int i = 0; i< GoodsListResult.size(); i++){
                    updateState(i);
                }


            }

            @Override
            public void error(String string) {

            }
        });
    }


    private void updateState(int i){
        Gson gson = new Gson();
        List<GoodsRequest> goodsList = new ArrayList<GoodsRequest>();
        String str = gson.toJson(goodsList);
        updateTransModel updateModel = new updateTransModel();
        updateModel.sendId = GoodsListResult.get(i).sendId;
        updateModel.goodsList = str;
        updateModel.remark = GoodsListResult.get(i).remark;
        updateModel.carrierPhone = GoodsListResult.get(i).carrierPhone;
        updateModel.receiverPhone = GoodsListResult.get(i).receiverPhone;
        updateModel.carrierName = GoodsListResult.get(i).carrierName;
        updateModel.startTime = GoodsListResult.get(i).startTime;
        updateModel.state = 2;
        updateModel.goodsName = GoodsListResult.get(i).goodsName;
        updateModel.lat = Latitude;
        updateModel.lng = Longitude;
        updateModel.sendName = GoodsListResult.get(i).sendName;
        updateModel.receiverName = GoodsListResult.get(i).sendName;
        updateModel.startAddress = GoodsListResult.get(i).startAddress;
        updateModel.goodsMoney = GoodsListResult.get(i).goodsMoney;
        updateModel.driverId = GoodsListResult.get(i).driverId;
        updateModel.sendPhone = GoodsListResult.get(i).sendPhone;
        updateModel.driverName = GoodsListResult.get(i).driverName;
        updateModel.driverPhone = GoodsListResult.get(i).driverPhone;
        updateModel.endTime = GoodsListResult.get(i).endTime;
        updateModel.moneyState = GoodsListResult.get(i).moneyState;
        updateModel.carrierId = GoodsListResult.get(i).carrierId;
        updateModel.endAddress = GoodsListResult.get(i).endAddress;
        updateModel.id = GoodsListResult.get(i).id;
        Object[] objects = {2,updateModel};
        request.startRequest(url, "updateTranslateInfo", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {

            }

            @Override
            public void error(String string) {

            }
        });
    }
}