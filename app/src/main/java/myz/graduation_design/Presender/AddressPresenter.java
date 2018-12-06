package myz.graduation_design.Presender;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import myz.graduation_design.IView.IAddressView;
import myz.graduation_design.Model.AddressModel;
import myz.graduation_design.Model.AddressResult;
import myz.graduation_design.Model.CommentResult;
import myz.graduation_design.Model.delAddressRequest;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;

public class AddressPresenter extends BasePresenter<IAddressView>{

    private IAddressView iLoginView;
    private Context context;
    private int userID;

    public AddressPresenter(IAddressView iLoginView, Context context){
        super(iLoginView);
        this.iLoginView = iLoginView;
        this.context = context;
        userID = Integer.parseInt(SharedPreferenceUtils.getString(context,SharedPreferenceUtils.USERID_KEY,""));

    }

    /**
     * 获取地址列表
     */
    public void getAddressList(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/AddressWSPort?xsd=1";
        final BaseRequest requet = new BaseRequest(context);
        Object[] objects = {userID};
        requet.startRequest(url, "getAddress", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<AddressResult> result = gson.fromJson(string, new TypeToken<List<AddressResult>>(){}.getType());
                //AddressListModel result = gson.fromJson(string, AddressListModel.class);
                Log.e("mao", "success: "+result.get(0).userId );
                iLoginView.getListSuccess(result);

                //TODO need test
            }

            @Override
            public void error(String string) {
                iLoginView.getListError();
            }
        });
    }

    /**
     * 删除地址
     * @param result
     */
    public void delAddress(AddressResult result){
        delAddressRequest model = new delAddressRequest();
        model.setProperty(0,result.id);
        model.setProperty(1,result.username);
        model.setProperty(2,result.addressInfo);
        model.setProperty(3,result.phone);
        model.setProperty(3,result.userId);
        Log.e("mao", "deleteAddress: "+userID );
        String url = StringUtils.BaseUrl+"/LogsticsWS/AddressWSPort?xsd=1";
        BaseRequest requet = new BaseRequest(context);
        requet.startRequest(url, "deleteAddress", model, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                CommentResult result = gson.fromJson(string, CommentResult.class);
                if(result.code == 0){
                    iLoginView.success();
                }else {
                    iLoginView.error();
                }
            }

            @Override
            public void error(String string) {
                iLoginView.error();
            }
        });
    }

    /**
     * 添加地址
     * @param addressInfo
     * @param username
     * @param phone
     */
    public void addAddress(String addressInfo,String username,String phone){
        AddressModel model = new AddressModel();
        model.setProperty(0,username);
        model.setProperty(1,addressInfo);
        model.setProperty(2,phone);
        model.setProperty(3,userID);
        Log.e("mao", "addAddress: "+userID );
        String url = StringUtils.BaseUrl+"/LogsticsWS/AddressWSPort?xsd=1";
        BaseRequest requet = new BaseRequest(context);
        requet.startRequest(url, "addAddress", model, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                CommentResult result = gson.fromJson(string, CommentResult.class);
                if(result.code == 0){
                    iLoginView.success();
                }else {
                    iLoginView.error();
                }
            }

            @Override
            public void error(String string) {
                iLoginView.error();
            }
        });
    }

}
