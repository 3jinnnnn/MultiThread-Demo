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
package me.zx.demo.thread.config.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 默认redis实例.
 * @author zhangxin
 * @since 0.0.1
 */
@Configuration
public class DefaultRedisConfig {
    @Primary
    @Bean(name = "defaultJedisPoolConfig")
    @ConfigurationProperties(prefix = "spring.redis.default")
    public JedisPoolConfig getJedisPoolConfig() {
        return new JedisPoolConfig();
    }

    /**
     * JedisPool.
     * @param config JedisPoolConfig
     * @param host host
     * @param port port
     * @param timeout timeout
     * @return JedisPool
     */
    @Primary
    @Bean(name = "defaultJedisPool")
    public JedisPool getJedisPool(@Qualifier("defaultJedisPoolConfig") final JedisPoolConfig config,
            @Value("${spring.redis.default.host}") final String host, @Value("${spring.redis.default.port}") final int port,
            @Value("${spring.redis.default.timeOut}") final int timeout) {
        return new JedisPool(config, host, port, timeout);
    }
}
