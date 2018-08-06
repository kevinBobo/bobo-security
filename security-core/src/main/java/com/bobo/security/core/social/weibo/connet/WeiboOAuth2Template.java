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

import com.bobo.security.core.social.weibo.handler.WeiboResponseErrorHandler;
import com.bobo.security.core.social.weixin.connect.WeixinAccessGrant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
public class WeiboOAuth2Template extends OAuth2Template {


    public WeiboOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret,
                authorizeUrl,
                accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));


        restTemplate.setErrorHandler(new WeiboResponseErrorHandler());

        return restTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AccessGrant postForAccessGrant(String accessTokenUrl,
                                             MultiValueMap<String, String> parameters) {
        List<String> params = new ArrayList<>();
        for (String key : parameters.keySet()) {
            params.add(key + "=" + parameters.getFirst(key));
        }

        String url = accessTokenUrl + "?" + params.stream().collect(Collectors.joining("&"));


        log.info("开始请求微博accesstoken:"+url);

        String response = getRestTemplate()
                .postForObject(url, null, String.class);

        log.info(response);

        //返回错误码时直接返回空
        Map<String, Object> result = null;
        try {
            result = new ObjectMapper().readValue(response, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回错误码时直接返回空
        if(StringUtils.isNotBlank(MapUtils.getString(result, "error_code"))){
            String errcode = MapUtils.getString(result, "error_code");
            String errmsg = MapUtils.getString(result, "error");
            throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
        }

        String token = MapUtils.getString(result, "access_token");

        Long expires = MapUtils.getLong(result, "expires_in");

        AccessGrant accessToken = new AccessGrant(
                token,
                null,
                null,
                expires != null ? Long.valueOf(expires) : null);

        return accessToken;
    }

}
