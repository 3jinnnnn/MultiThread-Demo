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

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestOperations;

import me.zx.demo.thread.entity.Response;

/**
 *
 * @author zhangxin
 * @since 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestHomeController {
    @Autowired
    private RestOperations restOperations;
    //    @TestConfiguration
    //    static class Config{
    //        @Bean
    //        public static RestOperations restOperations() {
    //            return mock
    //        }
    //    }

    @Test
    public void test() {
            ResponseEntity<Response> response = restOperations.getForEntity("http://localhost:8080/hello",
                    Response.class);
            Assert.assertTrue(response.getBody().isSuccess());
    }
}
