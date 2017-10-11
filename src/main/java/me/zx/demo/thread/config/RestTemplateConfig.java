/*
 * Copyright © 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zx.demo.thread.config;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Spring RestTemplate Config.
 * @author zhangxin
 * @since 0.0.1
 */
@Configuration
public class RestTemplateConfig {
    private static final int TIMEOUT = 5000;
    /**
     * SimpleClientHttpRequestFactory实现方式.<br />
     * 默认:<br />
     * ConnectTimeout: 5000(达到该值仍未连接上远程主机,则抛出异常)<br />
     * ReadTimeout: 5000(达到该值主机仍未返回结果,则抛出异常)<br />
     * @return ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(TIMEOUT);
        requestFactory.setConnectTimeout(TIMEOUT);
        return requestFactory;
    }

    /**
     * RestTemplate Bean.
     * 锡膏默认的编码方式为UTF-8
     * @param factory AsyncClientHttpRequestFactory
     * @return AsyncRestOperations
     */
    @Bean
    @ConditionalOnMissingBean({ RestOperations.class, RestTemplate.class })
    public RestOperations restOperations(
            final @Qualifier("clientHttpRequestFactory") ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);

        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
        }
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }
}
