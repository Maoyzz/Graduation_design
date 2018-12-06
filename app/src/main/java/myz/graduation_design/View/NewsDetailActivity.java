package myz.graduation_design.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.Model.MessageModel;
import myz.graduation_design.Model.newsModel;
import myz.graduation_design.R;
import myz.graduation_design.Utils.StringUtils;

/**
 * Created by 10246 on 2018/4/8.
 */

public class NewsDetailActivity extends BaseActivity{

    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_sender)
    TextView mTvSender;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    MessageModel model;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_newsdetail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        model = (MessageModel) getIntent().getSerializableExtra("model");
        mTvContent.setText(model.messageContent);
        mTvDate.setText(StringUtils.getFormatString(getResources(),R.string.news_date,model.messageTime));
        mTvSender.setText("系统消息");

        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("新闻详情");
        mTvHeaderRight.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.tv_header_left)
    void Back(){
        finish();
    }
}
