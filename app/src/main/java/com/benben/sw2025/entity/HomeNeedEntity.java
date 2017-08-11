package com.benben.sw2025.entity;

/**
 * Created by 牛海丰 on 2017/8/8.
 */

public class HomeNeedEntity {
    private String needid ;
    private String userid ;
    private String domain1 ;
    private String domain2 ;
    private String brief ;
    private String needtime ;
    private String needtype ;
    private String created_at ;
    private String updated_at ;
    private String looks ;
    private String id ;
    private String configid ;
    private String verifytime ;
    private String remark ;
    private String name ;

    public HomeNeedEntity() {
    }

    public HomeNeedEntity(String needid, String userid, String domain1, String domain2, String brief, String needtime, String needtype, String created_at, String updated_at, String looks, String id, String configid, String verifytime, String remark, String name) {
        super();
        this.needid = needid;
        this.userid = userid;
        this.domain1 = domain1;
        this.domain2 = domain2;
        this.brief = brief;
        this.needtime = needtime;
        this.needtype = needtype;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.looks = looks;
        this.id = id;
        this.configid = configid;
        this.verifytime = verifytime;
        this.remark = remark;
        this.name = name;
    }

    public String getNeedid() {
        return needid;
    }

    public void setNeedid(String needid) {
        this.needid = needid;
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

    public String getNeedtime() {
        return needtime;
    }

    public void setNeedtime(String needtime) {
        this.needtime = needtime;
    }

    public String getNeedtype() {
        return needtype;
    }

    public void setNeedtype(String needtype) {
        this.needtype = needtype;
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

    public String getLooks() {
        return looks;
    }

    public void setLooks(String looks) {
        this.looks = looks;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
