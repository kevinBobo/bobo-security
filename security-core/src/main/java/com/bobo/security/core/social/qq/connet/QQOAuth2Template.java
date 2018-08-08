/**
 * 
 */
package com.bobo.security.core.social.qq.connet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author bobo
 *
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.oauth2.OAuth2Template#postForAccessGrant(java.lang.String, org.springframework.util.MultiValueMap)
	 */
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

//		log.info("开始获取accessToken:"+accessTokenUrl);
//
//		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
//
//		log.info("获取accessToken的响应："+responseStr);
//
//		//当错误的时候返回callback，我们转换为json
//		int start = responseStr.lastIndexOf("{");
//		int end = responseStr.lastIndexOf("}")+1;
//
//		String response = responseStr.substring(start, end);
//
//		//返回错误码时直接返回空
//		Map<String, Object> result = null;
//		try {
//			result = new ObjectMapper().readValue(response, Map.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		//返回错误码时直接返回空
//		if(StringUtils.isNotBlank(MapUtils.getString(result, "error"))){
//			String errcode = MapUtils.getString(result, "error");
//			String errmsg = MapUtils.getString(result, "error_description");
//			throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
//		}
//
//		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
//
//		String accessToken = StringUtils.substringAfterLast(items[0], "=");
//		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
//		String refreshToken = StringUtils.substringAfterLast(items[2], "=");

		String accessToken = parameters.getFirst("code");
		
		return new AccessGrant(accessToken, null, null, null);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.social.oauth2.OAuth2Template#createRestTemplate()
	 */
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

}
