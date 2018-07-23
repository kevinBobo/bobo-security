package com.bobo.security.core.properties;

import lombok.Data;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:41
 */
@Data
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();
}
