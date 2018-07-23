package com.bobo.security.core.social.qq.config;

import com.bobo.security.core.properties.QQProperties;
import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.social.qq.connet.QQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/22下午9:39
 */
@Configuration
@ConditionalOnProperty(prefix = "bobo.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	private SecurityProperties securityProperties;

	public QQAutoConfig(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
	 * #createConnectionFactory()
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqConfig = securityProperties.getSocial().getQq();
		return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}

}
