package myz.graduation_design.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import myz.graduation_design.Model.MessageModel;
import myz.graduation_design.Model.newsModel;
import myz.graduation_design.Presender.TabFragmentListener;
import myz.graduation_design.R;
import myz.graduation_design.Soap.BaseRequest;
import myz.graduation_design.Utils.ImageUtils;
import myz.graduation_design.Utils.NewsResource;
import myz.graduation_design.Utils.SharedPreferenceUtils;
import myz.graduation_design.Utils.StringUtils;
import myz.graduation_design.adapter.newsAdapter;

/**
 * Created by 10246 on 2018/4/3.
 */

public class NewsFragment extends Fragment implements View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnBannerListener{


    private TabFragmentListener fragmentListener;
    //private UserLoader mUserLoader;

    private TextView mTvNewsAll;
    private TextView mTvNewsOwn;
    private RecyclerView mRvNews;
    private TextView mTvHeaderRight;
    private Banner banner;
    private LinearLayout mLayoutNews;

    //Todo fake model
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    private TextView[] TextViewList;
    private ImageView[] ImageViewList;

    private List<MessageModel> messageModelList = new ArrayList<>();

    //
    newsAdapter adapter;
    String NewsUrl;
    NewsResource news;



    public void setTabFragmentListener(TabFragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public static NewsFragment newInstance(TabFragmentListener listener) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setTabFragmentListener(listener);
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_m, container, false);
//        mUserLoader = new UserLoader();
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        initHeader(view);
        mRvNews = (RecyclerView)view.findViewById(R.id.Rv_news);
        mTvNewsAll = (TextView)view.findViewById(R.id.tv_news_title_all);
        mTvNewsOwn = (TextView)view.findViewById(R.id.tv_news_title_own);
        news = new NewsResource();

        StringUtils.setNewsText(view,R.id.tv_new1,news.newsTitle1);
        StringUtils.setNewsText(view,R.id.tv_new2,news.newsTitle2);
        StringUtils.setNewsText(view,R.id.tv_new3,news.newsTitle3);
        StringUtils.setNewsText(view,R.id.tv_new4,news.newsTitle4);
        StringUtils.setNewsText(view,R.id.tv_new5,news.newsTitle5);
        StringUtils.setNewsText(view,R.id.tv_new6,news.newsTitle6);
        StringUtils.setNewsText(view,R.id.tv_new7,news.newsTitle7);
        StringUtils.setNewsText(view,R.id.tv_new8,news.newsTitle8);
        StringUtils.setNewsText(view,R.id.tv_new9,news.newsTitle9);
        StringUtils.setNewsText(view,R.id.tv_new10,news.newsTitle10);

