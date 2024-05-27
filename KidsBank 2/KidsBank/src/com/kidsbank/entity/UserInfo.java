package com.kidsbank.entity;

public class UserInfo {
    private static UserInfo instance;

    private String userId;

    private String role;

    private String linkId;

    private UserInfo(){}  // 私有构造函数，确保单例模式

    public static UserInfo getInstance(){
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }

    public void setUserInfo(String userId, String role, String linkId){
        this.userId = userId;
        this.role = role;
        this.linkId = linkId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String userParentId) {
        this.linkId = userParentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void clearUserInfo() {
        this.userId = null;
        this.role = null;
        this.linkId = null;
    }
}
