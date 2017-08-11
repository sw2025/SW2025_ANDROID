package com.benben.sw2025.tools;

/**
 * Created by 牛海丰 on 2016/07/19 0029.
 */
public class Url {

    //测试
    //private static final String IP = "http://paper.zerdream.com/api/" ;
    //正式
    private static final String IP = "http://sw.zerdream.com/api/" ;

    public static final String login = IP + "user/login" ;
    public static final String getSmsCode = IP + "user/getSmsCode" ;
    public static final String register = IP + "user/register" ;
    public static final String myEvent = IP + "event/myevent?token=%s" ;
    public static final String detailsEvent = IP + "event/myeventdetail?token=%s" ;
    public static final String eventType = IP + "eventType" ;
    public static final String myexpert = IP + "expert/myexpert?token=%s" ;
    public static final String eventApply = IP + "event/eventapply?token=%s" ;
    public static final String authMe = IP + "authMe?token=%s" ;
    public static final String mySysInfo = IP + "mySysInfo?token=%s" ;


    // 供求首页
    public static final String myNeed = IP + "need/myneed?token=%s" ;


    //头像路径
    //public static final String IconPath = SDUtil.getSDPath() + File.separator + "ziya"+ File.separator + "icon.png" ;

    //图片
    public static final String FileIP =  "http://images.ziyawang.com";
    //public static final String Rule = "http://files.ziyawang.com/law.html" ;


}
