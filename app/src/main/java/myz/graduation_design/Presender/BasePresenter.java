package myz.graduation_design.Presender;

import myz.graduation_design.IView.IBaseView;

/**
 * Created by 10246 on 2018/4/3.
 */

public class BasePresenter <V extends IBaseView>{
    protected V mIView;

    public BasePresenter(V iView){
        mIView = iView;
    }

    public void destroy() {
        mIView = null;
    }
}
