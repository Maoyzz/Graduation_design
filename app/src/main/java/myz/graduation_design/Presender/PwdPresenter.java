package myz.graduation_design.Presender;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import myz.graduation_design.IView.IPwdView;
import myz.graduation_design.Model.CommentResult;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;

public class PwdPresenter extends BasePresenter<IPwdView> {

    private IPwdView iView;
    private Context context;


    public PwdPresenter(IPwdView iView, Context context) {
        super(iView);
        this.iView = iView;
        this.context = context;
    }

    /**
     * 改变登录密码
     * @param newPwd
     */
    public void changeLoginPwd(String newPwd){
        BaseRequest requet = new BaseRequest(context);
        String url = StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        String oldpwd = SharedPreferenceUtils.getString(context, SharedPreferenceUtils.USERPWD_KEY, "");
        String userName = SharedPreferenceUtils.getString(context, SharedPreferenceUtils.USERNAME_KEY, "");
        Object[] objects = {userName,oldpwd,newPwd};
        requet.startRequest(url, "setUserPwd", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                CommentResult result = gson.fromJson(string, CommentResult.class);
                if(result.code == 0){
                    iView.loginPwdSuccess();
                }else {
                    iView.loginPwdError();
                }
                Log.e("mao", "changeLoginPwd: "+string );
            }

            @Override
            public void error(String string) {
                iView.loginPwdError();
            }
        });
    }

    /**
     * 修改支付密码
     * @param newPwd
     */
    public void changePayPwd(String newPwd){
        BaseRequest requet = new BaseRequest(context);
        String url = StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        String oldpwd = SharedPreferenceUtils.getString(context, SharedPreferenceUtils.PAYPWD_KEY, "");
        String userName = SharedPreferenceUtils.getString(context, SharedPreferenceUtils.USERNAME_KEY, "");
        if(oldpwd == null){
            oldpwd = "";
        }
        Object[] objects = {userName,oldpwd,newPwd};
        requet.startRequest(url, "setPayPwd", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                CommentResult result = gson.fromJson(string, CommentResult.class);
                if(result.code == 0){
                    iView.payPwdSuccess();
                }else {
                    iView.payPwdError();
                }
                Log.e("mao", "changeLoginPwd: "+string );
            }

            @Override
            public void error(String string) {
                iView.payPwdError();
            }
        });
    }


}
