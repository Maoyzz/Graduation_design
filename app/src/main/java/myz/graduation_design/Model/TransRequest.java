package myz.graduation_design.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class TransRequest implements KvmSerializable {

    public int carrierId;
    public String carrierName;
    public String carrierPhone;
    public int driverId;
    public String driverName;
    public String driverPhone;
    public String endAddress;
    public String endTime;
    public String goodsList;
    public int goodsMoney;
    public String goodsName;
    public int moneyState;
    public String receiverName;
    public String receiverPhone;
    public String remark;
    public int reward;
    public int sendId;
    public String sendName;
    public String sendPhone;
    public String startAddress;
    public String startTime;
    public int state;
    public String lat;
    public String lng;




    @Override
    public Object getProperty(int index) {

        switch (index) {
            case 0:
                return carrierId;
            case 1:
                return carrierName;
            case 2:
                return carrierPhone;
            case 3:
                return driverId;
            case 4:
                return driverName;
            case 5:
                return driverPhone;
            case 6:
                return endAddress;
            case 7:
                return endTime;
            case 8:
                return goodsList;
            case 9:
                return goodsMoney;
            case 10:
                return goodsName;
            case 11:
                return moneyState;
            case 12:
                return receiverName;
            case 13:
                return receiverPhone;
            case 14:
                return remark;
            case 15:
                return reward;
            case 16:
                return sendId;
            case 17:
                return sendName;
            case 18:
                return sendPhone;
            case 19:
                return startAddress;
            case 20:
                return startTime;
            case 21:
                return state;
            case 22:
                return lat;
            case 23:
                return lng;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 24;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                carrierId = Integer.parseInt(value.toString());
                break;
            case 1:
                carrierName = value.toString();
                break;
            case 2:
                carrierPhone = value.toString();
                break;
            case 3:
                driverId = Integer.parseInt(value.toString());
                break;
            case 4:
                driverName = value.toString();
                break;
            case 5:
                driverPhone = value.toString();
                break;
            case 6:
                endAddress = value.toString();
                break;
            case 7:
                endTime = value.toString();
                break;
            case 8:
                goodsList = value.toString();
                break;
            case 9:
                goodsMoney = Integer.parseInt(value.toString());
                break;
            case 10:
                goodsName = value.toString();
                break;
            case 11:
                moneyState = Integer.parseInt(value.toString());
                break;
            case 12:
                receiverName = value.toString();
                break;
            case 13:
                receiverPhone = value.toString();
                break;
            case 14:
                remark = value.toString();
                break;
            case 15:
                reward = Integer.parseInt(value.toString());
                break;
            case 16:
                sendId = Integer.parseInt(value.toString());
                break;
            case 17:
                sendName = value.toString();
                break;
            case 18:
                sendPhone = value.toString();
                break;
            case 19:
                startAddress = value.toString();
                break;
            case 20:
                startTime = value.toString();
                break;
            case 21:
                state = Integer.parseInt(value.toString());
                break;
            case 22:
                lat = value.toString();
                break;
            case 23:
                lng = value.toString();
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
                info.name = "carrierId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "carrierName";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "carrierPhone";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "driverId";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "driverName";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "driverPhone";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "endAddress";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "endTime";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "goodsList";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "goodsMoney";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "goodsName";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "moneyState";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "receiverName";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "receiverPhone";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "remark";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "reward";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "sendId";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "sendName";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "sendPhone";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "startAddress";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "startTime";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "state";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "lat";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "lng";
                break;
            default:
                break;
        }
    }
}
