package myz.graduation_design.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by 10246 on 2018/4/11.
 */

public class AddressModel implements Serializable,KvmSerializable{
    //    public String Sender;
    public String username;
    public String addressInfo;
    public String phone;
    public Integer userId;
    public String Remark;
    public int id;


    @Override
    public Object getProperty(int index) {

        switch (index) {
            case 0:
                return username;
            case 1:
                return addressInfo;
            case 2:
                return phone;
            case 3:
                return userId;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                username = value.toString();
                break;
            case 1:
                addressInfo = value.toString();
                break;
            case 2:
                phone = value.toString();
                break;
            case 3:
                userId = Integer.parseInt(value.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "username";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "addressInfo";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "phone";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "userId";
                break;
            default:
                break;
        }
    }
}
