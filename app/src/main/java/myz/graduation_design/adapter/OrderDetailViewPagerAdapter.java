package myz.graduation_design.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 10246 on 2018/4/11.
 */

public class OrderDetailViewPagerAdapter extends PagerAdapter {

    private List<View> list;
    private String[] titles;

    public OrderDetailViewPagerAdapter(List<View> list, String[] titles) {
        this.list = list;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("lyt", "instantiateItem: position:" + position);
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    /* make Refresh work?
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    */
}