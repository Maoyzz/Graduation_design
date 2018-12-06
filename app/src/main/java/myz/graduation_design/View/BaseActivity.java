package myz.graduation_design.View;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import myz.graduation_design.Presender.BasePresenter;

/**
 * Created by 10246 on 2018/4/3.
 */

public abstract class BaseActivity <P extends BasePresenter> extends AppCompatActivity {

    protected P mPresenter;
    public Typeface iconfont;
    public String TAG = "mao";


    protected abstract int getContentLayout();//返回页面布局id
    protected abstract void initView(Bundle savedInstanceState);//做视图相关的初始化工作
    protected abstract void initData(Bundle savedInstanceState);//做数据相关的初始化工作
    protected abstract void initEvent();//做监听事件相关的初始化工作


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
            ButterKnife.bind(this);
        }
        iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        initView(savedInstanceState);//由具体的activity实现，做视图相关的初始化
        initData(savedInstanceState);//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            //mPresenter.destroy();
            mPresenter = null;
        }
    }
    //finish
    public void setBack(int id){
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
