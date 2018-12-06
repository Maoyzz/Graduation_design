package myz.graduation_design.adapter;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import myz.graduation_design.Model.MessageModel;
import myz.graduation_design.Model.newsModel;
import myz.graduation_design.R;
import myz.graduation_design.Utils.StringUtils;

/**
 * Created by 10246 on 2018/4/8.
 */

public class newsAdapter extends BaseQuickAdapter<MessageModel, BaseViewHolder> {

    Resources resources;
    private Typeface iconfont;

    public newsAdapter( @Nullable List<MessageModel> data,Resources resources,Typeface iconfont) {
        super(R.layout.item_news, data);
        this.resources = resources;
        this.iconfont = iconfont;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel item) {
        helper.setText(R.id.tv_content,item.messageTitle)
                .setText(R.id.tv_sender, resources.getString(R.string.icon_talk)+"系统消息")
                .setText(R.id.tv_date,resources.getString(R.string.icon_time)+"  "+ StringUtils.getFormatString(resources,R.string.news_date,item.messageTime))
                .setTypeface(R.id.tv_sender, iconfont)
                .setTypeface(R.id.tv_date,iconfont);

    }
}
