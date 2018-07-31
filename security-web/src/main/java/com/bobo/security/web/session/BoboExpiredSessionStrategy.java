/**
 * 
 */
package com.bobo.security.web.session;

import com.bobo.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 并发登录导致session失效时，默认的处理策略
 * 
 * @author bobo
 *
 */
public class BoboExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public BoboExpiredSessionStrategy(SecurityProperties securityPropertie) {
		super(securityPropertie);
	}

	/**
	 *
	 * @param event
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}

	/**
	 *
	 * @return
	 */
	@Override
	protected boolean isConcurrency() {
		return true;
	}

}
