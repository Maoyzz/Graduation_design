package myz.graduation_design.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.AccountModel;
import myz.graduation_design.R;

/**
 * Created by Mao on 2018/4/15.
 */

public class AccountAdapter extends BaseQuickAdapter<AccountModel,BaseViewHolder> {
    public AccountAdapter( @Nullable List<AccountModel> data) {
        super(R.layout.item_account, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountModel item) {
        helper.setText(R.id.tv_date,item.Time)
        .setText(R.id.tv_title,item.title)
        .setText(R.id.tv_content,item.Content)
        .setText(R.id.tv_state,item.state);
    }
}
