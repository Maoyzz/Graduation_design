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
import myz.graduation_design.Model.CompanyModel;
import myz.graduation_design.Model.CustomerModel;
import myz.graduation_design.Model.updateTransModel;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.CompanyAdapter;

/**
 * Created by 10246 on 2018/4/12.
 */

public class CompanyListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.sw_company)
    SwipeRefreshLayout mSwCompany;
    @BindView(R.id.rv_company)
    RecyclerView mRvCompany;
    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;

    final static int TYPE_NORMAL = 0;
    final static int TYPE_SEND = 1;
    private int type = 0;

    CompanyAdapter adapter;
    List<CompanyModel> result;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_company_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("物流公司列表");
        mTvHeaderRight.setText(R.string.icon_add);
        mTvHeaderRight.setTypeface(iconfont);

        adapter = new CompanyAdapter(new ArrayList<CompanyModel>(),iconfont,0);
        mRvCompany.setLayoutManager(new LinearLayoutManager(this));
        mRvCompany.setAdapter(adapter);
        mSwCompany.setOnRefreshListener(this);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        result = new ArrayList<CompanyModel>();
        CompanyModel model = new CompanyModel();
        model.CompanyName = "宁波理工";
        model.Description = "宁波理工学院宁波理工学院宁波理工学院宁波理工学院";
        //result.add(0,model);
        //adapter.setNewData(result);

    }

    @Override
    protected void initEvent() {
        getOwnCompanyList();
    }
    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @OnClick(R.id.tv_header_right)
    void addCompany(){

        startActivity(new Intent(this,CompanyAddActivity.class));
    }

    @Override
    public void onRefresh() {
        //adapter.setNewData(result);
        mSwCompany.setRefreshing(false);
        getOwnCompanyList();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.layout_del:
                //del
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CompanyModel model = (CompanyModel) adapter.getData().get(position);
        Intent intent = new Intent();
        intent.putExtra("CompanyModel",model);
        switch (type){
            case TYPE_NORMAL:
                //进入详情
                break;
            case TYPE_SEND:
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }




    public void getOwnCompanyList(){
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
                    if(list.get(i).role == 1){
                        CompanyModel model = new CompanyModel();
                        model.CompanyName = list.get(i).username;
                        model.CompanyID = list.get(i).id;
                        model.CompanyPhone = list.get(i).userPhone;
                        model.Description = "";
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
            getOwnCompanyList();
        }
    }
}
