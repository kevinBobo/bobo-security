package com.bobo.security.web.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:26
 */
public class BoboExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public BoboExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }


    /* (non-Javadoc)
     * @see org.springframework.security.web.session.SessionInformationExpiredStrategy#onExpiredSessionDetected(org.springframework.security.web.session.SessionInformationExpiredEvent)
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /* (non-Javadoc)
     * @see com.bobo.security.browser.session.AbstractSessionStrategy#isConcurrency()
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
