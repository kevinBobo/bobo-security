package com.bobo.security.web.support;

import lombok.Data;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:24
 */
@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;
}
