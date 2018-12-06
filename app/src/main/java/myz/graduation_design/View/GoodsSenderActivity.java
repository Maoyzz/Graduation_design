package myz.graduation_design.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.Model.AddressResult;
import myz.graduation_design.Model.CompanyModel;
import myz.graduation_design.Model.GoodsRequest;
import myz.graduation_design.Model.TransRequest;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.OrderDetailViewPagerAdapter;

/**
 * Created by 10246 on 2018/4/10.
 */

public class GoodsSenderActivity extends BaseActivity implements ViewPager.OnPageChangeListener ,View.OnClickListener{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.tl_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_content)
    ViewPager mVpDetail;
    LayoutInflater inflater;

    private View mViewAddress;
    private View mViewGoods;
    private View mViewMoney;

    //view address
    private TextView mTvStartAddress;
    private TextView mTvEndAddress;
    private TextView mTvCompany;
    private TextView mTvStartModify;
    private TextView mTvStartDel;
    private TextView mTvEndModify;
    private TextView mTvEndDel;
    private TextView mTvCompanyModify;
    private TextView mTvCompanyDel;
    private ImageView mImgStart;
    private ImageView mImgEnd;
    private ImageView mImgCompany;

    private TextView mTvImgTimeStart;
    private TextView mTvImgTimeEnd;
    private TextView mTvTimeStart;
    private TextView mTvTimeEnd;
    private EditText mEtLanker;
    private EditText mEtPhone;
    private EditText mEtRemark;
    private Button mBtnSend;

    private TextView mTvPage1;
    private TextView mTvPage2;
    private TextView mTvPage3;



    //view Goods
    private LinearLayout mLayoutGoods;
    private ImageView mImgAdd;
    private ImageView mImgMinus;

    AddressResult startAddressModel;
    AddressResult endAddressModel;
    CompanyModel companyModel;

    Dialog DialogTime;
    TransRequest request;
    String startTime;
    String endTime;


    EditText[] GoodsNamelist = new EditText[99];
    EditText[] GoodsDescribelist = new EditText[99];
    EditText[] GoodsAttributelist = new EditText[99];
    EditText[] GoodsRemarklist = new EditText[99];



    OrderDetailViewPagerAdapter adapter;
    List<View> viewList = new ArrayList<View>();
    List<View> goodsList = new ArrayList<View>();
    List<GoodsRequest> GoodsRequestList = new ArrayList<GoodsRequest>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_goods_sender;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initHeader();
        inflater = LayoutInflater.from(this);
        mViewAddress = inflater.inflate(R.layout.layout_sender_address, null);
        mViewGoods = inflater.inflate(R.layout.layout_sender_goods, null);
        mViewMoney = inflater.inflate(R.layout.layout_sender_money, null);

        viewList.clear();
        viewList.add(mViewAddress);
        viewList.add(mViewGoods);
        viewList.add(mViewMoney);

        String[] titles = {"地址","货品","其他"};
        adapter = new OrderDetailViewPagerAdapter(viewList, titles);

        mVpDetail.setAdapter(adapter);
        mVpDetail.addOnPageChangeListener(this);

        mTabLayout.setupWithViewPager(mVpDetail);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTvPage1 = (TextView) mViewAddress.findViewById(R.id.goods_page1);
        mTvPage2 = (TextView) mViewGoods.findViewById(R.id.goods_page2);
        mTvPage3 = (TextView) mViewMoney.findViewById(R.id.goods_page3);
        mTvPage1.setText(Html.fromHtml(getResources().getString(R.string.goods_page1)));
        mTvPage2.setText(Html.fromHtml(getResources().getString(R.string.goods_page2)));
        mTvPage3.setText(Html.fromHtml(getResources().getString(R.string.goods_page3)));


