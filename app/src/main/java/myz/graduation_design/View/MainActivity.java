package myz.graduation_design.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import myz.graduation_design.Presender.TabFragmentListener;
import myz.graduation_design.R;

import myz.graduation_design.Presender.MainPresenter;
import myz.graduation_design.rxbus.RxBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import service.MyService;

/**
 * Created by 10246 on 2018/4/3.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener ,TabFragmentListener {

    private Fragment mFragmentMe;
    private Fragment mFragmentGoods;
    private Fragment mFragmentCom;
    private Fragment mFragmentNews;
    private Fragment mFragmentAccount;

    private LinearLayout mLayoutBottomMe;
    private LinearLayout mLayoutBottomGoods;
    private RelativeLayout mLayoutBottomCom;
    private LinearLayout mLayoutBottomNews;
    private LinearLayout mLayoutBottomAccount;

    private TextView mTvBottomMe;
    private TextView mTvBottomGoods;
    private TextView mTvBottomNews;
    private TextView mTvBottomAccount;
    private ImageView mImgBottomCom;

    private TextView mTvImgBottomMe;
    private TextView mTvImgBottomGoods;
    private TextView mTvImgBottomNews;
    private TextView mTvImgBottomAccount;

    private int mCurrentFragmentWhich;
    private Fragment mCurrentFragment;
    public int type;
    FragmentManager fm = getSupportFragmentManager();



    Fragment[] fragmentArray = {mFragmentMe, mFragmentGoods, mFragmentCom,mFragmentNews,mFragmentAccount};
    TextView[] tabTvImgArray = {mTvBottomMe, mTvBottomGoods, mTvBottomNews,mTvBottomAccount};
    TextView[] tabTvArray = {mTvImgBottomMe, mTvImgBottomGoods, mTvImgBottomNews,mTvImgBottomAccount};
    final int TAB_ME = 0;
    final int TAB_GOODS = 1;
    final int TAB_NEWS = 2;
    final int TAB_ACCOUNT = 3;

    final int FRA_ME = 0;
    final int FRA_GOODS = 1;
    final int FRA_NEWS = 2;
    final int FRA_COM = 3;
    final int FRA_ACCOUNT = 4;


    @Override
    protected int getContentLayout() {
        type = getIntent().getIntExtra("type",0);
        return R.layout.activity_main_m;
    }



    @Override
    protected void initView(Bundle savedInstanceState) {
        initTab();
        initFragment();
    }

    private void initTab(){
        //start service
        Intent intent = new Intent(this, MyService.class);
        startService(intent);


        tabTvArray[TAB_GOODS] = findViewById(R.id.tv_bottom_goods);
        tabTvArray[TAB_NEWS] = findViewById(R.id.tv_bottom_news);
        tabTvArray[TAB_ACCOUNT] = findViewById(R.id.tv_bottom_account);
        tabTvArray[TAB_ME] = findViewById(R.id.tv_bottom_me);

        tabTvImgArray[TAB_GOODS] = findViewById(R.id.tv_img_bottom_goods);
        tabTvImgArray[TAB_NEWS] = findViewById(R.id.tv_img_bottom_news);
        tabTvImgArray[TAB_ACCOUNT] = findViewById(R.id.tv_img_bottom_account);
        tabTvImgArray[TAB_ME] = findViewById(R.id.tv_img_bottom_me);

        mLayoutBottomMe = findViewById(R.id.layout_bottom_me);
        mLayoutBottomGoods = findViewById(R.id.layout_bottom_goods);
        mLayoutBottomNews = findViewById(R.id.layout_bottom_news);
        mLayoutBottomAccount = findViewById(R.id.layout_bottom_account);
        mLayoutBottomCom = findViewById(R.id.layout_bottom_com);

        mLayoutBottomMe.setOnClickListener(this);
        mLayoutBottomGoods.setOnClickListener(this);
        mLayoutBottomNews.setOnClickListener(this);
        mLayoutBottomAccount.setOnClickListener(this);
        mLayoutBottomCom.setOnClickListener(this);


        mImgBottomCom = findViewById(R.id.img_bottom_com);



        for(int i = 0; i < tabTvImgArray.length; i ++){
            tabTvImgArray[i].setTypeface(iconfont);
        }
        checkTab(TAB_ME,false);
    }

    public void checkTabItem(TextView imgText, TextView tvText){
        imgText.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvText.setTextColor(getResources().getColor(R.color.colorPrimary));
//        switch (imgText.getId()){
//            case R.id.img_tab_start:
//                imgText.setText(R.string.tab_start_img_checked);
//                break;
//            case R.id.img_tab_ontheway:
//                imgText.setText(R.string.tab_ontheway_img_checked);
//                break;
//            case R.id.img_tab_me:
//                imgText.setText(R.string.tab_me_img_checked);
//                break;
//        }
    }

    private void checkTab(int which ,Boolean isCheckImg){

        for (int i = 0; i < tabTvImgArray.length; i++){
            if(isCheckImg){
                uncheckTabItem(tabTvImgArray[i], tabTvArray[i]);
            }else {
                if (i == which){
                    checkTabItem(tabTvImgArray[i], tabTvArray[i]);
                }else{
                    uncheckTabItem(tabTvImgArray[i], tabTvArray[i]);
                }
            }

        }
    }

    public void uncheckTabItem(TextView imgText, TextView tvText){
        imgText.setTextColor(getResources().getColor(R.color.tab_text_unchecked));
        tvText.setTextColor(getResources().getColor(R.color.tab_text_unchecked));
//        switch (imgText.getId()){
//            case R.id.img_tab_start:
//                imgText.setText(R.string.tab_start_img_unchecked);
//                break;
//            case R.id.img_tab_ontheway:
//                imgText.setText(R.string.tab_ontheway_img_unchecked);
//                break;
//            case R.id.img_tab_me:
//                imgText.setText(R.string.tab_me_img_unchecked);
//                break;
//        }
    }


    private void initFragment(){

        if (fragmentArray[FRA_ME] == null) {
            fragmentArray[FRA_ME] = MeFragment.newInstance(this);
            mCurrentFragmentWhich = FRA_ME;
            mCurrentFragment = fragmentArray[FRA_ME];
            fm.beginTransaction().add(R.id.content, mCurrentFragment).commit();
        }
    }

    /**
     * hide other Fragment
     */
    private void hideFragments(int which){
        for (int i = 0; i < fragmentArray.length; i++){
            if (fragmentArray[i] != null && which != i){
                fm.beginTransaction().hide(fragmentArray[i]).commit();
            }
        }
    }
    private void switchFragment(int which){
        mCurrentFragmentWhich = which;
        hideFragments(which);
        if (fragmentArray[which] == null){
            switch (which){
                case FRA_GOODS:
                    fragmentArray[which] = GoodsFragment.newInstance(this);
                    break;
                case FRA_ME:
                    fragmentArray[which] = MeFragment.newInstance(this);
                    ((MeFragment)fragmentArray[which]).onResume();
                    break;
                case FRA_NEWS:
                    fragmentArray[which] = NewsFragment.newInstance(this);
                    break;
                case FRA_ACCOUNT:
                    fragmentArray[which] = AccountFragment.newInstance(this);
                    break;
                case FRA_COM:
                    fragmentArray[which] = OnWayFragment.newInstance(this);
                    break;
            }
            fm.beginTransaction().add(R.id.content,fragmentArray[which]).commit();
        }else{
            fm.beginTransaction().show(fragmentArray[which]).commit();
            if (which == FRA_ME){
                ((MeFragment)fragmentArray[which]).onResume();
            }
        }
        //
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        RxBus.getDefault().toObservable(String.class)
                //在io线程进行订阅，可以执行一些耗时操作
                .subscribeOn(Schedulers.io())
                //在主线程进行观察，可做UI更新操作
                .observeOn(AndroidSchedulers.mainThread())
                //观察的对象
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        if ("finishMainActivity".equals(string)){
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    protected void initEvent() {

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.layout_bottom_me:
                checkTab(TAB_ME,false);
                switchFragment(FRA_ME);
                break;
            case R.id.layout_bottom_goods:
                checkTab(TAB_GOODS,false);
                switchFragment(FRA_GOODS);
                break;
            case R.id.layout_bottom_news:
                checkTab(TAB_NEWS,false);
                switchFragment(FRA_NEWS);
                break;
            case R.id.layout_bottom_account:
                checkTab(TAB_ACCOUNT,false);
                switchFragment(FRA_ACCOUNT);
                break;
            case R.id.layout_bottom_com:
                checkTab(6,true);
                switchFragment(FRA_COM);
                break;
                default:

        }
    }

    @Override
    public void createVerityDialog() {

    }

    @Override
    public void changeToTab(int tabid) {

        switch (tabid){
            case FRA_ME:
                checkTab(TAB_ME,false);
                switchFragment(FRA_ME);
                break;
            case FRA_GOODS:
                checkTab(TAB_GOODS,false);
                switchFragment(FRA_GOODS);
                break;
            case FRA_NEWS:
                checkTab(TAB_NEWS,false);
                switchFragment(FRA_NEWS);
                break;
            case FRA_ACCOUNT:
                checkTab(TAB_ACCOUNT,false);
                switchFragment(FRA_ACCOUNT);
                break;
            case FRA_COM:
                checkTab(6,true);
                switchFragment(FRA_COM);
                break;
            default:

        }


    }

    @Override
    public int getCurrentTab() {
        return 0;
    }
}
