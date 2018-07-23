package com.bobo.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:40
 */
@Data
public class QQProperties extends SocialProperties {

    private String providerId = "qq";
}
