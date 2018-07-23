package com.bobo.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**y
 * @author bobo
 * @Description:
 * @date 2018/7/22下午9:29
 */
@Data
@ConfigurationProperties(prefix = "bobo.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();
}
