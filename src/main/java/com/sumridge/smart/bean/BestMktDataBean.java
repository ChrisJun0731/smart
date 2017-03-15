package com.sumridge.smart.bean;

/**
 * Created by liu on 17/3/6.
 */
public class BestMktDataBean {

    private String cusip;

    private double bidPrice;

    private int bidQty;

    private int bidSourceId;

    private double askPrice;

    private int askQty;

    private int askSourceId;

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getBidQty() {
        return bidQty;
    }

    public void setBidQty(int bidQty) {
        this.bidQty = bidQty;
    }

    public int getBidSourceId() {
        return bidSourceId;
    }

    public void setBidSourceId(int bidSourceId) {
        this.bidSourceId = bidSourceId;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public int getAskQty() {
        return askQty;
    }

    public void setAskQty(int askQty) {
        this.askQty = askQty;
    }

    public int getAskSourceId() {
        return askSourceId;
    }

    public void setAskSourceId(int askSourceId) {
        this.askSourceId = askSourceId;
    }
}
