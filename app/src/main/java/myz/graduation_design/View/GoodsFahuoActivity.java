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
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.GoodsListAdapter;

/**
 * Created by 10246 on 2018/4/10.
 */

public class GoodsFahuoActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener,SwipeRefreshLayout.OnRefreshListener{

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
    List<GoodsModel> result = new ArrayList<GoodsModel>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_goods_fahuo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initHeader();
        adapter = new GoodsListAdapter(new ArrayList<GoodsListResult>(),iconfont);
        mRvGoods.setAdapter(adapter);
        mRvGoods.setLayoutManager(new LinearLayoutManager(this));
        mSwGoods.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("派单");
        mTvHeaderRight.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getGoodsListByCarrierid();
    }

    @Override
    protected void initEvent() {

    }

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
                for(int i = 0; i < GoodsListResult.size(); i++){
                    if(GoodsListResult.get(i).state != 0){
                        GoodsListResult.remove(i);
                    }
                }

                adapter.setNewData(GoodsListResult);
            }

            @Override
            public void error(String string) {

            }
        });
    }


    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }




    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onRefresh() {

    }
}
