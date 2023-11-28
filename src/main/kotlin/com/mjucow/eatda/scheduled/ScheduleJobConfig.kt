package com.mjucow.eatda.scheduled

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableScheduling
class ScheduleJobConfig {
//    @Bean
//    fun taskScheduler(): TaskScheduler {
//        val threadPoolTaskScheduler = ThreadPoolTaskScheduler().apply {
//            poolSize = 5
//            setThreadNamePrefix("ThreadPoolTaskScheduler")
//        }
//        return threadPoolTaskScheduler
//    }
}
