package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import myz.graduation_design.R;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.rxbus.RxBus;

/**
 * Created by 10246 on 2018/4/8.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.layout_avatar)
    RelativeLayout mRlAvatar;
    @BindView(R.id.layout_phone)
    RelativeLayout mRlPhone;
    @BindView(R.id.layout_pwd)
    RelativeLayout mRlPwd;
    @BindView(R.id.layout_about_us)
    RelativeLayout mRlAboutUs;
    @BindView(R.id.layout_feedback)
    RelativeLayout mRlFeedback;
    @BindView(R.id.tv_img_avatar)
    TextView mTvImgAvatar;
    @BindView(R.id.tv_img_phone)
    TextView mTvImgPhone;
    @BindView(R.id.tv_img_pwd)
    TextView mTvImgPwd;
    @BindView(R.id.tv_img_feedback)
    TextView mTvImgFeedback;
    @BindView(R.id.tv_img_about_us)
    TextView mTvImgAboutUs;
    @BindView(R.id.btn_login_out)
    Button mBtnLoginOut;
    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initHeader();
        mRlAvatar.setOnClickListener(this);
        mRlPhone.setOnClickListener(this);
        mRlPwd.setOnClickListener(this);
        mRlAboutUs.setOnClickListener(this);
        mRlFeedback.setOnClickListener(this);
        mBtnLoginOut.setOnClickListener(this);

        mTvImgAvatar.setTypeface(iconfont);
        mTvImgPhone.setTypeface(iconfont);
        mTvImgPwd.setTypeface(iconfont);
        mTvImgFeedback.setTypeface(iconfont);
        mTvImgAboutUs.setTypeface(iconfont);

    }
    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderRight.setVisibility(View.GONE);
        mTvHeaderCenter.setText("账户管理");
        mTvHeaderLeft.setOnClickListener(this);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_avatar:
                break;
            case R.id.layout_phone:
                break;
            case R.id.layout_pwd:
                break;
            case R.id.layout_about_us:
                startActivity(new Intent(UserInfoActivity.this,AboutUsActivity.class));
                break;
            case R.id.layout_feedback:
                startActivity(new Intent(UserInfoActivity.this,FeedBackActivity.class));
                break;
            case R.id.btn_login_out:
                Intent intent = new Intent(UserInfoActivity.this,LoginActivity.class);
                RxBus.getDefault().post("finishMainActivity");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERROLE_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERNAME_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERID_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERPHONE_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERPWD_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.PAYPWD_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERMONEY_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERLATITUDE_KEY,"");
                SharedPreferenceUtils.putString(UserInfoActivity.this,SharedPreferenceUtils.USERLONGITUDE_KEY,"");

                finish();
                startActivity(intent);
                break;
            case R.id.tv_header_left:
                finish();
                break;
            default:

        }
    }
}
