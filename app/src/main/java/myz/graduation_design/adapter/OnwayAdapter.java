package myz.graduation_design.adapter;

import android.graphics.Typeface;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.OnwayModel;
import myz.graduation_design.R;

public class OnwayAdapter extends BaseQuickAdapter<OnwayModel,BaseViewHolder> {

    public Typeface iconfont;

    public OnwayAdapter( @Nullable List<OnwayModel> data,Typeface iconfont) {
        super(R.layout.item_onway, data);
        this.iconfont = iconfont;
    }

    @Override
    protected void convert(BaseViewHolder helper, OnwayModel item) {
            helper.setText(R.id.tv_goods_name,"货品名称"+item.goodsName)
            .setText(R.id.tv_driver_name,"司机:"+item.driverName)
            .setText(R.id.tv_phone,item.drvierPhone)
            .setText(R.id.tv_time,item.endDate)
            .setTypeface(R.id.tv_img_phone,iconfont);
            helper.addOnClickListener(R.id.img_location)
            .addOnClickListener(R.id.tv_phone);
    }
}
