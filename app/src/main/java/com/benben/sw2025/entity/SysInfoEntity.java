package com.benben.sw2025.entity;

/**
 * Created by 牛海丰 on 2017/8/10.
 */

public class SysInfoEntity {

    private String sendid ;
    private String sendtime ;
    private String title ;
    private String content ;
    private String state ;

    public SysInfoEntity(){}

    public SysInfoEntity(String sendid, String sendtime, String title, String content, String state) {
        super();
        this.sendid = sendid;
        this.sendtime = sendtime;
        this.title = title;
        this.content = content;
        this.state = state;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
