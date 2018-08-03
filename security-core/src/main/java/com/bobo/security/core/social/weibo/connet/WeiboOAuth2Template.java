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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
        RestTemplate restTemplate = new RestTemplate(
                ClientHttpRequestFactorySelector.getRequestFactory());
        HttpMessageConverter<?> messageConverter = new FormHttpMessageConverter() {

            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                return true;
            }

            @Override
            public MultiValueMap<String, String> read(
                    Class<? extends MultiValueMap<String, ?>> clazz,
                    HttpInputMessage inputMessage) throws IOException,
                    HttpMessageNotReadableException {

                TypeReference<Map<String, ?>> mapType = new TypeReference<Map<String, ?>>() {
                };
                LinkedHashMap<String, ?> readValue = objectMapper.readValue(
                        inputMessage.getBody(), mapType);
                LinkedMultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>();
                for (Entry<String, ?> currentEntry : readValue.entrySet()) {
                    result.add(currentEntry.getKey(), currentEntry.getValue()
                            .toString());
                }
                return result;
            }
        };

        restTemplate.setMessageConverters(Collections
                .<HttpMessageConverter<?>>singletonList(messageConverter));
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

        if (log.isDebugEnabled()) {
            log.debug("url = " + url);
        }
        MultiValueMap<String, String> response = getRestTemplate()
                .postForObject(url, null, MultiValueMap.class);
        String expires = response.getFirst("expires_in");
        String accessToken = response.getFirst("access_token");
        if (log.isDebugEnabled()) {
            log.debug("access token value = " + accessToken);
        }
        return new AccessGrant(accessToken, null, null,
                expires != null ? Long.valueOf(expires) : null);
    }

}
