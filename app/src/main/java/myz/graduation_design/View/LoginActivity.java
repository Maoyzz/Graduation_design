package myz.graduation_design.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.IView.ILoginView;
import myz.graduation_design.Presender.LoginPresenter;
import myz.graduation_design.R;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.rxbus.RxBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 10246 on 2018/4/4.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_pwd)
    EditText mEtUserPwd;
    @BindView(R.id.tv_val)
    TextView mTvVal;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.tv_ic_username)
    TextView mTvIcUserName;
    @BindView(R.id.tv_ic_pwd)
    TextView mTvIcPwd;
    @BindView(R.id.RBtn_User)
    RadioButton mRbUser;
    @BindView(R.id.RBtn_Company)
    RadioButton mRbCompany;
    @BindView(R.id.RBtn_Driver)
    RadioButton mRbDriver;


    private LoginPresenter mLoginPresenter;



    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvIcUserName.setTypeface(iconfont);
        mTvIcPwd.setTypeface(iconfont);
        mRbUser.setTypeface(iconfont);
        mRbCompany.setTypeface(iconfont);
        mRbDriver.setTypeface(iconfont);
        mRbUser.setTextColor(Color.parseColor("#E7ABAA"));
        mRbCompany.setTextColor(Color.parseColor("#fce055"));
        mRbDriver.setTextColor(Color.parseColor("#2FAEFF"));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mLoginPresenter = new LoginPresenter(this,this);

        RxBus.getDefault().toObservable(String.class)
                //在io线程进行订阅，可以执行一些耗时操作
                .subscribeOn(Schedulers.io())
                //在主线程进行观察，可做UI更新操作
                .observeOn(AndroidSchedulers.mainThread())
                //观察的对象
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        if ("finishloginactivity".equals(string)){
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });

    }

    private Boolean JugPwd(){
        if (StringUtils.IsEmpty(mEtUserName.getText().toString())){
            Toast.makeText(this,"账号为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.IsEmpty(mEtUserPwd.getText().toString())){
            Toast.makeText(this,"密码为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void initEvent() {

    }
    /**
     *login success
     */

    @Override
    public void LoginSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("type",0);
        startActivity(intent);
        finish();

    }

    /**
     *login error
     */
    @Override
    public void LoginError() {
        Toast.makeText(this,"登录失败，请重新登录",Toast.LENGTH_SHORT).show();
        mEtUserPwd.setText("");
    }

    @OnClick(R.id.btn_login)
    void login(){
        int type = 0;
        if(JugPwd()){
            if(mRbUser.isChecked()){
                type = 0;
            }else if(mRbCompany.isChecked()){
                type = 1;
            }else {
                type = 2;
            }
            mLoginPresenter.CheckLogin(mEtUserName.getText().toString(),mEtUserPwd.getText().toString(),type);
        }
    }

    @OnClick(R.id.tv_sign)
    void signUp(){
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        intent.putExtra("type",0);
        startActivity(intent);
    }

    @OnClick(R.id.tv_val)
    void loginWithVal(){
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }
}
