package myz.graduation_design.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class addUserRelationshipRequest implements KvmSerializable{

    public int carrierId;
    public int userId;


    @Override
    public Object getProperty(int index) {
        switch (index){
            case 0:
                return carrierId;
            case 1:
                return userId;

        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index){
            case 0:
                carrierId = Integer.parseInt(value.toString());
                break;
            case 1:
                userId = Integer.parseInt(value.toString());
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "carrierId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "userId";
                break;
        }
    }
}
