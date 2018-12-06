package myz.graduation_design.IView;

public interface IAddressView extends IBaseView{
    void success();
    void error();
    void getListSuccess(Object object);
    void getListError();
}
