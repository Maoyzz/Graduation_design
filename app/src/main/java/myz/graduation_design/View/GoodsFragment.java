package myz.graduation_design.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Presender.TabFragmentListener;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;


/**
 * Created by 10246 on 2018/4/3.
 */

public class GoodsFragment extends Fragment implements View.OnClickListener{

    private TabFragmentListener fragmentListener;
    private TextView mTvHeaderLeft;
    private TextView mTvHeaderCenter;
    private TextView mTvHeaderRight;

    private LinearLayout mLayoutGoodsList;
    private LinearLayout mLayoutSender;
    private LinearLayout mLayoutCarriage;
    private LinearLayout mLayoutFahuo;
    private LinearLayout mLayoutWait;
    private LinearLayout mLayoutRefuse;

    private TextView mTvGoodsListTitle;
    private TextView mTvGoodsSendTitle;
    private TextView mTvGoodsFahuoTitle;
    private TextView mTvGoodsCarriageTitle;
    private TextView mTvGoodsWaitTitle;
    private TextView mTvGoodsRefuseTitle;

    private int role;


    //private UserLoader mUserLoader;

    public void setTabFragmentListener(TabFragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public static GoodsFragment newInstance(TabFragmentListener listener) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle args = new Bundle();
        fragment.setTabFragmentListener(listener);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_m, container, false);
        //mUserLoader = new UserLoader();
        role = Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERROLE_KEY,""));
        initView(view);
        return view;
    }

    private void initView(View view){
        initHeader(view);
        mLayoutGoodsList = (LinearLayout)view.findViewById(R.id.layout_goods_list);
        mLayoutSender = (LinearLayout)view.findViewById(R.id.layout_goods_send);
        mLayoutCarriage = (LinearLayout)view.findViewById(R.id.layout_goods_carriage);
        mLayoutFahuo = (LinearLayout)view.findViewById(R.id.layout_goods_fahuo);
        mLayoutWait = (LinearLayout)view.findViewById(R.id.layout_goods_wait);
        mLayoutRefuse = (LinearLayout)view.findViewById(R.id.layout_goods_refuse);

        mTvGoodsListTitle = (TextView)view.findViewById(R.id.tv_goods_list_title);
        mTvGoodsSendTitle = (TextView)view.findViewById(R.id.tv_goods_send_title);
        mTvGoodsFahuoTitle = (TextView)view.findViewById(R.id.tv_goods_fahuo_title);
        mTvGoodsCarriageTitle = (TextView)view.findViewById(R.id.tv_goods_carriage_title);
        mTvGoodsWaitTitle = (TextView)view.findViewById(R.id.tv_goods_wait_title);
        mTvGoodsRefuseTitle = (TextView)view.findViewById(R.id.tv_goods_refuse_title);
//        mTvGoodsListTitle.setText(Html.fromHtml(" 货品列表(<font color=\"#FF0000\">" + 1 + "</font>)"));

        mLayoutGoodsList.setOnClickListener(this);
        mLayoutSender.setOnClickListener(this);
        mLayoutCarriage.setOnClickListener(this);
        mLayoutFahuo.setOnClickListener(this);
        mLayoutWait.setOnClickListener(this);
        mLayoutRefuse.setOnClickListener(this);

        TextView mTvGoodsList = (TextView)view.findViewById(R.id.tv_goods_list);
        TextView mTvGoodsSend = (TextView)view.findViewById(R.id.tv_goods_send);
        TextView mTvGoodsFahuo = (TextView)view.findViewById(R.id.tv_goods_fahuo);
        TextView mTvGoodsCarriage= (TextView)view.findViewById(R.id.tv_goods_carriage);
        TextView mTvGoodsWait = (TextView)view.findViewById(R.id.tv_goods_wait);
        TextView mTvGoodsRefuse = (TextView)view.findViewById(R.id.tv_goods_refuse);

        mTvGoodsList.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvGoodsSend.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvGoodsFahuo.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvGoodsCarriage.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvGoodsWait.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvGoodsRefuse.setTypeface(((MainActivity)getActivity()).iconfont);
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
                break;
        }
    }

    private void initHeader(View view){
        mTvHeaderLeft = (TextView)view.findViewById(R.id.tv_header_left);
        mTvHeaderRight = (TextView)view.findViewById(R.id.tv_header_right);
        mTvHeaderCenter = (TextView)view.findViewById(R.id.tv_header_center);
        mTvHeaderLeft.setVisibility(View.GONE);
        mTvHeaderRight.setTypeface(((MainActivity)getActivity()).iconfont);
        mTvHeaderCenter.setText("货品");
        mTvHeaderRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        intent = new Intent((MainActivity)getActivity(),GoodsListActivity.class);
        switch (view.getId()){
            case R.id.layout_goods_list:
                intent.putExtra("type",0);
                break;
            case R.id.layout_goods_send:
                intent = new Intent((MainActivity)getActivity(),GoodsSenderActivity.class);
                break;
            case R.id.layout_goods_carriage:
                if(role != 2){
                    Toast.makeText(getContext(),"您的角色不为司机，无法权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("type",2);
                break;
            case R.id.layout_goods_fahuo:
                if(role != 1){
                    Toast.makeText(getContext(),"您的角色不为物流公司，无法权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("type",1);
                break;
            case R.id.layout_goods_wait:
                intent.putExtra("type",3);
                break;
            case R.id.layout_goods_refuse:
                intent.putExtra("type",4);
                break;
            case R.id.tv_header_right:
                fragmentListener.changeToTab(2);
                return;
            default:
                break;

        }
        startActivity(intent);
    }

    /**
     * 用户获取货品列表
     */
    private void getGoodsList(){
        String url = StringUtils.BaseUrl+"/LogsticsWS/TranslateInfoWSPort?wsdl";
        BaseRequest request = new BaseRequest(getContext());
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""))};
        request.startRequest(url, "findTranslateInfoBySendid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                //TODO json test
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                int numG = GoodsListResult.size();
                int numR = 0;
                int numW = 0;

                for(int i = 0; i < GoodsListResult.size(); i++){
                    switch (GoodsListResult.get(i).state){
                        case -1:
                            numR = numR + 1;
                            break;
                        case 3:
                            numW = numW + 1;
                            break;

                    }
                }
                mTvGoodsListTitle.setText(Html.fromHtml(" 货品列表(<font color=\"#FF0000\">" + numG + "</font>)"));
                mTvGoodsRefuseTitle.setText(Html.fromHtml(" 被拒订单(<font color=\"#FF0000\">" + numR + "</font>)"));
                mTvGoodsWaitTitle.setText(Html.fromHtml(" 待确认(<font color=\"#FF0000\">" + numW + "</font>)"));

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
        BaseRequest request = new BaseRequest(getContext());
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""))};
        Log.e("mao", "getGoodsListByCarrierid: "+objects );
        request.startRequest(url, "findTranslateInfoByCarrierid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                int numG = GoodsListResult.size();
                int numR = 0;
                int numW = 0;
                int numF = 0;
                for(int i=0 ;i < GoodsListResult.size() ;i++){
                    switch (GoodsListResult.get(i).state){
                        case -1:
                            numG = numG - 1;
                            break;
                        case 0:
                            numF = numF + 1;
                            numW = numW + 1;
                            break;
                        case -2:
                            numR = numR + 1;
                            numW = numW + 1;
                            break;
                    }
                }
                mTvGoodsListTitle.setText(Html.fromHtml(" 货品列表(<font color=\"#FF0000\">" + numG + "</font>)"));
                mTvGoodsRefuseTitle.setText(Html.fromHtml(" 被拒订单(<font color=\"#FF0000\">" + numR + "</font>)"));
                mTvGoodsWaitTitle.setText(Html.fromHtml(" 待确认(<font color=\"#FF0000\">" + numW + "</font>)"));
                mTvGoodsFahuoTitle.setText(Html.fromHtml(" 待派单(<font color=\"#FF0000\">" + numF + "</font>)"));

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
        BaseRequest request = new BaseRequest(getContext());
        Object[] objects = {Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""))};
        Log.e("mao", "getGoodsListByDriverid: "+objects );
        request.startRequest(url, "findTranslateInfoByDriverid", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<GoodsListResult> GoodsListResult = gson.fromJson(string, new TypeToken<List<GoodsListResult>>(){}.getType());
                int numG = GoodsListResult.size();
                int numW = 0;
                int numC = 0;
                for(int i=0 ;i < GoodsListResult.size() ;i++){
                    switch (GoodsListResult.get(i).state){
                        case -2:
                            numG = numG - 1;
                            break;
                        case 1:
                            numC = numC + 1;
                            numW = numW + 1;
                            break;
                        case 2:
                            numW = numW + 1;
                            break;
                    }
                }
                mTvGoodsListTitle.setText(Html.fromHtml(" 货品列表(<font color=\"#FF0000\">" + numG + "</font>)"));
                mTvGoodsWaitTitle.setText(Html.fromHtml(" 待确认(<font color=\"#FF0000\">" + numW + "</font>)"));
                mTvGoodsCarriageTitle.setText(Html.fromHtml(" 承运(<font color=\"#FF0000\">" + numC + "</font>)"));

            }

            @Override
            public void error(String string) {

            }
        });
    }

}
