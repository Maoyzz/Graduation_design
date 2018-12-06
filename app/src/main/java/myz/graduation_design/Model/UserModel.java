package myz.graduation_design.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by 10246 on 2018/4/16.
 */

public class UserModel implements KvmSerializable {
    public String username;
    public String password;
    public Integer role;
    public String userPhone;

    @Override
    public Object getProperty(int index) {

        switch (index) {
            case 0:
                return username;
            case 1:
                return password;
            case 2:
                return role;
            case 3:
                return userPhone;
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
                password = value.toString();
                break;
            case 2:
                role = Integer.parseInt(value.toString()); ;
                break;
            case 3:
                userPhone = value.toString();
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
                info.name = "password";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "role";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "userPhone";
                break;
            default:
                break;
        }
    }
}
