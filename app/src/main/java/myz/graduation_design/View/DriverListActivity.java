package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.Model.CustomerModel;
import myz.graduation_design.Model.DriverModel;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.DriverAdapter;

/**
 * Created by 10246 on 2018/4/13.
 */

public class DriverListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.rv_driver)
    RecyclerView mRvDriver;
    @BindView(R.id.sw_driver)
    SwipeRefreshLayout mSwDriver;


    DriverAdapter adapter;
    List<DriverModel> result = new ArrayList<DriverModel>();

    final static int TYPE_NORMAL = 0;
    final static int TYPE_ADD = 1;
    int type = 0;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_driverlist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("司机列表");
        mTvHeaderRight.setText(R.string.icon_add);
        mTvHeaderRight.setTypeface(iconfont);

        adapter = new DriverAdapter(new ArrayList<DriverModel>(),0);
        mRvDriver.setLayoutManager(new LinearLayoutManager(this));
        mRvDriver.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
        mSwDriver.setOnRefreshListener(this);
        type = getIntent().getIntExtra("type",0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        DriverModel model = new DriverModel();
        model.DriverName = "叶先生";
        model.DriverPhone = "17858936016";
        //result.add(model);
        //adapter.setNewData(result);
    }

    @Override
    protected void initEvent() {
        getOwnDriverList();
    }

    @Override
    public void onRefresh() {
        mSwDriver.setRefreshing(false);
        getOwnDriverList();
    }

    @OnClick(R.id.tv_header_left)
    void Back(){

    }

    @OnClick(R.id.tv_header_right)
    void search(){
        startActivity(new Intent(DriverListActivity.this,DriverAddActivity.class));
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DriverModel model = (DriverModel)adapter.getData().get(position);
        Intent intent = new Intent();
        intent.putExtra("data",model);
        switch (type){
            case TYPE_ADD:
                setResult(RESULT_OK,intent);
                finish();
                break;
            case TYPE_NORMAL:
                break;
                default:
        }
    }

    public void getOwnDriverList(){
        BaseRequest requet = new BaseRequest(this);
        String url = StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        int id = Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""));
        Object[] objects = {id};
        requet.startRequest(url, "findRelationUserList",objects , new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                result.clear();
                Gson gson = new Gson();
                List<CustomerModel> list = gson.fromJson(string, new TypeToken<List<CustomerModel>>(){}.getType());

                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).role == 2){
                        DriverModel model = new DriverModel();
                        model.DriverPhone = list.get(i).userPhone;
                        model.id = list.get(i).id;
                        model.DriverName = list.get(i).username;
                        result.add(model);
                    }
                }
                adapter.setNewData(result);


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
            getOwnDriverList();
        }
    }

}
