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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author zhangxin
 * @since 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestAsyncService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestAsyncService.class);
    @Autowired
    private AsyncService sercie;

    @Test
    public void test() throws InterruptedException, ExecutionException {
        Future<String> task1 = sercie.task1();
        Future<String> task2 = sercie.task2();
        while (true) {
            if (task1.isDone() && task2.isDone()) {
                LOGGER.info("Task1 result: {}", task1.get());
                LOGGER.info("Task2 result: {}", task2.get());
                break;
            }
            Thread.sleep(1000);
        }
        LOGGER.info("All tasks finished.");
    }
}
