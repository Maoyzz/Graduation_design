package myz.graduation_design.View;

import android.accounts.Account;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myz.graduation_design.Model.AccountModel;
import myz.graduation_design.Presender.TabFragmentListener;
import myz.graduation_design.R;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.AccountAdapter;
import myz.graduation_design.weight.PayPsdInputView;

/**
 * Created by 10246 on 2018/4/3.
 */

public class AccountFragment extends Fragment implements View.OnClickListener{
    private TabFragmentListener fragmentListener;
    private TextView mTvHeaderLeft;
    private TextView mTvHeaderCenter;
    private TextView mTvHeaderRight;
    private SwipeRefreshLayout mSwAccount;
    private RecyclerView mRvAccount;
    private TextView mTvMoney;
    private TextView mTvRecharge;
    Dialog mDialogPay;

    AccountAdapter adapter;


//    private UserLoader mUserLoader;

    public void setTabFragmentListener(TabFragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public static AccountFragment newInstance(TabFragmentListener listener) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setTabFragmentListener(listener);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_m, container, false);
//        mUserLoader = new UserLoader();
        initView(view);
        initDate();
        return view;
    }
    private void initView(View view){
        initHeader(view);
        mSwAccount = (SwipeRefreshLayout)view.findViewById(R.id.sw_account);
        mRvAccount = (RecyclerView)view.findViewById(R.id.rv_account);
        mTvMoney = (TextView)view.findViewById(R.id.tv_money) ;
        adapter = new AccountAdapter(new ArrayList<AccountModel>());
        mRvAccount.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.e("mao", "initView: "+SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERMONEY_KEY,"") );
        String money = StringUtils.getFormatString(getResources(),R.string.rmb, ""+SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERMONEY_KEY,""));
        mRvAccount.setAdapter(adapter);
        mTvMoney.setText(money);
        mTvRecharge = (TextView) view.findViewById(R.id.tv_recharge);
        mTvRecharge.setOnClickListener(this);
    }

    private void initHeader(View view){
        mTvHeaderLeft = (TextView)view.findViewById(R.id.tv_header_left);
        mTvHeaderRight = (TextView)view.findViewById(R.id.tv_header_right);
        mTvHeaderCenter = (TextView)view.findViewById(R.id.tv_header_center);
        mTvHeaderLeft.setVisibility(View.GONE);
        mTvHeaderRight.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvHeaderCenter.setText("账户");
        mTvHeaderRight.setOnClickListener(this);
    }

    private void initDate(){
        AccountModel model = new AccountModel();
        List<AccountModel> result = new ArrayList<AccountModel>();
        model.Content = "订单123456789需要付个费";
        model.state = "未付款";
        model.Time = "2018年5月1号";
        model.title = "需要缴费";
        result.add(model);
        adapter.setNewData(result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_header_right:
                fragmentListener.changeToTab(2);
                break;
            case R.id.tv_recharge:
                createPayDialog();
                break;
        }
    }

    private void createPayDialog() {
        if (mDialogPay == null ){
            mDialogPay = new Dialog(getContext(), R.style.bottomDialog);
        }
        if (mDialogPay.isShowing()){
            return;
        }
        //set dialog position
        Window window = mDialogPay.getWindow();
        window.setContentView(R.layout.dialog_pay_pwd);
        window.setGravity(Gravity.CENTER);
        //set dialog padding to 0
        WindowManager.LayoutParams lp = window.getAttributes();
        //set dialog start position
        lp.width = window.getWindowManager().getDefaultDisplay().getWidth();
        window.getDecorView().setPadding(100, 0, 100, 0);
        window.setAttributes(lp);

        ImageView mTvImgCross = (ImageView)window.findViewById(R.id.tv_img_cross);
        mTvImgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogPay.dismiss();
            }
        });
        TextView mTvPayAmount = (TextView)window.findViewById(R.id.tv_pay_amount);
        TextView mTvChargeAmount = (TextView)window.findViewById(R.id.tv_charge_amount);
        final PayPsdInputView mEtPay = (PayPsdInputView)window.findViewById(R.id.et_pay);
        mEtPay.setOnLengthListener(new PayPsdInputView.OnLengthListener() {
            @Override
            public void onLength(int length) {

            }

            @Override
            public void isLengthSix(String pwd) {

            }
        });
        mDialogPay.show();
    }
}
