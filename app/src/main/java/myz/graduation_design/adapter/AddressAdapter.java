package myz.graduation_design.adapter;

import android.graphics.Typeface;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.AddressModel;
import myz.graduation_design.Model.AddressResult;
import myz.graduation_design.R;

/**
 * Created by 10246 on 2018/4/11.
 */

public class AddressAdapter extends BaseQuickAdapter<AddressResult,BaseViewHolder> {

    private Typeface iconfont;

    public AddressAdapter(@Nullable List<AddressResult> data, Typeface iconfont) {
        super(R.layout.item_address, data);
        this.iconfont = iconfont;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressResult item) {
        helper.setText(R.id.tv_address,item.addressInfo)
                .setText(R.id.tv_address_receiver,item.username)
                .setTypeface(R.id.tv_img_receiver,iconfont)
                .setTypeface(R.id.tv_img_address,iconfont)
                .setTypeface(R.id.tv_img_del,iconfont)
                .setTypeface(R.id.tv_img_edit,iconfont);

        helper.addOnClickListener(R.id.layout_edit)
                .addOnClickListener(R.id.layout_del);
    }
}
