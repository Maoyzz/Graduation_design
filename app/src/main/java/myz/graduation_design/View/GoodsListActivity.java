package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Model.GoodsModel;
import myz.graduation_design.Model.GoodsRequest;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.GoodsListAdapter;

/**
 * Created by 10246 on 2018/4/10.
 */

public class GoodsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;

    @BindView(R.id.rv_goods)
    RecyclerView mRvGoods;
    @BindView(R.id.sw_goods)
    SwipeRefreshLayout mSwGoods;


    GoodsListAdapter adapter;

    public final int  TYPE_ALL = 0;
    public final int  TYPE_PAIDAN = 1;
    public final int  TYPE_CARRIAGE = 2;
    public final int  TYPE_WAIT = 3;
    public final int  TYPE_REFUSE = 4;
    int type = 0;
    int role;
    List<GoodsListResult> results = new ArrayList<GoodsListResult>();
    @Override
    protected int getContentLayout() {
        role = Integer.parseInt(SharedPreferenceUtils.getString(GoodsListActivity.this,SharedPreferenceUtils.USERROLE_KEY, ""));
        type = getIntent().getIntExtra("type",0);
        return R.layout.activity_goods_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initHeader();
        adapter = new GoodsListAdapter(new ArrayList<GoodsListResult>(),iconfont);
        mRvGoods.setAdapter(adapter);
        mRvGoods.setLayoutManager(new LinearLayoutManager(this));
        mSwGoods.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
        adapter.openLoadAnimation();

    }

    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        switch (type){
            case TYPE_ALL:
                mTvHeaderCenter.setText("货品列表");
                break;
            case TYPE_PAIDAN:
                mTvHeaderCenter.setText("派单");
                break;
            case TYPE_CARRIAGE:
                mTvHeaderCenter.setText("承运");
                break;
            case TYPE_WAIT:
                mTvHeaderCenter.setText("待确认");
                break;
            case TYPE_REFUSE:
                mTvHeaderCenter.setText("被拒订单");
                break;

        }

        mTvHeaderRight.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        switch (role){
            case 0:
                getGoodsList();
                break;
            case 1:
                getGoodsListByCarrierid();
                break;
            case 2:
                getGoodsListByDriverid();
                break;
            default:
        }

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @Override
    public void onRefresh() {
        mSwGoods.setRefreshing(false);
        switch (role){
            case 0:
                getGoodsList();
                break;
            case 1:
                getGoodsListByCarrierid();
                break;
            case 2:
                getGoodsListByDriverid();
                break;

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GoodsListResult result = (GoodsListResult)adapter.getData().get(position);
        Intent intent = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("result",result);
        startActivity(intent);

    }

    /**
     * 用户获取货品列表
     */
    private void getGoodsList(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(this);
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoBySendid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                //TODO json test
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );
                results.clear();
                for(int i = 0 ;i < GoodsListResult.size() ;i++){
                    switch (type){
                        case TYPE_ALL:
                            results = GoodsListResult;
                            break;
                        case TYPE_REFUSE:
                            if(GoodsListResult.get(i).state == -1){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                        case TYPE_WAIT:
                            if(GoodsListResult.get(i).state == 3){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                    }
                }

                adapter.setNewData(results);
            }

            @Override
            public void error(String string) {

            }
        });
    }

    /**
     * 承运商获取列表
     */


    private void getGoodsListByCarrierid(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(this);
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoByCarrierid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );
                results.clear();
                for(int i=0 ;i < GoodsListResult.size() ;i++){
                    switch (type){
                        case TYPE_ALL:
                            if(GoodsListResult.get(i).state != -1){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                        case TYPE_PAIDAN:
                            if(GoodsListResult.get(i).state == 0){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                        case TYPE_REFUSE:
                            if(GoodsListResult.get(i).state == -2){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                        case TYPE_WAIT:
                            if(GoodsListResult.get(i).state == 0 || GoodsListResult.get(i).state == -2){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                    }
                }
                adapter.setNewData(results);
            }

            @Override
            public void error(String string) {

            }
        });
    }

    /**
     * 司机获得货品列表
     */
    private void getGoodsListByDriverid(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(this);
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoByDriverid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                Log.e("mao", "success: "+GoodsListResult.get(0).carrierName );
                results.clear();
                for(int i=0 ;i < GoodsListResult.size() ;i++){
                    switch (type){
                        case TYPE_ALL:
                            if(GoodsListResult.get(i).state != -2){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                        case TYPE_CARRIAGE:
                            if(GoodsListResult.get(i).state == 1){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                        case TYPE_WAIT:
                            if(GoodsListResult.get(i).state == 1 || GoodsListResult.get(i).state == 2){
                                results.add(GoodsListResult.get(i));
                            }
                            break;
                    }
                }

                adapter.setNewData(results);
            }

            @Override
            public void error(String string) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (role){
                case 0:
                    getGoodsList();
                    break;
                case 1:
                    getGoodsListByCarrierid();
                    break;
                case 2:
                    getGoodsListByDriverid();
                    break;
                default:
            }
        }
    }

}
