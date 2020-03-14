package com.sinmem.peony.web.conf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf.security
 * @Author sinmem
 * @CreateTime 2020-02-23 02:49
 * @Description 全局定时任务
 */
@Configuration
@EnableScheduling
public class StaticScheduleTask {
    @Scheduled(cron = "0 0 4 * * ?")
    private void configureTasks(){
        logoutUser();
    }

    /**
     * 强制登出所有用户
     */
    private void logoutUser() {
    }

    private void testTask() {
        System.out.println("StaticScheduleTask Scheduled Test!");
    }
}
