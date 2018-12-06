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

public class CheckDialog extends Dialog {

    private TextView mTvDialogTitle;
    private TextView mTvDialogOk;
    private TextView mTvDialogCancel;
    private Context context;
    private DialogListener mDialogListener;

    public CheckDialog(@NonNull Context context) {
        super(context, R.style.bottomDialog);
        this.context = context;
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
        show();
        mTvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDialogListener!= null){
                    mDialogListener.clickOk();
                }
            }
        });
        mTvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDialogListener!= null){
                    mDialogListener.clickCancel();
                }
            }
        });
    }

    public void setDialogTitle(String title){
        mTvDialogTitle.setText(title);
    }

    public interface DialogListener{
        void clickOk();
        void clickCancel();
    }

    public void setDialogListener(DialogListener mDialogListener){
        this.mDialogListener = mDialogListener;
    }
}
