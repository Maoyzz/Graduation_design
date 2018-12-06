package myz.graduation_design.View;

import android.os.Bundle;
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
import myz.graduation_design.Model.CompanyModel;
import myz.graduation_design.Model.CustomerModel;
import myz.graduation_design.Model.addUserRelationshipRequest;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.CompanyAdapter;

/**
 * Created by 10246 on 2018/4/12.
 */

public class CompanyAddActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.et_header_center)
    TextView mEtHeaderCenter;
    @BindView(R.id.rv_company_add)
    RecyclerView mRvCompanyAdd;

    CompanyAdapter adapter;
    List<CompanyModel> result = new ArrayList<CompanyModel>();
    BaseRequest requet;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_company_add;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderRight.setTypeface(iconfont);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        adapter = new CompanyAdapter(new ArrayList<CompanyModel>(),iconfont,1);
        mRvCompanyAdd.setLayoutManager(new LinearLayoutManager(this));
        mRvCompanyAdd.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        requet = new BaseRequest(this);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @OnClick(R.id.tv_header_right)
    void search(){
        getAllCompanyList(mEtHeaderCenter.getText().toString());
    }


    public void getAllCompanyList(final String query){

        String url = StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        int id = Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""));
        Object[] objects = {id};
        requet.startRequest(url, "findAllUserList",objects , new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                result.clear();
                Gson gson = new Gson();
                List<CustomerModel> list = gson.fromJson(string, new TypeToken<List<CustomerModel>>(){}.getType());

                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).role == 1 && list.get(i).username.contains(query)){
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        CompanyModel model = (CompanyModel)adapter.getData().get(position);
        switch (view.getId()){
            case R.id.layout_del:
            addNewCompany(model.CompanyID);
                Log.e(TAG, "onItemChildClick: " );
            break;
        }
    }

    private void addNewCompany(int carrierId) {
        String url = StringUtils.BaseUrl+"/LogsticsWS/UserWSPort?wsdl";
        addUserRelationshipRequest request = new addUserRelationshipRequest();
        request.carrierId = carrierId;
        request.userId = Integer.parseInt(SharedPreferenceUtils.getString(this,SharedPreferenceUtils.USERID_KEY,""));
        requet.startRequest(url, "addUserRelationship", request, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void error(String string) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
