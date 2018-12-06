package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Model.UserInfoResult;
import myz.graduation_design.Presender.TabFragmentListener;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.GpsUtils;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;

/**
 * Created by 10246 on 2018/4/3.
 */

public class MeFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout mRlUserInfo;
    private RelativeLayout mRlGoods;
    private RelativeLayout mRlAccount;
    private RelativeLayout mRlPwd;
    private RelativeLayout mRlOther;

    private LinearLayout mLlGoodsList;
    private LinearLayout mLlSender;
    private LinearLayout mLlAddress;

    private LinearLayout mLlAccountList;
    private LinearLayout mLlRemains;
    private LinearLayout mLlBankCard;

    private LinearLayout mLlLoginPwd;
    private LinearLayout mLlPayPwd;

    private LinearLayout mLlAboutUs;
    private LinearLayout mLlFeedback;
    private LinearLayout mLlCompany;
    private LinearLayout mLlDriver;

    private TextView mTvPhone;
    private TextView mTvName;
    private ImageView mImgAvatar;


    public final int TYPE_LOGIN_PWD = 0;
    public final int TYPE_LOGIN_VAL = 1;





    private TabFragmentListener fragmentListener;

    public void setTabFragmentListener(TabFragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public static MeFragment newInstance(TabFragmentListener listener) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setTabFragmentListener(listener);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_me_m, container, false);
        initView(view);
        getUserInfo();
        if(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERROLE_KEY,"").equals("")){
            SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERROLE_KEY,"0");
        }
        if(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,"").equals("")){
            SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERID_KEY,"0");
        }
        GpsUtils.checkLocation(getContext());
        return view;
    }

    private void initView(View view) {
        mRlUserInfo = (RelativeLayout)view.findViewById(R.id.layout_user_info);
        mRlGoods = (RelativeLayout)view.findViewById(R.id.rl_goods);
        mRlAccount = (RelativeLayout)view.findViewById(R.id.rl_account);
        mRlPwd = (RelativeLayout)view.findViewById(R.id.rl_security);
        mRlOther = (RelativeLayout)view.findViewById(R.id.rl_other);

        mLlGoodsList = (LinearLayout)view.findViewById(R.id.layout_goodslist);
        mLlSender = (LinearLayout)view.findViewById(R.id.layout_sender);
        mLlAddress = (LinearLayout)view.findViewById(R.id.layout_address);
        mLlAccountList = (LinearLayout)view.findViewById(R.id.layout_accountlist);
        mLlRemains = (LinearLayout)view.findViewById(R.id.layout_remians);
        mLlBankCard = (LinearLayout)view.findViewById(R.id.layout_bankcard);
        mLlLoginPwd = (LinearLayout)view.findViewById(R.id.layout_login_pwd);
        mLlPayPwd = (LinearLayout)view.findViewById(R.id.layout_pay_pwd);
        mLlAboutUs = (LinearLayout)view.findViewById(R.id.layout_about_us);
        mLlFeedback = (LinearLayout)view.findViewById(R.id.layout_feedback);
        mLlCompany = (LinearLayout)view.findViewById(R.id.layout_company);
        mLlDriver = (LinearLayout)view.findViewById(R.id.layout_driver);

        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mImgAvatar = (ImageView) view.findViewById(R.id.img_avatar);

        mRlUserInfo.setOnClickListener(this);
        mLlGoodsList.setOnClickListener(this);
        mLlSender.setOnClickListener(this);
        mLlAddress.setOnClickListener(this);
        mLlAccountList.setOnClickListener(this);
        mLlRemains.setOnClickListener(this);
        mLlBankCard.setOnClickListener(this);
        mLlLoginPwd.setOnClickListener(this);
        mLlAboutUs.setOnClickListener(this);
        mLlFeedback.setOnClickListener(this);
        mLlPayPwd.setOnClickListener(this);
        mLlCompany.setOnClickListener(this);
        mLlDriver.setOnClickListener(this);


        TextView mTvImgName = (TextView)view.findViewById(R.id.tv_img_name);
        TextView mTvImgPhone = (TextView)view.findViewById(R.id.tv_img_phone);
        TextView mTvImgSetUp = (TextView)view.findViewById(R.id.tv_img_setup);


        mTvImgName.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvImgPhone.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvImgSetUp.setTypeface(((MainActivity)getActivity()).iconfont);

        mTvImgSetUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_img_setup:
                startActivity(new Intent(getActivity(),UserInfoActivity.class));
                break;
            case R.id.layout_goodslist:
                startActivity(new Intent(getActivity(),GoodsListActivity.class));
                break;
            case R.id.layout_sender:
                startActivity(new Intent(getActivity(),GoodsSenderActivity.class));
                break;
            case R.id.layout_address:
                startActivity(new Intent(getActivity(),AddressListActivity.class));
                break;
            case R.id.layout_company:
                startActivity(new Intent(getActivity(),CompanyListActivity.class));
                break;
            case R.id.layout_driver:
                startActivity(new Intent(getActivity(),DriverListActivity.class));
                break;
            case R.id.layout_accountlist:
                //startActivity(new Intent(getActivity(),AccountListActivity.class));
                fragmentListener.changeToTab(4);
                break;
            case R.id.layout_remians:
                Toast.makeText(getContext(),"余额："+SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERMONEY_KEY,""),Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_bankcard:
                break;
            case R.id.layout_login_pwd:
                Intent intent = new Intent(getActivity(),PwdActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
                break;
            case R.id.layout_pay_pwd:
                Intent intentPwd = new Intent(getActivity(),PwdActivity.class);
                intentPwd.putExtra("type",1);
                startActivity(intentPwd);
                break;
            case R.id.layout_about_us:
                startActivity(new Intent(getActivity(),AboutUsActivity.class));
                break;
            case R.id.layout_feedback:
                startActivity(new Intent(getActivity(),FeedBackActivity.class));
                break;
            default:

        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo(){

        BaseRequest requet = new BaseRequest(getContext());
        String url = StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        String username = SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERNAME_KEY, "");
        String userPhone = SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERPHONE_KEY,"");
        Log.e("mao", "username: " + username );
        Object[] objects;
        if(((MainActivity)getActivity()).type == TYPE_LOGIN_PWD){
            objects = new Object[]{username};
        }else {
            objects = new Object[]{userPhone};

        }

        requet.startRequest(url, "getUserInfo", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                UserInfoResult result = gson.fromJson(string, UserInfoResult.class);
                SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERPHONE_KEY,result.user.userPhone);
                SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.PAYPWD_KEY,result.user.payPwd);
                SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERID_KEY,result.user.id+"");
                SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERMONEY_KEY,result.user.userMoney);
                SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERNAME_KEY, result.user.username);
                SharedPreferenceUtils.putString(getContext(),SharedPreferenceUtils.USERROLE_KEY, result.user.role+"");
                mTvPhone.setText(result.user.userPhone);
                mTvName.setText(result.user.username);
                Log.e("mao", "success: "+result.user.userPhone );
                switch (result.user.role){
                    case 0:
                        mImgAvatar.setImageResource(R.mipmap.img_avatar_user);
                        break;
                    case 1:
                        mImgAvatar.setImageResource(R.mipmap.img_avatar_carrier);
                        break;
                    case 2:
                        mImgAvatar.setImageResource(R.mipmap.img_avatar_driver);
                        break;
                    default:
                        mImgAvatar.setImageResource(R.mipmap.img_avatar_user);
                }
            }

            @Override
            public void error(String string) {

            }
        });
    }




}
