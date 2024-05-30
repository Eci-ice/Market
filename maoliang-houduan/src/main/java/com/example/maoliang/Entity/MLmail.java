package com.example.maoliang.Entity;
//
//public class MLmail {
//
//    private int id;
//    private String location;
//    private String trackingNumber;
//    private int step;
//    private int orderid;
//
//    // Getters and Setters
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getTrackingNumber() {
//        return trackingNumber;
//    }
//
//    public void setTrackingNumber(String trackingNumber) {
//        this.trackingNumber = trackingNumber;
//    }
//
//    public int getStep() {
//        return step;
//    }
//
//    public void setStep(int step) {
//        this.step = step;
//    }
//
//    public int getOrderid() {
//        return orderid;
//    }
//
//    public void setOrderid(int orderid) {
//        this.orderid = orderid;
//    }
//}
import java.sql.Timestamp;

public class MLmail {
    private int orderid;
    private int step;
    private String location;
    private String trackingNumber;
    private Timestamp createtime;

    // Constructors, getters and setters

    public MLmail() {}

    public MLmail(int orderid, int step, String location, String trackingNumber, Timestamp createtime) {
        this.orderid = orderid;
        this.step = step;
        this.location = location;
        this.trackingNumber = trackingNumber;
        this.createtime = createtime;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
