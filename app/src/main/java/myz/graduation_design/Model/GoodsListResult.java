package myz.graduation_design.Model;

import java.io.Serializable;
import java.util.List;

public class GoodsListResult implements Serializable{
    public int sendId;
    public List<GoodsRequest> goodsList;
//    public String goodsList;
    public String remark;
    public String carrierPhone;
    public String receiverPhone;
    public String carrierName;
    public String startTime;
    public int id;
    public int state;
    public String goodsName;
    public String lat;
    public String reward;
    public String lng;
    public String sendName;
    public String receiverName;
    public String startAddress;
    public int goodsMoney;
    public int driverId;
    public String sendPhone;
    public String driverName;
    public String driverPhone;
    public String endTime;
    public int moneyState;
    public int carrierId;
    public String endAddress;
}