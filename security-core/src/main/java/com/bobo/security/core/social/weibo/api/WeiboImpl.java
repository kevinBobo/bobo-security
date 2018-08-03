package com.bobo.security.core.social.weibo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author bobo
 * @Description:
 * @date 2018/8/3上午10:54
 */
@Slf4j
public class WeiboImpl  extends AbstractOAuth2ApiBinding implements Weibo  {

    private static final String URL_GET_USERINFO = "https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s";

    private static final String URL_GET_UID = "https://api.weibo.com/2/account/get_uid.json?access_token=%s";


    private String uid;

    private String accessToken;


    private ObjectMapper objectMapper = new ObjectMapper();

    public WeiboImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.accessToken = accessToken;

        String url = String.format(URL_GET_UID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        this.uid = StringUtils.substringBetween(result, "\"uid\":\"", "\"}");
    }

    /**
     *
     * @return
     */
    @Override
    public WeiboUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO, accessToken, uid);

        String result = getRestTemplate().getForObject(url, String.class);

        log.info(result);

        WeiboUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, WeiboUserInfo.class);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
