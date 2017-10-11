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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author zhangxin
 * @since 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties="application.properties")
public class TestDefaultRedisConfig {
    @Autowired
    @Qualifier("defaultJedisPool")
    private JedisPool defaultJedisPool;

    @Autowired
    @Qualifier("extendJedisPool")
    private JedisPool extendJedisPool;

    @Test
    public void defaultTest() {
        Jedis jedis = defaultJedisPool.getResource();
        Assert.assertNotEquals(Long.MAX_VALUE, jedis.dbSize().longValue());
        jedis.close();
    }

}
