package com.bobo.security.core.social.weibo.connet;


import com.bobo.security.core.social.weibo.api.Weibo;
import com.bobo.security.core.social.weibo.api.WeiboImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 波波
 */
public final class WeiboServiceProvider extends AbstractOAuth2ServiceProvider<Weibo> {

	private static final String URL_AUTHORIZE = "https://api.weibo.com/oauth2/authorize";

	private static final String URL_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token";

	public WeiboServiceProvider(String appId, String appSecret) {
		super(new WeiboOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
	}

	@Override
	public Weibo getApi(String accessToken) {
		return new WeiboImpl(accessToken);
	}

}