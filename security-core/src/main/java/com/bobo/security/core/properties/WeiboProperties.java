package com.bobo.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/3下午1:20
 */
@Setter
@Getter
public class WeiboProperties extends SocialProperties {

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weibo。
     */
    private String providerId = "weibo";
}
