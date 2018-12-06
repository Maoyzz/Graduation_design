package myz.graduation_design.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import myz.graduation_design.R;
import myz.graduation_design.Utils.StringUtils;
//import com.transcend.qiyunlogistics.Utils.StringUtils;

/**
 * Created by myz on 2018/3/22.
 */

public class ValTextView extends android.support.v7.widget.AppCompatTextView {

    public CountDownTimer timer;
    public String time;
    public int mTextColor;
    public String mText;
    private ValTextViewClick mValTextViewClick;

    public ValTextView(Context context){
        super(context);
        time = "60";
        mText = getResources().getString(R.string.login_val_button);
        mTextColor = R.drawable.val_selector;
        initView();
        initTimer();
    }

    public ValTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context,attrs);
    }

    public ValTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ValTextView);
        time = ta.getString(R.styleable.ValTextView_time);
        mText = ta.getString(R.styleable.ValTextView_android_text);
        mTextColor = ta.getColor(R.styleable.ValTextView_android_textColor, R.drawable.val_selector);
        ta.recycle();
        initView();
        initTimer();
    }

    private void initView(){
        //初始化文字
        if(StringUtils.isEmptySpace(mText)){
            mText = getResources().getString(R.string.login_val_button);
            setText(mText);
        }else {
            setText(mText);
        }
        setEnabled(true);
        //初始化颜色
        if(mTextColor == R.drawable.val_selector){
            setTextColor(getResources().getColorStateList(mTextColor));
        }else {
            setTextColor(mTextColor);
        }
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
                if(mValTextViewClick != null){
                    mValTextViewClick.click();
                }
            }
        });
    }

    private void initTimer(){
        long millisInFuture = Long.parseLong(time) * 1000;
        long countDownInterval = 1000;
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long l) {
                setEnabled(false);
                String s = "" + (l / 1000);
                setText(StringUtils.getFormatString(getResources(), R.string.login_val_button_count, s));
            }

            @Override
            public void onFinish() {
                setEnabled(true);
                setText(mText);
            }
        } ;
    }
    public interface ValTextViewClick{
        void click();
    }

    public void setValTextViewClick(ValTextViewClick mValTextViewClick){
        this.mValTextViewClick = mValTextViewClick;
    }

}
