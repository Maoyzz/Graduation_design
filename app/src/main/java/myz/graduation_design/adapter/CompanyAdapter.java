package myz.graduation_design.adapter;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.CompanyModel;
import myz.graduation_design.R;
import myz.graduation_design.app.app;

/**
 * Created by 10246 on 2018/4/12.
 */

public class CompanyAdapter extends BaseQuickAdapter<CompanyModel,BaseViewHolder> {
    Typeface iconfont;
    final static int TYPE_OWN = 0;
    final static int TYPE_ADD = 1;
    int type = 0;


    public CompanyAdapter(@Nullable List<CompanyModel> data, Typeface iconfont,int type) {
        super(R.layout.item_comapny, data);
        this.iconfont = iconfont;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyModel item) {

        helper.setTypeface(R.id.tv_img_company,iconfont)
                .setText(R.id.tv_company_name,item.CompanyName)
                .setText(R.id.tv_description,item.Description)
                .setTypeface(R.id.tv_img_company_action,iconfont)
                .addOnClickListener(R.id.layout_del);
        switch (type){
            case TYPE_OWN:
                helper.setText(R.id.tv_img_company_action, R.string.icon_del)
                        .setText(R.id.tv_company_action,"删除");
                break;
            case TYPE_ADD:
                helper.setText(R.id.tv_img_company_action, R.string.icon_add)
                        .setText(R.id.tv_company_action,"添加");
                break;
            default:
                break;
        }
    }
}
