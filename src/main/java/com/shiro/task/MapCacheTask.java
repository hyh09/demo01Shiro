package com.shiro.task;

//import com.shiro.controller.MapCache;
import com.shiro.config.session.RetryLimitCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dell on 2020/9/23.
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class MapCacheTask {

    private int maxRetryNum = 5;


    private EhCacheManager shiroEhcacheManager;

    /**
     //     * 删除错误的登录状态
     //     */
    @Scheduled(cron = "0/5 * * * * ?")
    public  void configureTasks() {

        }








//    @Autowired
//    private MapCache mapCache;

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒

    //0 */1 * * * ?
    //@Scheduled(fixedRate=5000)
//    private void configureTasks() {
//        Map<Object, Object> map=  mapCache.getCacheMap();
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//        for (Map.Entry<Object, Object> entry : map.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
//            Date d2=new Date();
//            Date d1= (Date) entry.getValue();
//            int count= (int) (((d2.getTime() - d1.getTime()) / 1000) % 60);
//            System.out.println((((d2.getTime() - d1.getTime()) / 1000) % 60) + "分钟");
//            if(count>10){
//
//                mapCache.remove(entry.getKey());
//                System.err.println("删除锁住的用户: " +entry.getKey());
//
//            }
//        }
//
//    }



}
