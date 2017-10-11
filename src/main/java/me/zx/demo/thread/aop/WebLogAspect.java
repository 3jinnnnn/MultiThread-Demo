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
package me.zx.demo.thread.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Web层的日志切面.
 * @author zhangxin
 * @since 0.0.1
 */
@Aspect
@Component
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * me.zx.demo.thread.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 省略日志记录内容
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) throws Throwable {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE: " + JSON.toJSONString(result));
        LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}
