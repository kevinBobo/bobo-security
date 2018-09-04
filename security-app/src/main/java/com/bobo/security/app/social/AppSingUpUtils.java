/**
 * 
 */
package com.bobo.security.app.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * app环境下替换providerSignInUtils，避免由于没有session导致读不到社交用户信息的问题
 * 
 * @author bobo
 *
 */
@Component
public class AppSingUpUtils {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	/**
	 * 缓存手机号到redis
	 * @param request
	 * @param phtone
	 */
	public void saveRegisterPhone(WebRequest request,String phtone){
		redisTemplate.opsForValue().set(getPhoneKey(request), phtone, 10, TimeUnit.MINUTES);
	}

	/**
	 * 获取到缓存的手机号
	 * @param request
	 * @return
	 */
	public String getRegisterPhone(WebRequest request){
		String key = getPhoneKey(request);
		if(!redisTemplate.hasKey(key)){
			throw AppSecretException.NO_BIND_PHONE;
		}
		return (String) redisTemplate.opsForValue().get(key);
	}

	public void deletePhone(WebRequest request){
		redisTemplate.delete(getPhoneKey(request));
	}

	/**
	 * 缓存社交网站用户信息到redis
	 * @param request
	 * @param connectionData
	 */
	public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
		redisTemplate.opsForValue().set(getConnectionDataKey(request), connectionData, 10, TimeUnit.MINUTES);
	}

	/**
	 * 从redis获取到保存的社交信息
	 * @param request
	 * @return
	 */
	public ConnectionData getConnectionData(WebRequest request){
		String key = getConnectionDataKey(request);
		if(!redisTemplate.hasKey(key)){
			throw AppSecretException.NO_BIND_THIRD;
		}
		return  (ConnectionData) redisTemplate.opsForValue().get(key);
	}

	/**
	 * 将缓存的社交网站用户信息与系统注册用户信息绑定
	 * @param request
	 * @param userId
	 */
	public void doPostSignUp(WebRequest request, String userId) {
		String key = getConnectionDataKey(request);
		if(!redisTemplate.hasKey(key)){
			throw AppSecretException.NO_BIND_THIRD;
		}
		ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
		Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
				.createConnection(connectionData);
		usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);


		redisTemplate.delete(key);
	}

	/**
	 * 查看用户是否有绑定
	 */
	public boolean hasConnection(String userId){
		boolean flag = false;
		MultiValueMap<String, Connection<?>> allConnections = usersConnectionRepository.createConnectionRepository(userId).findAllConnections();
		Set<Map.Entry<String, List<Connection<?>>>> entries = allConnections.entrySet();

		for (Map.Entry<String,List<Connection<?>>> entry :entries) {
			if (entry.getValue().size()>0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取redis key
	 * @param request
	 * @return
	 *
	 */
	private String getConnectionDataKey(WebRequest request) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw AppSecretException.NO_DEVICEID;
		}
		return "bobo:security:social.connect." + deviceId;
	}

	private String getPhoneKey(WebRequest request){
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw AppSecretException.NO_DEVICEID;
		}
		return "bobo:security:social.connect.phone" + deviceId;
	}


}
