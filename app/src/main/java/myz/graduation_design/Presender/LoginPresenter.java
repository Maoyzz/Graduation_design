package myz.graduation_design.Presender;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import myz.graduation_design.IView.ILoginView;
import myz.graduation_design.Model.CommentResult;
import myz.graduation_design.Model.UserModel;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;

/**
 * Created by 10246 on 2018/4/4.
 */

public class LoginPresenter extends BasePresenter<ILoginView>{

    private ILoginView iLoginView;
    private Context context;

    public LoginPresenter(ILoginView iLoginView, Context context){
        super(iLoginView);
        this.iLoginView = iLoginView;
        this.context = context;
    }

    /**
     * login
     * @param UserName
     * @param Pwd
     * @param type
     */
    public void CheckLogin(final String UserName, final String Pwd,int type){
        UserModel user = new UserModel();
        user.setProperty(0,UserName);
        user.setProperty(1,Pwd);
        user.setProperty(2,type);
        String url= StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        BaseRequest request = new BaseRequest(context);

        request.startRequest(url,"login", user, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                CommentResult result = gson.fromJson(string, CommentResult.class);


                if(result.code == 0){
                    SharedPreferenceUtils.putString(context, SharedPreferenceUtils.USERNAME_KEY, UserName);
                    SharedPreferenceUtils.putString(context, SharedPreferenceUtils.USERPWD_KEY, Pwd);
                    Log.e("mao", "Pwd: "+Pwd );
                    iLoginView.LoginSuccess();
                }else {
                    iLoginView.LoginError();
                }
            }

            @Override
            public void error(String string) {

            }
        });

    }

}
