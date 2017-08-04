package com.benben.sw2025.entity;

/**
 * Created by 牛海丰 on 2017/8/3.
 */

public class HomeSelectExpertEntity {

    private String expertid ;
    private String expertname ;
    private String showimage ;

    public HomeSelectExpertEntity(){}

    public HomeSelectExpertEntity(String expertid, String expertname, String showimage) {
        super();
        this.expertid = expertid;
        this.expertname = expertname;
        this.showimage = showimage;
    }

    public String getExpertid() {
        return expertid;
    }

    public void setExpertid(String expertid) {
        this.expertid = expertid;
    }

    public String getExpertname() {
        return expertname;
    }

    public void setExpertname(String expertname) {
        this.expertname = expertname;
    }

    public String getShowimage() {
        return showimage;
    }

    public void setShowimage(String showimage) {
        this.showimage = showimage;
    }
}
