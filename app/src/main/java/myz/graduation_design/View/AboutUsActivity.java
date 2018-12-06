package myz.graduation_design.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import myz.graduation_design.R;
import myz.graduation_design.Utils.TestUtils;

/**
 * Created by Mao on 2018/4/15.
 */

public class AboutUsActivity extends BaseActivity{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("关于我们");
        mTvHeaderRight.setVisibility(View.GONE);
        mTvVersion.setText("当前版本1.0.1");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initEvent() {

    }
}