        ImageUtils.setNewsImage(getContext(),view,R.id.img_new1,news.newsImage1);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new2,news.newsImage2);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new3,news.newsImage3);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new4,news.newsImage4);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new5,news.newsImage5);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new6,news.newsImage6);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new7,news.newsImage7);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new8,news.newsImage8);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new9,news.newsImage9);
        ImageUtils.setNewsImage(getContext(),view,R.id.img_new10,news.newsImage10);

        view.findViewById(R.id.layout_news1).setOnClickListener(this);
        view.findViewById(R.id.layout_news2).setOnClickListener(this);
        view.findViewById(R.id.layout_news3).setOnClickListener(this);
        view.findViewById(R.id.layout_news4).setOnClickListener(this);
        view.findViewById(R.id.layout_news5).setOnClickListener(this);
        view.findViewById(R.id.layout_news6).setOnClickListener(this);
        view.findViewById(R.id.layout_news7).setOnClickListener(this);
        view.findViewById(R.id.layout_news8).setOnClickListener(this);
        view.findViewById(R.id.layout_news9).setOnClickListener(this);
        view.findViewById(R.id.layout_news10).setOnClickListener(this);





        banner = (Banner) view.findViewById(R.id.banner);
        mLayoutNews = (LinearLayout)view.findViewById(R.id.layout_news) ;
        mTvNewsAll.setOnClickListener(this);
        mTvNewsOwn.setOnClickListener(this);
        LinearLayoutManager linearLayoutManger = new LinearLayoutManager(getActivity());
        mRvNews.setLayoutManager(linearLayoutManger);
        adapter = new newsAdapter(new ArrayList<MessageModel>() {},getResources(),((MainActivity)getActivity()).iconfont);
        adapter.setOnItemClickListener(this);
        mRvNews.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);



        //banner
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add("http://www.cn156.com/data/attachment/block/cb/cb50bf46f37a408290543a5a00d37590.jpg");
        list_path.add("http://www.cn156.com/data/attachment/block/ac/ac30da13c06a6c6b78db520dec5a610b.jpg");
        list_path.add("http://www.cn156.com/data/attachment/block/2c/2c52489bb9b3cd0d4584fcf1d3fa2d9e.jpg");
        list_path.add("http://www.cn156.com/data/attachment/block/c4/c413809c302fe1a7b8b8acee6869c9c3.jpg");
        list_title.add("普洛斯集团与海航集团签署战略合作协议");
        list_title.add("百度外卖、饿了么在海淀区投放百万外卖封签");
        list_title.add("UPS追加14架747-8F型货机订单");
        list_title.add("中物联代管协会召开党风廉政建设和反腐败工");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
        banner.setOnBannerListener(this);


    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    private void initHeader(View view){
        TextView mTvHeaderLeft = (TextView)view.findViewById(R.id.tv_header_left);
        TextView mTvHeaderCenter = (TextView)view.findViewById(R.id.tv_header_center);
        mTvHeaderRight = (TextView)view.findViewById(R.id.tv_header_right);

        mTvHeaderCenter.setText("新闻列表");
        mTvHeaderLeft.setVisibility(View.GONE);
        mTvHeaderRight.setTypeface(((MainActivity)getActivity()).iconfont);
    }

    private void initData(){
//        model1.title = "中国进世界杯了";
//        model1.content = "11111213131313131\n3\n1\n3\n1\n3\n1\n3" +
//                "a\ns\nd\na\ns\nd\na\nd\ns\nad\ns\na\nd\na\nd\na\nd\nd\nd\na\nsda\ndada\nsda\nda\nd\na";
//        model1.date = "4.3";
//        model1.sender = "刘";
//        model2.title = "中国男篮无敌了";
//        model2.content = "1111121313131313131313134324";
//        model2.date = "4.4";
//        model2.sender = "毛";
//        model3.title = "湖人总冠军";
//        model3.content = "111112131313131313131313";
//        model3.date = "4.3";
//        model3.sender = "陈";
//        model4.title = "我就是看球";
//        model4.content = "111112131313131313131313";
//        model4.date = "4.2";
//        model4.sender = "宋";
//        list1.add(model1);
//        list1.add(model2);
//        list2.add(model3);
//        list2.add(model4);
        getMessageList();
    }

    @Override
    public void onClick(View view) {
        if(view == mTvNewsAll ){
            mTvNewsOwn.setTextColor(getResources().getColor(R.color.white));
            mTvNewsAll.setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvNewsOwn.setBackgroundResource(R.color.colorPrimary);
            mTvNewsAll.setBackgroundResource(R.color.white);
            mRvNews.setVisibility(View.GONE);
            mLayoutNews.setVisibility(View.VISIBLE);
        }else if(view == mTvNewsOwn){
            mTvNewsAll.setTextColor(getResources().getColor(R.color.white));
            mTvNewsOwn.setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvNewsAll.setBackgroundResource(R.color.colorPrimary);
            mTvNewsOwn.setBackgroundResource(R.color.white);
            mRvNews.setVisibility(View.VISIBLE);
            mLayoutNews.setVisibility(View.GONE);
            getMessageList();
        }else if(view == mTvHeaderRight){

        }

        Uri uri = null;
        switch (view.getId()){
            case R.id.layout_news1:
                uri = Uri.parse(news.newsUri1);
                break;
            case R.id.layout_news2:
                uri = Uri.parse(news.newsUri2);
                break;
            case R.id.layout_news3:
                uri = Uri.parse(news.newsUri3);
                break;
            case R.id.layout_news4:
                uri = Uri.parse(news.newsUri4);
                break;
            case R.id.layout_news5:
                uri = Uri.parse(news.newsUri5);
                break;
            case R.id.layout_news6:
                uri = Uri.parse(news.newsUri6);
                break;
            case R.id.layout_news7:
                uri = Uri.parse(news.newsUri7);
                break;
            case R.id.layout_news8:
                uri = Uri.parse(news.newsUri8);
                break;
            case R.id.layout_news9:
                uri = Uri.parse(news.newsUri9);
                break;
            case R.id.layout_news10:
                uri = Uri.parse(news.newsUri10);
                break;
        }
        if(uri != null){
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Log.e("mao", "onItemClick: " );
        MessageModel model = (MessageModel)adapter.getData().get(position);
        Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
        intent.putExtra("model",model);
        startActivity(intent);
    }

    @Override
    public void OnBannerClick(int position) {
//        Intent intent = new Intent(getActivity(), RouteActivity.class);
        switch (position){
            case 0:
                NewsUrl = "http://www.cn156.com/article-89255-1.html";
                break;
            case 1:
                NewsUrl = "http://www.cn156.com/article-88900-1.html";
                break;
            case 2:
                NewsUrl = "http://www.cn156.com/article-88891-1.html";
                break;
            case 3:
                NewsUrl = "http://www.cn156.com/article-88894-1.html";
                break;
        }
        Uri uri = Uri.parse(NewsUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);


//        intent.putExtra("url",NewsUrl);
//        startActivity(intent);
    }

    private void getMessageList(){
        BaseRequest request = new BaseRequest(getContext());
        String url = StringUtils.BaseUrl+"/LogsticsWS/MessageWSPort?wsdl";
        int id = Integer.parseInt(SharedPreferenceUtils.getString(getContext(),SharedPreferenceUtils.USERID_KEY,""));
        Object[] objects = {id};
        request.startRequest(url, "getMessage", objects, new BaseRequest.getResult() {
            @Override
            public void success(String string) {
                Gson gson = new Gson();
                List<MessageModel> result = gson.fromJson(string, new TypeToken<List<MessageModel>>(){}.getType());
                adapter.setNewData(result);
            }

            @Override
            public void error(String string) {

            }
        });
    }
}
