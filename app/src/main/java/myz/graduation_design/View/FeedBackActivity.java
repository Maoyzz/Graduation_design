package myz.graduation_design.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import myz.graduation_design.R;

public class FeedBackActivity extends  BaseActivity{

    @BindView(R.id.tv_header_left)
    TextView mTvHeaderLeft;
    @BindView(R.id.tv_header_center)
    TextView mTvHeaderCenter;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.et_feedback)
    EditText mEtFeedBack;





    @Override
    protected int getContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initHeader();
    }

    private void initHeader(){
        mTvHeaderLeft.setTypeface(iconfont);
        mTvHeaderCenter.setText("意见与建议");
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

    @OnClick(R.id.btn_confirm)
    void Confirm(){
        if(mEtFeedBack.getText().toString().equals("")){
            Toast.makeText(this,"请填写您的反馈，谢谢",Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(this,"谢谢您的反馈",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
