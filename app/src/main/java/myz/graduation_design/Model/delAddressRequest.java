package myz.graduation_design.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class delAddressRequest implements KvmSerializable {
    //    public String Sender;
    public int id;
    public String username;
    public String addressInfo;
    public String phone;
    public Integer userId;


    @Override
    public Object getProperty(int index) {

        switch (index) {
            case 0:
                return id;
            case 1:
                return username;
            case 2:
                return addressInfo;
            case 3:
                return phone;
            case 4:
                return userId;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                id = Integer.parseInt(value.toString());
                break;
            case 1:
                username = value.toString();
                break;
            case 2:
                addressInfo = value.toString();
                break;
            case 3:
                phone = value.toString();
                break;
            case 4:
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
                info.name = "id";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "username";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "addressInfo";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "phone";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "userId";
                break;
            default:
                break;
        }
    }
}
