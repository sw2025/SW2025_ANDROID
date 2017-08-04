package com.benben.sw2025.entity;

import android.support.annotation.NonNull;

/**
 * Created by 牛海丰 on 2017/7/19.
 */

public class HomeEventEntity implements Comparable<HomeEventEntity>{

    private String eventid ;
    private String userid ;
    private String domain1 ;
    private String domain2 ;
    private String brief ;
    private String eventtime ;
    private String created_at ;
    private String updated_at ;
    private String id ;
    private String configid ;
    private String verifytime ;
    private String remark ;
    private String is_deal ;
    private String name ;

    public HomeEventEntity(){}

    public HomeEventEntity(String eventid, String userid, String domain1, String domain2, String brief, String eventtime, String created_at, String updated_at, String id, String configid, String verifytime, String remark, String is_deal, String name) {
        super();
        this.eventid = eventid;
        this.userid = userid;
        this.domain1 = domain1;
        this.domain2 = domain2;
        this.brief = brief;
        this.eventtime = eventtime;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id = id;
        this.configid = configid;
        this.verifytime = verifytime;
        this.remark = remark;
        this.is_deal = is_deal;
        this.name = name;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDomain1() {
        return domain1;
    }

    public void setDomain1(String domain1) {
        this.domain1 = domain1;
    }

    public String getDomain2() {
        return domain2;
    }

    public void setDomain2(String domain2) {
        this.domain2 = domain2;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigid() {
        return configid;
    }

    public void setConfigid(String configid) {
        this.configid = configid;
    }

    public String getVerifytime() {
        return verifytime;
    }

    public void setVerifytime(String verifytime) {
        this.verifytime = verifytime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIs_deal() {
        return is_deal;
    }

    public void setIs_deal(String is_deal) {
        this.is_deal = is_deal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull HomeEventEntity o) {
        int i = Integer.parseInt(this.getConfigid()) - Integer.parseInt(o.getConfigid()) ;
        return i;
    }
}
