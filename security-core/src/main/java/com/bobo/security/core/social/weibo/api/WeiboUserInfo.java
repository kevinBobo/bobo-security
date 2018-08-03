package com.bobo.security.core.social.weibo.api;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/3上午10:54
 */
@Data
public class WeiboUserInfo {

    /**
     * 用户uid
     */
    private BigInteger uid;

    /**
     * 字符串型的用户uid
     */
    private String idstr;

    /**
     * 用户昵称
     */
    private String screen_name;

    /**
     * 友好显示名称
     */
    private String name;

    /**
     * 用户所在省级ID
     */
    private int province;

    /**
     * 用户所在城市ID
     */
    private  int city;

    /**
     * 用户所在地
     */
    private String location;

    /**
     * 用户个人描述
     */
    private String description;

    /**
     * 用户博客地址
     */
    private String url;

    /**
     * 用户头像地址（中图），50×50像素
     */
    private String profile_image_url;

    /**
     * 用户的微博统一URL地址
     */
    private String profile_url;

    /**
     * 用户的个性化域名
     */
    private String domain;

    /**
     * 用户的微号
     */
    private String weihao;

    /**
     * 性别，m：男、f：女、n：未知
     */
    private String gender;

    /**
     * 粉丝数
     */
    private int followers_count;

    /**
     * 关注数
     */
    private int friends_count;

    /**
     * 微博数
     */
    private int statuses_count;

    /**
     * 收藏数
     */
    private int favourites_count;

    /**
     * 用户创建（注册）时间
     */
    private String created_at;

    /**
     * 暂未支持
     */
    private boolean following;

    /**
     * 是否允许所有人给我发私信，true：是，false：否
     */
    private boolean allow_all_act_msg;

    /**
     * 是否允许标识用户的地理位置，true：是，false：否
     */
    private boolean geo_enabled;

    /**
     * 是否是微博认证用户，即加V用户，true：是，false：否
     */
    private boolean verified;

    /**
     * 暂未支持
     */
    private int verified_type;

    /**
     * 用户备注信息，只有在查询用户关系时才返回此字段
     */
    private String remark;

    /**
     * 用户的最近一条微博信息字段 详细
     */
    private Object status;

    /**
     * 是否允许所有人对我的微博进行评论，true：是，false：否
     */
    private boolean allow_all_comment;

    /**
     * 用户头像地址（大图），180×180像素
     */
    private String avatar_large;

    /**
     * 用户头像地址（高清），高清头像原图
     */
    private String avatar_hd;

    /**
     * 认证原因
     */
    private String verified_reason;

    /**
     * 该用户是否关注当前登录用户，true：是，false：否
     */
    private boolean follow_me;

    /**
     * 用户的在线状态，0：不在线、1：在线
     */
    private int online_status;

    /**
     * 用户的互粉数
     */
    private int bi_followers_count;

    /**
     * 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
     */
    private String lang;
}
