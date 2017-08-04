package com.benben.sw2025.entity;

import java.util.List;

/**
 * Created by 牛海丰 on 2017/8/3.
 */

public class EventTypeEntity {

    private String domainid ;
    private String domainname ;
    private String level ;
    private String parentid ;
    private String created_at ;
    private String updated_at ;
    private List<benben> benben ;

    public EventTypeEntity(){

    }

    public EventTypeEntity(String domainid, String domainname, String level, String parentid, String created_at, String updated_at) {
        super();
        this.domainid = domainid;
        this.domainname = domainname;
        this.level = level;
        this.parentid = parentid;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getDomainid() {
        return domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    public String getDomainname() {
        return domainname;
    }

    public void setDomainname(String domainname) {
        this.domainname = domainname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
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

    public List<com.benben.sw2025.entity.benben> getBenben() {
        return benben;
    }

    public void setBenben(List<com.benben.sw2025.entity.benben> benben) {
        this.benben = benben;
    }
}