//1
        mTvStartAddress = (TextView)mViewAddress.findViewById(R.id.tv_startaddress);
        mTvEndAddress = (TextView)mViewAddress.findViewById(R.id.tv_endaddress);
        mTvCompany = (TextView)mViewAddress.findViewById(R.id.tv_company);
        mTvStartModify = (TextView)mViewAddress.findViewById(R.id.tv_startadress_modify);
        mTvStartDel = (TextView) mViewAddress.findViewById(R.id.tv_startadress_del);
        mTvEndModify = (TextView) mViewAddress.findViewById(R.id.tv_endadress_modify);
        mTvEndDel= (TextView) mViewAddress.findViewById(R.id.tv_endadress_del);
        mTvCompanyModify= (TextView) mViewAddress.findViewById(R.id.tv_company_modify);
        mTvCompanyDel= (TextView) mViewAddress.findViewById(R.id.tv_company_del);

        mImgStart = (ImageView) mViewAddress.findViewById(R.id.img_start);
        mImgEnd = (ImageView) mViewAddress.findViewById(R.id.img_end);
        mImgCompany = (ImageView) mViewAddress.findViewById(R.id.img_company);


        mTvStartAddress.setOnClickListener(this);
        mTvEndAddress.setOnClickListener(this);
        mTvCompany.setOnClickListener(this);
        mTvStartModify.setOnClickListener(this);
        mTvStartDel.setOnClickListener(this);
        mTvEndModify.setOnClickListener(this);
        mTvEndDel.setOnClickListener(this);
        mTvCompanyModify.setOnClickListener(this);
        mTvCompanyDel.setOnClickListener(this);

