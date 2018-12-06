package myz.graduation_design.Soap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import myz.graduation_design.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 10246 on 2018/4/16.
 */

public class BaseRequest {
    //String url= "http://192.168.0.178:8080/LogsticsWS/UserWSPort?wsdl";
    String url= "http://192.168.0.178:8080/LogsticsWS/AddressWSPort?wsdl";
    //web服务的命名空间
    String namespace="http://webservice.logstics.suny.com/";
    //请求服务的方法名称
    //String methodName="register";
    //String methodName="login";
    //soap请求地址
    //String soapActionAddress = "http://WebXml.com.cn/getMobileCodeInfo";

//example
//    UserModel user = new UserModel();
//        user.setProperty(0,UserName);
//        user.setProperty(1,Pwd);
//        user.setProperty(2,1);
//    String url= "http://192.168.0.178:8080/LogsticsWS/UserWSPort?wsdl";

    private Context context;
    private ProgressDialog mpd;




    public BaseRequest(Context context){
        this.context = context;
        if(context instanceof Activity){
            mpd = new ProgressDialog(context);
            mpd.setMessage("正在加载中，请稍等......");
            mpd.show();
        }

    }

    public void setPdDismiss(Boolean dismiss){
        if(dismiss){
            mpd.dismiss();
        }
    }

    /**
     * 外部调用进行网络请求
     * @param url
     * @param methodName
     * @param object
     * @param getResult
     */
    public void startRequest(final String url,final String methodName, final Object object, final getResult getResult){
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                HttpTransportSE transport = new HttpTransportSE(url);
                SoapObject soapObject = new SoapObject(namespace,methodName);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                PropertyInfo objekt = new PropertyInfo();
                objekt.setName("arg0");
                objekt.setValue(object);
                objekt.setType(object.getClass());
                soapObject.addProperty(objekt);
                envelope.bodyOut = soapObject;
                envelope.setOutputSoapObject(soapObject);
                envelope.addMapping(namespace, object.getClass()+"", object.getClass());
//                Log.e("mao", "call: "+ soapObject.getProperty(2)+"object.getClass()"+object.getClass()+"");
                try {
                    transport.call(namespace + methodName, envelope);
                } catch (IOException e) {
                    mpd.dismiss();
                    e.printStackTrace();
                    getResult.error("服务器请求失败");
                    Log.e("lyt", "call: observer" + e.getMessage());
                } catch (XmlPullParserException e) {
                    mpd.dismiss();
                    e.printStackTrace();
                    getResult.error("服务器请求失败");
                    Log.e("lyt", "call: observer" + e.getMessage());
                }
                SoapObject object = (SoapObject) envelope.bodyIn;
                Log.e("mao", "call: " + (object == null));
                if(object == null){
                    subscriber.onNext("服务器请求失败");
                }else {
                    String result = object.getProperty(0).toString();
                    subscriber.onNext(result.toString());
                }
                subscriber.onCompleted();
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        try {
                            if(string.equals("服务器请求失败")){
                                getResult.error(string);
                                Toast.makeText(context, string,Toast.LENGTH_SHORT).show();
                            }else {
                                getResult.success(string);
                            }
                        }catch (Exception e){
                            Log.e("mao", "call: "+string );
                            getResult.error(string);
                        }
                        mpd.dismiss();

                        Log.e("lyt", "call: subscriber" + string);
                    }
                });
    }

    /**
     * 外部调用进行网络请求
     * @param url
     * @param methodName
     * @param objects
     * @param getResult
     */
    public void startRequest(final String url,final String methodName, final Object[] objects, final getResult getResult){
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    HttpTransportSE transport = new HttpTransportSE(url);
                    SoapObject soapObject = new SoapObject(namespace,methodName);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    for(int i = 0; i < objects.length; i++){
                        soapObject.addProperty("arg"+i,objects[i]);
                    }
                    envelope.bodyOut = soapObject;
                    envelope.setOutputSoapObject(soapObject);
                    try {
                        transport.call(namespace + methodName, envelope);
                    } catch (IOException e) {
                        e.printStackTrace();
                        mpd.dismiss();
                        getResult.error("服务器请求失败");
                        Log.e("lyt", "call: observer" + e.getMessage());
                    } catch (XmlPullParserException e) {
                        mpd.dismiss();
                        e.printStackTrace();
                        getResult.error("服务器请求失败");
                        Log.e("lyt", "call: observer" + e.getMessage());
                    }
                    SoapObject object = (SoapObject) envelope.bodyIn;
                    Log.e("mao", "call: " + (envelope.bodyIn == null));
                    if(envelope.bodyIn == null){
                        subscriber.onNext("服务器请求失败");
                    }else {
                        String result = object.getProperty(0).toString();
                        subscriber.onNext(result);
                    }
                    subscriber.onCompleted();
                }catch (Exception e){
                    Log.e("mao", "call: "+e );
                    mpd.dismiss();
                }

            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        mpd.dismiss();
                        try {
                            if(string.equals("服务器请求失败")){
                                getResult.error(string);
                                Toast.makeText(context, string,Toast.LENGTH_SHORT).show();
                            }else {
                                getResult.success(string);
                            }
                        }catch (Exception e){
                            getResult.error(string);
                            Log.e("mao", "call: "+string );
                        }

                        Log.e("lyt", "call: subscriber" + string);
                    }
                });
    }



    public interface getResult{
        void success(String string);
        void error(String string);
    }

}
