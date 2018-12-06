package myz.graduation_design.Soap;


import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 10246 on 2018/4/16.
 */

public class SoapRequet{
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            //SOAP REQUEST
            // webservice服务地址
            String url= "http://192.168.0.107:8080/SSPWS/SystemWSPort?wsdl";
            //web服务的命名空间
            String namespace="http://ws.ssp.honest.com/";
            //请求服务的方法名称
            String methodName="addUser";
            //String methodName="findUser";
            //soap请求地址
            String soapActionAddress = "http://WebXml.com.cn/getMobileCodeInfo";



            HttpTransportSE transport = new HttpTransportSE(url);
            SoapObject soapObject = new SoapObject(namespace,methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //soapObject.addProperty("username", "lyt");
            envelope.bodyOut = soapObject;
            envelope.setOutputSoapObject(soapObject);
            try {
                transport.call(namespace + methodName, envelope);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("lyt", "call: observer" + e.getMessage());
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Log.e("lyt", "call: observer" + e.getMessage());
            }
            SoapObject object = (SoapObject) envelope.bodyIn;
            Log.e("mao", "call: "+ (object == null));
            subscriber.onNext(object.toString());
            subscriber.onCompleted();
        }
    });

}
