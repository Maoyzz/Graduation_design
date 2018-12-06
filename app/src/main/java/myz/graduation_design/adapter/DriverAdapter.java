package myz.graduation_design.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.DriverModel;
import myz.graduation_design.R;

/**
 * Created by 10246 on 2018/4/13.
 */

public class DriverAdapter extends BaseQuickAdapter<DriverModel,BaseViewHolder> {
    final static int TYPE_DRIVER_OWN = 0;
    final static int TYPE_DRIVER_ADD = 1;
    private int type;

    public DriverAdapter( @Nullable List<DriverModel> data,int type) {
        super(R.layout.item_driver, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, DriverModel item) {
        helper.setText(R.id.tv_driver_name,item.DriverName)
                .setText(R.id.tv_phone,item.DriverPhone)
        .addOnClickListener(R.id.img_add);
        switch (type){
            case TYPE_DRIVER_OWN:
                helper.setImageResource(R.id.img_add,R.mipmap.ic_launcher);
                break;
            case TYPE_DRIVER_ADD:
                helper.setImageResource(R.id.img_add,R.mipmap.icon_add);
                break;
        }
    }
}
