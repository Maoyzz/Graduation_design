package myz.graduation_design.Presender;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import myz.graduation_design.IView.IsignUpView;
import myz.graduation_design.Model.CommentResult;
import myz.graduation_design.Model.UserModel;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.StringUtils;

/**
 * Created by 10246 on 2018/4/4.
 */

public class SignUpPresenter extends BasePresenter<IsignUpView> {
    private IsignUpView iView;
    private Context context;


    public SignUpPresenter(IsignUpView iView, Context context) {
        super(iView);
        this.iView = iView;
        this.context = context;
    }

    /**
     * sign up
     * @param UserName
     * @param Pwd
     * @param role
     * @param phone
     */
    public void signup(String UserName, String Pwd,int role,String phone){
        Log.e("mao", "signup: " + phone);
        UserModel user = new UserModel();
        user.setProperty(0,UserName);
        user.setProperty(1,Pwd);
        user.setProperty(2,role);
        user.setProperty(3,phone);

        final BaseRequest request = new BaseRequest(context);
        String url= StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        request.startRequest(url,"register", user, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                CommentResult result = gson.fromJson(string, CommentResult.class);
                if(result.code == 0){
                    iView.signUpSuccess();
                }else {
                    iView.signUpError();
                }
                Toast.makeText(context,result.message,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void error(String string) {

            }
        });
    }
}
