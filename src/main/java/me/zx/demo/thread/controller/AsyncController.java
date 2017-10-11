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
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestOperations;

import me.zx.demo.thread.entity.Response;
import me.zx.demo.thread.service.AsyncService;

/**
 * 异步请求Controller.
 * @author zhangxin
 * @since 0.0.1
 */
@RestController
@RequestMapping(path = "/async")
public class AsyncController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncController.class);
    @Autowired
    @Qualifier("asyncRestOperations")
    private AsyncRestOperations asyncRestTemplate;

    @Autowired
    private AsyncService asyncSercie;

    /**
     * 异步请求.
     * 服务器收到请求后会马上进行返回.
     * @return Response
     */
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public Response hello() {
        String url = "http://localhost:8080/three-seconds"; //休眠5秒的服务
        ListenableFuture<ResponseEntity<Response>> future = asyncRestTemplate.getForEntity(url, Response.class);
        future.addCallback(new SuccessCallback<ResponseEntity<Response>>() {
            @Override
            public void onSuccess(final ResponseEntity<Response> arg0) {
                try {
                    asyncSercie.task1();
                } catch (final InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                LOGGER.info("SUCCESS");
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(final Throwable arg0) {
                try {
                    asyncSercie.task1();
                } catch (final InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                LOGGER.info("FALSE");
            }
        });
        return new Response(true);
    }
}
