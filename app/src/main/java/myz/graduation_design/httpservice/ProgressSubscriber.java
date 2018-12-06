package myz.graduation_design.httpservice;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import rx.Subscriber;

/**
 * Created by 10246 on 2018/4/16.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;
    private SwipeRefreshLayout mSwLayout;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context){
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mSwLayout = new SwipeRefreshLayout(context);
    }

    @Override
    public void onStart() {
        mSwLayout.setRefreshing(true);
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("mao", "onError: "+e );
        mSubscriberOnNextListener.onError(401,"网络请求失败");
        mSwLayout.setRefreshing(false);
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
            mSwLayout.setRefreshing(false);
        }
    }

    public interface SubscriberOnNextListener<T>{
        void onNext(T t);
        void onError(int code, String message);
    }
}
