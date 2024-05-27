package com.kidsbank.entity;

/**
 * This entity class is to define the gift properties for "Redeem Gifts" module
 */

public class GiftInfo {

    private int giftNo;

    private String giftId;

    private String giftName;

    private String giftValue;

    private String giftStatus;

    private String modifiedTime;

    public GiftInfo(){

    }

    public GiftInfo(int giftNo, String giftId, String giftName, String giftValue, String giftStatus, String modifiedTime) {
        this.giftNo = giftNo;
        this.giftId = giftId;
        this.giftName = giftName;
        this.giftValue = giftValue;
        this.giftStatus = giftStatus;
        this.modifiedTime = modifiedTime;
    }

    public int getGiftNo() {
        return giftNo;
    }

    public void setGiftNo(int giftNo) {
        this.giftNo = giftNo;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftValue() {
        return giftValue;
    }

    public void setGiftValue(String giftValue) {
        this.giftValue = giftValue;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