//        drawStartAddress();
        //2

        mImgAdd = (ImageView) mViewGoods.findViewById(R.id.img_add);
        mLayoutGoods = (LinearLayout) mViewGoods.findViewById(R.id.layout_goods);
        mImgAdd.setOnClickListener(this);

        //3

        mTvImgTimeStart = (TextView)mViewMoney.findViewById(R.id.tv_img_time_start);
        mTvImgTimeEnd = (TextView)mViewMoney.findViewById(R.id.tv_img_time_end);
        mTvTimeStart = (TextView)mViewMoney.findViewById(R.id.tv_time_start);
        mTvTimeEnd = (TextView)mViewMoney.findViewById(R.id.tv_time_end);
        mEtLanker = (EditText) mViewMoney.findViewById(R.id.et_lanker);
        mEtPhone = (EditText)mViewMoney.findViewById(R.id.et_phone);
        mEtRemark = (EditText)mViewMoney.findViewById(R.id.et_remark);
        mBtnSend = (Button) mViewMoney.findViewById(R.id.btn_send);
        mTvImgTimeStart.setTypeface(iconfont);
        mTvImgTimeEnd.setTypeface(iconfont);
        mTvTimeStart.setOnClickListener(this);
        mTvTimeEnd.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
    }

    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("寄货");
        mTvHeaderRight.setVisibility(View.GONE);

    }

    private void drawAddress(int type){
        switch (type){
            case 0:
                if(startAddressModel != null){
                    mTvStartAddress.setText(startAddressModel.addressInfo);
                    mImgStart.setImageResource(R.mipmap.icon_success);
                }
                break;

            case 1:
                if(endAddressModel != null){
                    mTvEndAddress.setText(endAddressModel.addressInfo);
                    mImgEnd.setImageResource(R.mipmap.icon_success);
                }
                break;
            default:
        }


    }

    private void drawCompany(){
        if(companyModel != null){
            mTvCompany.setText(companyModel.CompanyName);
            mImgCompany.setImageResource(R.mipmap.icon_success);
        }
    }

    private void addGoodsitem(){
        final View item = inflater.inflate(R.layout.layout_goods, null);
        ImageView mImgMinus = (ImageView) item.findViewById(R.id.img_minus);
        View ViewBorderTop = (View) item.findViewById(R.id.view_goods_top);

        goodsList.add(item);
        mLayoutGoods.addView(item);
        Log.e(TAG, "goodsList.size(): "+goodsList.size() );
        for(int i = 0; i < goodsList.size(); i++){
            GoodsNamelist[i] = (EditText)goodsList.get(i).findViewById(R.id.et_item_goods_name);
            GoodsDescribelist[i] = (EditText)goodsList.get(i).findViewById(R.id.et_item_goods_describe);
            GoodsAttributelist[i] = (EditText)goodsList.get(i).findViewById(R.id.et_item_goods_attribute);
            GoodsRemarklist[i] = (EditText)goodsList.get(i).findViewById(R.id.et_item_goods_remark);
        }
        if(goodsList.get(0) == item){
            ViewBorderTop.setVisibility(View.VISIBLE);
        }
        mImgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsList.remove(item);
                mLayoutGoods.removeView(item);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        request = new TransRequest();


    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.tv_startaddress:
                intent = new Intent(GoodsSenderActivity.this,AddressListActivity.class);
                intent.putExtra("type",0);
                startActivityForResult(intent,100);
                break;
            case R.id.tv_endaddress:
                intent = new Intent(GoodsSenderActivity.this,AddressListActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,101);
                break;
            case R.id.tv_company:
                intent = new Intent(GoodsSenderActivity.this,CompanyListActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,104);
                break;
            case R.id.tv_startadress_modify:
                intent = new Intent(GoodsSenderActivity.this,AddressListActivity.class);
                intent.putExtra("type",2);
                startActivityForResult(intent,102);
                break;
            case R.id.tv_startadress_del:
                startAddressModel = null;
                mTvStartAddress.setText("请添加启运地");
                mImgStart.setImageResource(R.mipmap.icon_warning);
                break;
            case R.id.tv_endadress_modify:
                intent = new Intent(GoodsSenderActivity.this,AddressListActivity.class);
                intent.putExtra("type",3);
                startActivityForResult(intent,103);
                break;
            case R.id.tv_endadress_del:
                endAddressModel = null;
                mTvEndAddress.setText("请添加目的地");
                mImgEnd.setImageResource(R.mipmap.icon_warning);
                break;
            case R.id.tv_company_modify:
                intent = new Intent(GoodsSenderActivity.this,CompanyListActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,105);
                break;
            case R.id.tv_company_del:
                companyModel = null;
                mTvCompany.setText("请选择承运商");
                mImgCompany.setImageResource(R.mipmap.icon_warning);
                break;
            case R.id.img_add:
                addGoodsitem();
                break;
            case R.id.tv_time_start:
                setTimeDialog(mTvTimeStart);
                break;
            case R.id.tv_time_end:
                setTimeDialog(mTvTimeEnd);
                break;
            case R.id.btn_send:
                addNewTrans();
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    startAddressModel = (AddressResult) data.getSerializableExtra("startAddress");
                    drawAddress(0);
                    break;
                case 101:
                    endAddressModel = (AddressResult)data.getSerializableExtra("endAddress");
                    drawAddress(1);
                    break;
                case 102:
                    startAddressModel = (AddressResult)data.getSerializableExtra("startAddress");
                    drawAddress(0);
                    break;
                case 103:
                    endAddressModel = (AddressResult)data.getSerializableExtra("endAddress");
                    drawAddress(1);
                    break;
                case 104:
                case 105:
                    companyModel = (CompanyModel)data.getSerializableExtra("CompanyModel");
                    Log.e(TAG, "CompanyModelID: "+companyModel.CompanyID );
                    drawCompany();
                    break;
                default:
                    break;
            }
        }

    }

    private void setTimeDialog(final TextView mTextView){
        if (DialogTime == null ){
            DialogTime = new Dialog(this, R.style.bottomDialog);
        }
        if (DialogTime.isShowing()){
            return;
        }
        Window window = DialogTime.getWindow();
        window.setContentView(R.layout.layout_dialog);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = window.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(lp);
        window.getDecorView().setPadding(70, 0, 70, 0);
        final EditText mEtYaer = (EditText)window.findViewById(R.id.et_time_year);
        final EditText mEtMonth = (EditText)window.findViewById(R.id.et_time_month);
        final EditText mEtDay = (EditText)window.findViewById(R.id.et_time_day);
        final EditText mEtHour = (EditText)window.findViewById(R.id.et_time_hour);
        mEtYaer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        mEtMonth.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        mEtDay.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        mEtHour.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        TextView mTvBtn = (TextView)window.findViewById(R.id.tv_btn_time);
        DialogTime.show();
        mTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mEtYaer.getText().toString().equals("") && !mEtMonth.getText().toString().equals("") && !mEtDay.getText().toString().equals("") &&
                        !mEtHour.getText().toString().equals("") && Float.parseFloat(mEtYaer.getText().toString()) >= 2018 &&
                        Float.parseFloat(mEtMonth.getText().toString()) <= 12 && Float.parseFloat(mEtDay.getText().toString()) <= 31 && Float.parseFloat(mEtHour.getText().toString()) <= 24){
                    mTextView.setText(mEtYaer.getText().toString() +"年"+mEtMonth.getText().toString()+"月"+mEtDay.getText().toString() +"日"+ mEtHour.getText().toString() +"时");
                    DialogTime.dismiss();
                }else {
                    Toast.makeText(GoodsSenderActivity.this,"请填写正确时间",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void addNewTrans(){

        if(companyModel == null){
            Toast.makeText(this,"请添加承运商",Toast.LENGTH_SHORT).show();
            return;
        }
        if(startAddressModel == null){
            Toast.makeText(this,"请添加启运地",Toast.LENGTH_SHORT).show();
            return;
        }
        if(endAddressModel == null){
            Toast.makeText(this,"请添加目的地",Toast.LENGTH_SHORT).show();
            return;
        }
        if(goodsList.size() == 0){
            Toast.makeText(this,"请添加货品",Toast.LENGTH_SHORT).show();
            return;
        }


        GoodsRequest requestG = new GoodsRequest();
        for(int i = 0; i < goodsList.size(); i++){
            requestG.goodsRemark = GoodsRemarklist[i].getText().toString();
            requestG.goodsName = GoodsNamelist[i].getText().toString();
            requestG.goodsAttribute = GoodsAttributelist[i].getText().toString();
            requestG.goodsDescribe= GoodsDescribelist[i].getText().toString();
            GoodsRequestList.add(requestG);
        }
        Gson gson = new Gson();
        String goodsList = gson.toJson(GoodsRequestList);
        Log.e(TAG, "addNewTrans: "+ goodsList);


        request.carrierId = companyModel.CompanyID;
        request.carrierName = companyModel.CompanyName;
        request.carrierPhone = companyModel.CompanyPhone;
//        request.driverId = 0;
        request.driverName = "";
        request.driverPhone = "";
        request.endAddress = endAddressModel.addressInfo;
        request.endTime = mTvTimeEnd.getText().toString();
        request.goodsList = goodsList;
        request.goodsMoney = 0;
        request.goodsName = GoodsRequestList.get(0).goodsName;
        request.moneyState = 0;
        request.receiverName = mEtLanker.getText().toString();
        request.receiverPhone = mEtPhone.getText().toString();
        request.remark = mEtRemark.getText().toString();
        request.reward = 0;
        request.sendId = Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""));
        request.sendName = SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERNAME_KEY,"");
        request.sendPhone = SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERPHONE_KEY,"");
        request.startAddress = startAddressModel.addressInfo;
        request.startTime = mTvTimeStart.getText().toString();
        request.state = 0;
        Object[] objects = {0,request};
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest baseRequest = new BaseRequest(this);
        baseRequest.startRequest(url, "addTranslateInfo", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Toast.makeText(GoodsSenderActivity.this,"创建订单成功",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void error(String string) {
                Toast.makeText(GoodsSenderActivity.this,string,Toast.LENGTH_SHORT).show();
            }
        });





    }


}
