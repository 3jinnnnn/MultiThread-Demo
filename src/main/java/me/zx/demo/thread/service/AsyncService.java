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
package me.zx.demo.thread.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 * 同步服务.
 * @author zhangxin
 * @since 0.0.1
 */
@Service
public class AsyncService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);
    private static final long SLEEP_TIME = 5000;
    /**
     * 同步任务mySimpleAsync.
     * @return Future
     * @throws InterruptedException exception
     */
    @Async("mySimpleAsync")
    public Future<String> task1() throws InterruptedException {
        LOGGER.info("进入task1,当前线程{}", Thread.currentThread().getName());
        Thread.sleep(SLEEP_TIME);
        LOGGER.info("退出task1,当前线程{}", Thread.currentThread().getName());
        return new AsyncResult<String>("task1 has compalted!");
    }

    /**
     * 同步任务myAsync.
     * @return Future
     * @throws InterruptedException exception
     */
    @Async("myAsync")
    public Future<String> task2() throws InterruptedException {
        LOGGER.info("进入task2,当前线程{}", Thread.currentThread().getName());
        Thread.sleep(SLEEP_TIME);
        LOGGER.info("退出task2,当前线程{}", Thread.currentThread().getName());
        return new AsyncResult<String>("task2 has compalted!");
    }
}
