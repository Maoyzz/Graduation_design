package myz.graduation_design.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import myz.graduation_design.IView.IPwdView;
import myz.graduation_design.Presender.PwdPresenter;
import myz.graduation_design.R;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.weight.CheckDialog;
import myz.graduation_design.weight.ValTextView;

/**
 * Created by Mao on 2018/4/15.
 */

public class PwdActivity extends BaseActivity implements View.OnClickListener,IPwdView{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.et_val)
    EditText mEtVal;
    @BindView(R.id.et_pwd_new)
    EditText mEtPwdNew;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;

    @BindView(R.id.tv_val)
    ValTextView mTvVal;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;


    final static int TYPE_LOGIN_PWD = 0;
    final static int TYPE_PAY_PWD = 1;
    private EventHandler eventHandler;
    private boolean flag=true;
    private boolean ValTrue = false;

    PwdPresenter mPwdPresenter;
    String phone;
    CheckDialog mCheckDialog;

    int type = 0;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_pwd;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        phone = SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERPHONE_KEY,"");
        mTvPhone.setText(phone);
        initHeader();
    }

    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderRight.setVisibility(View.GONE);
        switch (type){
            case TYPE_LOGIN_PWD:
                mTvHeaderCenter.setText("修改登录密码");
                break;
            case TYPE_PAY_PWD:
                mTvHeaderCenter.setText("修改支付密码");
                break;
            default:
        }
        mTvHeaderLeft.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPwdPresenter = new PwdPresenter(this,this);
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if(result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
//                    if(smart) {
//                        Toast.makeText(getApplicationContext(),"该手机号已经注册过，请重新输入",
//                                Toast.LENGTH_LONG).show();
//                        mEtPhone.requestFocus();
//                        return;
//                    }
                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    ValTrue = true;
                    Toast.makeText(getApplicationContext(), "验证码输入正确",
                            Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                if(flag)
                {
                    mTvVal.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
                    //mEtPhone.requestFocus();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_LONG).show();
                }
            }

        }

    };

    @Override
    protected void initEvent() {
        mTvVal.setValTextViewClick(new ValTextView.ValTextViewClick() {
            @Override
            public void click() {
//                if(!judPhone()){
//                    return;
//                }//去掉左右空格获取字符串
                SMSSDK.getVerificationCode("86",mTvPhone.getText().toString().trim());
                mEtVal.requestFocus();
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_left:
                finish();
                break;
            case R.id.btn_confirm:
                switch (type){
                    case TYPE_LOGIN_PWD:
                        setCheckDialog(TYPE_LOGIN_PWD);
//                        mPwdPresenter.changeLoginPwd(mEtPwd.getText().toString());
                        break;
                    case TYPE_PAY_PWD:
                        setCheckDialog(TYPE_PAY_PWD);
//                        mPwdPresenter.changePayPwd(mEtPwd.getText().toString());
                        break;
                    default:
                        break;
                }
                //SMSSDK.submitVerificationCode("86",mTvPhone.getText().toString(),mEtVal.getText().toString());
                break;
        }
    }

    /**
     * press dialog
     * @param type
     */
    private void setCheckDialog(final int type){
        if(mCheckDialog == null){
            mCheckDialog = new CheckDialog(this);
        }
        switch (type){
            case TYPE_LOGIN_PWD:
                mCheckDialog.setDialogTitle("确定修改登录密码？");
                break;
            case TYPE_PAY_PWD:
                mCheckDialog.setDialogTitle("确定修改支付密码？");
                break;
            default:
                break;
        }
        mCheckDialog.show();
        mCheckDialog.setDialogListener(new CheckDialog.DialogListener() {
            @Override
            public void clickOk() {
                switch (type){
                    case TYPE_LOGIN_PWD:
                        mPwdPresenter.changeLoginPwd(mEtPwd.getText().toString());
                        break;
                    case TYPE_PAY_PWD:
                        mPwdPresenter.changePayPwd(mEtPwd.getText().toString());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void clickCancel() {
                mCheckDialog.dismiss();
            }
        });
    }

    @Override
    public void loginPwdSuccess() {
        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        finish();
        mCheckDialog.dismiss();
    }

    @Override
    public void loginPwdError() {
        mCheckDialog.dismiss();
    }

    @Override
    public void payPwdSuccess() {
        Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        finish();
        mCheckDialog.dismiss();
    }

    @Override
    public void payPwdError() {
        mCheckDialog.dismiss();

    }
}
