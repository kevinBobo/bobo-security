/*
 * Copyright 2011 France Telecom R&D Beijing Co., Ltd 北京法国电信研发中心有限公司
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bobo.security.core.social.weibo.connet;


import com.bobo.security.core.social.weibo.api.Weibo;
import com.bobo.security.core.social.weibo.api.WeiboImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * Twitter ServiceProvider implementation that exposes the Twitter 4j API
 * binding.
 * 
 * @author Craig Walls
 */
public final class WeiboServiceProvider extends
		AbstractOAuth2ServiceProvider<Weibo> {

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