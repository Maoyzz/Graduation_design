package myz.graduation_design.weight;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import myz.graduation_design.R;
import myz.graduation_design.Utils.DensityUtils;

public class PhoneDialog extends Dialog {

    private TextView mTvDialogTitle;
    private TextView mTvDialogOk;
    private TextView mTvDialogCancel;
    private String Phone;
    private Context context;
    private callPhone mcallPhone;


    public PhoneDialog(@NonNull Context context,String Phone,callPhone mcallPhone) {
        super(context, R.style.bottomDialog);
        this.context = context;
        this.Phone = Phone;
        this.mcallPhone = mcallPhone;
        initView();
    }

    private void initView() {
        Window window = this.getWindow();
        window.setContentView(R.layout.dialog_phone);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = window.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(lp);
        int DialogPadding = (int) DensityUtils.dp2px(this.getContext(),50);
        window.getDecorView().setPadding(DialogPadding, 0, DialogPadding, 0);

        mTvDialogTitle = window.findViewById(R.id.tv_dialog_text);
        mTvDialogOk = window.findViewById(R.id.tv_ok);
        mTvDialogCancel = window.findViewById(R.id.tv_cancel);
        mTvDialogTitle.setText(Phone);
        show();
        mTvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallPhone.call(Phone);
            }
        });
        mTvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallPhone.cancel();
            }
        });
    }


    public interface callPhone{
        void call(String Phone);
        void cancel();
    }



}
