package myz.graduation_design.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import myz.graduation_design.IView.IsignUpView;
import myz.graduation_design.Presender.SignUpPresenter;
import myz.graduation_design.R;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.rxbus.RxBus;
import myz.graduation_design.weight.ValTextView;

/**
 * Created by 10246 on 2018/4/4.
 */

public class SignUpActivity extends BaseActivity<SignUpPresenter> implements IsignUpView,View.OnClickListener{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_val)
    EditText mEtVal;
    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd_new)
    EditText mEtPwdNew;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.RBtn_User)
    RadioButton mRBtnUser;
    @BindView(R.id.RBtn_Driver)
    RadioButton mRBtnDriver;
    @BindView(R.id.RBtn_Company)
    RadioButton mRBtnCompany;


    ValTextView mTvVal;



    private SignUpPresenter signUpPresenter;
    private EventHandler eventHandler;
    private boolean flag=true;
    private String cord_number;
    private String phone_number;
    private boolean ValTrue = false;
    private int type = 0;

    final int TYPE_SIGNUP = 0;
    final int TYPE_LOGIN = 1;
    int role = 0;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        Log.e(TAG, "onCreate: "+type );
        initHeader();
        mTvVal = findViewById(R.id.tv_val);
        if(type == TYPE_LOGIN){
            findViewById(R.id.layout_username).setVisibility(View.GONE);
            findViewById(R.id.layout_pwd).setVisibility(View.GONE);
            findViewById(R.id.layout_pwd_new).setVisibility(View.GONE);
            findViewById(R.id.view1).setVisibility(View.GONE);
            findViewById(R.id.view2).setVisibility(View.GONE);
            findViewById(R.id.view3).setVisibility(View.GONE);
            findViewById(R.id.Rg_Role).setVisibility(View.GONE);
        }
    }

    private void initHeader(){
        switch (type){
            case TYPE_LOGIN:
                mTvHeaderCenter.setText(R.string.login_val);
                break;
            case TYPE_SIGNUP:
                mTvHeaderCenter.setText(R.string.sign_up);
                break;
            default:
        }
        mTvHeaderLeft.setText(R.string.header_icon_back);
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderRight.setVisibility(View.GONE);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        signUpPresenter = new SignUpPresenter(this,this);
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
                    if(smart) {
                        Toast.makeText(getApplicationContext(),"该手机号已经注册过，请重新输入",
                                Toast.LENGTH_LONG).show();
                        mEtPhone.requestFocus();
                        return;
                    }
                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    ValTrue = true;
//                    Toast.makeText(getApplicationContext(), "验证码输入正确",
//                            Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                if(flag)
                {
                    mTvVal.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
                    mEtPhone.requestFocus();
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
        mBtnConfirm.setOnClickListener(this);
        //mTvVal.setOnClickListener(this);
        mTvVal.setValTextViewClick(new ValTextView.ValTextViewClick() {
            @Override
            public void click() {
                if(!judPhone()){
                    return;
                }//去掉左右空格获取字符串
                SMSSDK.getVerificationCode("86",phone_number);
                mEtVal.requestFocus();
            }
        });
    }

    @Override
    public void signUpSuccess() {
        finish();
    }

    @Override
    public void signUpError() {

    }

    @Override
    public void sendVal() {

    }

    @OnClick(R.id.tv_header_left)
    void back(){
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
//            case R.id.tv_val:
//
//                break;
            case R.id.btn_confirm:
                if(type == TYPE_LOGIN){
                    if(!judcodeVal()){
                        return;
                    }
                }else {
                    if(!judCord()){
                        return;
                    }
                }

                //SMSSDK.submitVerificationCode("86",phone_number,cord_number);
                //flag = false;
                if(!ValTrue){
                    if(type == TYPE_LOGIN){
                        SharedPreferenceUtils.putString(this,SharedPreferenceUtils.USERPHONE_KEY,mEtPhone.getText().toString());
                        RxBus.getDefault().post("finishloginactivity");
                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        intent.putExtra("type",1);
                        startActivity(intent);
                        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                    }else {
                        if(mRBtnUser.isChecked()){
                            role = 0;
                        }else if(mRBtnCompany.isChecked()){
                            role = 1;
                        }else {
                            role = 2;
                        }
                        signUpPresenter.signup(mEtUserName.getText().toString(),mEtPwd.getText().toString(),role,mEtPhone.getText().toString());
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean judPhone()
    {
        if(StringUtils.isEmptySpace(mEtPhone.getText().toString().trim()))
        {
            Toast.makeText(SignUpActivity.this,"请输入您的电话号码",Toast.LENGTH_LONG).show();
            mEtPhone.requestFocus();
            return false;
        }
        else if(mEtPhone.getText().toString().trim().length()!=11)
        {
            Toast.makeText(SignUpActivity.this,"您的电话号码位数不正确",Toast.LENGTH_LONG).show();
            mEtPhone.requestFocus();
            return false;
        }
        else
        {
            phone_number = mEtPhone.getText().toString().trim();
            String num="[1][3578]\\d{9}";
            if(phone_number.matches(num))
                return true;
            else
            {
                Toast.makeText(SignUpActivity.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                mEtPhone.requestFocus();
                return false;
            }
        }
    }

    private boolean judCord()
    {
        if(!judPhone()){
            return false;
        }
        if(StringUtils.isEmptySpace(mEtVal.getText().toString().trim()))
        {
            Toast.makeText(SignUpActivity.this,"请输入您的验证码",Toast.LENGTH_LONG).show();
            mEtVal.requestFocus();
            return false;
        }
        else if(mEtVal.getText().toString().trim().length()!=4)
        {
            Toast.makeText(SignUpActivity.this,"您的验证码位数不正确",Toast.LENGTH_LONG).show();
            mEtVal.requestFocus();

            return false;
        }
        else if(!isComplete()){
            return false;
        }
        else
        {
            cord_number=mEtVal.getText().toString().trim();
            return true;
        }
    }

    private boolean judcodeVal(){
        if(!judPhone()){
            return false;
        }
        if(StringUtils.isEmptySpace(mEtVal.getText().toString().trim()))
        {
            Toast.makeText(SignUpActivity.this,"请输入您的验证码",Toast.LENGTH_LONG).show();
            mEtVal.requestFocus();
            return false;
        }
        else if(mEtVal.getText().toString().trim().length()!=4)
        {
            Toast.makeText(SignUpActivity.this,"您的验证码位数不正确",Toast.LENGTH_LONG).show();
            mEtVal.requestFocus();

            return false;
        }
        else {
            cord_number=mEtVal.getText().toString().trim();
            return true;
        }
    }

    private boolean isComplete(){
        if(StringUtils.isEmptySpace(mEtUserName.getText().toString())){
            Toast.makeText(SignUpActivity.this,"请输入您的用户名",Toast.LENGTH_LONG).show();
            mEtUserName.requestFocus();
            return false;
        }
        else if(StringUtils.isEmptySpace(mEtPwd.getText().toString())){
            Toast.makeText(SignUpActivity.this,"请输入您的密码",Toast.LENGTH_LONG).show();
            mEtPwd.requestFocus();
            return false;
        }
        else if(StringUtils.isEmptySpace(mEtPwdNew.getText().toString())){
            Toast.makeText(SignUpActivity.this,"请输入您的新密码",Toast.LENGTH_LONG).show();
            mEtPwdNew.requestFocus();
            return false;
        }
        else if(!mEtPwdNew.getText().toString().equals(mEtPwd.getText().toString())){
            Toast.makeText(SignUpActivity.this,"两次输入的密码请相同",Toast.LENGTH_LONG).show();
            mEtPwdNew.requestFocus();
            return false;
        }else {
            return true;
        }
    }




}
