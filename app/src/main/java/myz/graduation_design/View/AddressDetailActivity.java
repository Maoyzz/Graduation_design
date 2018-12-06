package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.IView.IAddressView;
import myz.graduation_design.Model.AddressModel;
import myz.graduation_design.Model.AddressResult;
import myz.graduation_design.Presender.AddressPresenter;
import myz.graduation_design.R;

/**
 * Created by 10246 on 2018/4/11.
 */

public class AddressDetailActivity extends BaseActivity implements IAddressView{

//    @BindView(R.id.tv_img_send)
//    TextView mTvImgSend;
    @BindView(R.id.tv_img_receiver)
    TextView mTvImgReceiver;
    @BindView(R.id.tv_img_phone)
    TextView mTvImgPhone;
    @BindView(R.id.tv_img_address)
    TextView mTvImgAddress;
    @BindView(R.id.tv_img_remark)
    TextView mTvImgRemark;
//    @BindView(R.id.tv_sender)
//    TextView mTvSender;
    @BindView(R.id.tv_receiver)
    TextView mTvReceiver;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;
//    @BindView(R.id.et_sender)
//    EditText mEtSender;
    @BindView(R.id.et_receiver)
    EditText mEtReceiver;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.btn_address)
    Button mBtnAddress;
    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;



    AddressResult model;
    AddressPresenter mAddressPresenter;
    private final static int TYPE_ADD = 0;
    private final static int TYPE_CHANGE = 1;
    private int type;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_address_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        model = (AddressResult) getIntent().getSerializableExtra("data");
        type = getIntent().getIntExtra("type",0);

        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("地址详情");
        mTvHeaderRight.setVisibility(View.GONE);

//        mTvImgSend.setTypeface(iconfont);
        mTvImgReceiver.setTypeface(iconfont);
        mTvImgPhone.setTypeface(iconfont);
        mTvImgAddress.setTypeface(iconfont);
        mTvImgRemark.setTypeface(iconfont);

        if(model != null && type == TYPE_CHANGE){
//            mEtSender.setText(model.Sender);
            mEtReceiver.setText(model.username);
            mEtPhone.setText(model.phone);
//            mEtRemark.setText(model.Remark);
            mEtAddress.setText(model.addressInfo);
        }

    }

    @OnClick(R.id.btn_address)
    void press(){
        switch (type){
            case TYPE_ADD:
                break;
            case TYPE_CHANGE:
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddressPresenter = new AddressPresenter(this,this);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @OnClick(R.id.btn_address)
    void Checked(){
        switch (type){
            case TYPE_ADD:
                if(mEtAddress.getText().toString().equals("") || mEtReceiver.getText().toString().equals("") || mEtPhone.getText().toString().equals("")){
                    Toast.makeText(this,"前填写完整的地址信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAddressPresenter.addAddress(mEtAddress.getText().toString(),mEtReceiver.getText().toString(),mEtPhone.getText().toString());
                break;
        }
    }


    @Override
    public void success() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void error() {

    }

    @Override
    public void getListSuccess(Object object) {

    }

    @Override
    public void getListError() {

    }


}
