package com.bobo.security.core.social.weixin.api;

/**
 * @author bobo
 * @Description: 微信api调用接口
 * @date 2018/7/23上午10:41
 */
public interface Weixin {

	/* (non-Javadoc)
	 * @see com.ymt.pz365.framework.security.social.api.SocialUserProfileService#getUserProfile(java.lang.String)
	 */
	WeixinUserInfo getUserInfo(String openId);
	
}
