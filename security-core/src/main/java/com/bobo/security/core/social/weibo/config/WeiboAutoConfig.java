package com.bobo.security.core.social.weibo.config;

import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.properties.WeiboProperties;
import com.bobo.security.core.social.weibo.connet.WeiboConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/3下午2:47
 */
@Configuration
@ConditionalOnProperty(prefix = "bobo.security.social.weibo", name = "app-id")
public class WeiboAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiboProperties weibo = securityProperties.getSocial().getWeibo();
        return new WeiboConnectionFactory(weibo.getProviderId(),weibo.getAppId(),weibo.getAppSecret());
    }
}
