/**
 * 
 */
package com.bobo.security.core.social;

import com.bobo.security.core.properties.SecurityProperties;
import com.bobo.security.core.social.support.BoboSpringSocialConfigurer;
import com.bobo.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置主类
 * 
 * @author bobo
 *
 */
@Configuration
@EnableSocial
@Order(1)
@Slf4j
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;
	
	@Autowired(required = false)
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	/* (non-Javadoc)
	 * @see org.springframework.social.config.annotation.SocialConfigurerAdapter#getUsersConnectionRepository(org.springframework.social.connect.ConnectionFactoryLocator)
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
//		repository.setTablePrefix("bobo_");

        if(connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		return repository;
	}
	
	/**
	 * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
	 * @return
	 */
	@Bean
	public SpringSocialConfigurer boboSocialSecurityConfig() {
        //社交登录用的表
        String createUserSql = "create table UserConnection (userId varchar(128) not null," +
                "providerId varchar(128) not null," +
                "providerUserId varchar(128)," +
                "rank int not null," +
                "displayName varchar(255)," +
                "profileUrl varchar(512)," +
                "imageUrl varchar(512)," +
                "accessToken varchar(512) not null," +
                "secret varchar(512)," +
                "refreshToken varchar(512)," +
                "expireTime bigint," +
                "primary key (userId, providerId, providerUserId))";
        String createIndex = "create unique index UserConnectionRank on UserConnection(userId, providerId, rank);";
        try {
            JdbcTemplate template = new JdbcTemplate(dataSource);
            template.execute(createUserSql);
            log.info("UserConnection created successed");
            template.execute(createIndex);
            log.info("UserConnection inedx created successed");
        }catch (BadSqlGrammarException e){
//            e.printStackTrace();
            log.error("UserConnection table already created or created error");
        }
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		BoboSpringSocialConfigurer configurer = new BoboSpringSocialConfigurer(filterProcessesUrl);
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
		return configurer;
	}

	/**
	 * 用来处理注册流程的工具类
	 * 
	 * @param connectionFactoryLocator
	 * @return
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator)) {
		};
	}
}
