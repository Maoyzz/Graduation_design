package myz.graduation_design.View;

import android.os.Bundle;
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
import myz.graduation_design.Model.addUserRelationshipRequest;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.DriverAdapter;

public class DriverAddActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.et_header_center)
    TextView mEtHeaderCenter;
    @BindView(R.id.rv_driver_add)
    RecyclerView mRvDriverAdd;
    BaseRequest requet;
    DriverAdapter adapter;
    List<DriverModel> result = new ArrayList<DriverModel>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_driver_add;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initHeader();
        mRvDriverAdd.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderRight.setText(R.string.icon_search);
        mTvHeaderRight.setTypeface(iconfont);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        adapter = new DriverAdapter(new ArrayList<DriverModel>(),1);
        mRvDriverAdd.setAdapter(adapter);
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
        getAllDriverList(mEtHeaderCenter.getText().toString());
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        DriverModel model = (DriverModel)adapter.getData().get(position);
        switch (view.getId()){
            case R.id.img_add:
                addNewDriver(model.id);
                break;
        }
    }


    public void getAllDriverList(final String query){

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
                    if(list.get(i).role == 2 && list.get(i).username.contains(query)){
                        DriverModel model = new DriverModel();
                        model.DriverName = list.get(i).username;
                        model.id = list.get(i).id;
                        model.DriverPhone = list.get(i).userPhone;
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

    private void addNewDriver(int carrierId) {
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


}
