package com.bobo.security.web.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bobo
 * @Description:
 * @date 2018/7/23上午10:27
 */
public class BoboInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
    public BoboInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }
}
