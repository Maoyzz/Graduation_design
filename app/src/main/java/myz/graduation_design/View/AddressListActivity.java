package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.IView.IAddressView;
import myz.graduation_design.Model.AddressListModel;
import myz.graduation_design.Model.AddressModel;
import myz.graduation_design.Model.AddressResult;
import myz.graduation_design.Presender.AddressPresenter;
import myz.graduation_design.R;
import myz.graduation_design.adapter.AddressAdapter;

/**
 * Created by 10246 on 2018/4/11.
 */

public class AddressListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener,IAddressView{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.Sw_address)
    SwipeRefreshLayout mSwAddress;
    @BindView(R.id.Rv_address)
    RecyclerView mRvAddress;

    AddressAdapter adapter;
    AddressPresenter mAddressPresenter;

    private final static int TYPE_START_ADDRESS_NEW = 0;
    private final static int TYPE_END_ADDRESS_NEW = 1;
    private final static int TYPE_START_ADDRESS_MODIFY = 2;
    private final static int TYPE_END_ADDRESS_MODIFY = 3;
    private final static int TYPE_NORMAL = 4;

    private int type = 4;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("地址列表");
        mTvHeaderRight.setText(R.string.icon_add);
        mTvHeaderRight.setTypeface(iconfont);
        mTvHeaderRight.setTextSize(22);

        mSwAddress.setOnRefreshListener(this);
        adapter = new AddressAdapter(new ArrayList<AddressResult>(),iconfont);
        mRvAddress.setAdapter(adapter);
        mRvAddress.setLayoutManager(new LinearLayoutManager(this));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSwAddress.setRefreshing(true);
        mAddressPresenter = new AddressPresenter(this,this);
        mAddressPresenter.getAddressList();
        mSwAddress.setRefreshing(false);


//        getAddressList();
        type = getIntent().getIntExtra("type",4);
    }

    @Override
    protected void initEvent() {

    }

    private void getAddressList(String string){
        //TODO fakeDate
//        adapter.setNewData(fakeDate);
        mSwAddress.setRefreshing(false);
    }

    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }

    @OnClick(R.id.tv_header_right)
    void addNewAddress(){
        startActivityForResult((new Intent(AddressListActivity.this,
                AddressDetailActivity.class)).putExtra("type",0), 100);
    }

    @Override
    public void onRefresh() {
        mSwAddress.setRefreshing(false);
        get();
    }

    private void get(){
        mAddressPresenter.getAddressList();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        AddressResult model = (AddressResult) adapter.getData().get(position);
        switch (view.getId()){
            case R.id.layout_edit:
                Intent intent = new Intent(AddressListActivity.this,AddressDetailActivity.class);
                intent.putExtra("data",model);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.layout_del:
                mAddressPresenter.delAddress(model);
                Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AddressResult model = (AddressResult) adapter.getData().get(position);
        Intent intent = new Intent();
        switch (type){
            case TYPE_START_ADDRESS_NEW:
                intent.putExtra("startAddress",model);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case TYPE_START_ADDRESS_MODIFY:
                intent.putExtra("startAddress",model);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case TYPE_END_ADDRESS_NEW:
                intent.putExtra("endAddress",model);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case TYPE_END_ADDRESS_MODIFY:
                intent.putExtra("endAddress",model);
                setResult(RESULT_OK,intent);
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void success() {
        get();
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error() {
        mSwAddress.setRefreshing(false);
        Toast.makeText(this,"删除出错",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getListSuccess(Object object) {
        List<AddressResult> list = new ArrayList<AddressResult>();
        list = (List<AddressResult>)object;
        adapter.setNewData(list);
//        Log.e("mao", "getListSuccess: "+model.list.get(0).id );
        mSwAddress.setRefreshing(false);
    }

    @Override
    public void getListError() {
        mSwAddress.setRefreshing(false);
        Toast.makeText(this,"获取列表错误",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            get();
        }
    }
}
