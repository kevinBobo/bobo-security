/**
 * 
 */
package com.bobo.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.util.Map;

/**
 * @author bobo
 *
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
	
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	private String appId;
	
	private String openId;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public QQImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		
		String url = String.format(URL_GET_OPENID, accessToken);
		String responseStr = getRestTemplate().getForObject(url, String.class);

		//当错误的时候返回callback，我们转换为json
		int start = responseStr.lastIndexOf("{");
		int end = responseStr.lastIndexOf("}")+1;
		log.info(responseStr);

		String response = responseStr.substring(start, end);

		//返回错误码时直接返回空
		Map<String, Object> result = null;
		try {
			result = new ObjectMapper().readValue(response, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//返回错误码时直接返回空
		if(StringUtils.isNotBlank(MapUtils.getString(result, "error"))){
			String errcode = MapUtils.getString(result, "error");
			String errmsg = MapUtils.getString(result, "error_description");
			throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
		}


		this.openId = StringUtils.substringBetween(responseStr, "\"openid\":\"", "\"}");
	}

	/**
	 *
	 * @return
	 */
	@Override
	public QQUserInfo getUserInfo() {
		
		String url = String.format(URL_GET_USERINFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);

//		result = result.replaceAll("\n","").trim();

		log.info(result);
		
		QQUserInfo userInfo = null;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

}
