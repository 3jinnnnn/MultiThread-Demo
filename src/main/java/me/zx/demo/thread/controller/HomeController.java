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
package me.zx.demo.thread.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.zx.demo.thread.entity.Response;
import me.zx.demo.thread.mapper.extend.RoleMapper;
import me.zx.demo.thread.mapper.main.UserMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author zhangxin
 * @since 0.0.1
 */
@RestController
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    @Qualifier("defaultJedisPool")
    private JedisPool defaultJedisPool;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     *
     * @return Response
     */
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public Response hello() {
        Response response = new Response(true);
        Jedis jedis = defaultJedisPool.getResource();
        response.setErrorCode(String.valueOf(userMapper.count() + roleMapper.count()));
        response.setErrorMsg(jedis.dbSize().toString());
        jedis.close();
        return response;
    }

    /**
    *
    * @return Response
    */
    @RequestMapping(path = "/three-seconds", method = RequestMethod.GET)
    public Response threeSeconds() {
        try {
            Thread.sleep(Integer.parseInt("3000"));
        } catch (final InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new Response(true);
    }
}
