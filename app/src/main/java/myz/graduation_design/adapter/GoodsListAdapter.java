package myz.graduation_design.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.GoodsListResult;
import myz.graduation_design.Model.GoodsModel;
import myz.graduation_design.R;

/**
 * Created by 10246 on 2018/4/12.
 */

public class GoodsListAdapter extends BaseQuickAdapter<GoodsListResult,BaseViewHolder> {

    Typeface iconfont;

    public GoodsListAdapter(@Nullable List<GoodsListResult> data, Typeface iconfont) {
        super(R.layout.item_goods, data);
        this.iconfont = iconfont;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsListResult item) {
        helper.setTypeface(R.id.tv_img_address_start,iconfont)
                .setTypeface(R.id.tv_img_address_end,iconfont)
                .setTypeface(R.id.tv_img_goods,iconfont)
                .setTypeface(R.id.tv_img_lanker,iconfont)
                .setTypeface(R.id.tv_img_phone,iconfont)
                .setTypeface(R.id.tv_img_date,iconfont);

        helper.setText(R.id.tv_address_start,item.startAddress)
                .setText(R.id.tv_address_end,item.endAddress)
                .setText(R.id.tv_lanker,item.receiverName)
                .setText(R.id.tv_phone,item.receiverPhone)
                .setText(R.id.tv_end_date,item.endTime);
        if(item.goodsList.size() > 0){
            helper.setText(R.id.tv_goods_name,item.goodsList.get(0).goodsName);
        }
        switch (item.state){
            case 0:
                helper.setText(R.id.tv_state,"物流公司待确认")
                        .setTextColor(R.id.tv_state, Color.parseColor("#ff9933"));
                break;
            case 1:
                helper.setText(R.id.tv_state,"等待运输")
                        .setTextColor(R.id.tv_state, Color.parseColor("#ff9933"));
                break;
            case -1:
                helper.setText(R.id.tv_state,"物流公司拒绝，请重新选择")
                .setTextColor(R.id.tv_state, Color.RED);
                break;
            case 2:
                helper.setText(R.id.tv_state,"司机接单")
                        .setTextColor(R.id.tv_state, Color.parseColor("#ff9933"));
                break;
            case -2:
                helper.setText(R.id.tv_state,"司机拒绝，请重新选择")
                        .setTextColor(R.id.tv_state, Color.RED);
                break;
            case 3:
                helper.setText(R.id.tv_state,"司机到达")
                        .setTextColor(R.id.tv_state, Color.GREEN);
                break;
            case 4:
                helper.setText(R.id.tv_state,"订单完成")
                        .setTextColor(R.id.tv_state, Color.GREEN);
                break;
                default:

        }
    }
}
