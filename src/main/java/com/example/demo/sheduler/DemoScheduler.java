package com.example.demo.sheduler;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class DemoScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DemoScheduler.class);

    @Scheduled(cron = "0 */1 * * * *")
    @SchedulerLock(name = "EveryMinuteScheduler", lockAtMostFor = "PT30S", lockAtLeastFor = "PT30S")
    public void everyMinuteScheduler() {
        LockAssert.assertLocked();
        logger.info("EveryMinuteScheduler executed");
    }

    @Scheduled(cron = "0 0 */1 * * *")
    @SchedulerLock(name = "EveryHourScheduler", lockAtMostFor = "PT30m", lockAtLeastFor = "PT30m")
    public void everyHourScheduler() {
        LockAssert.assertLocked();
        logger.info("EveryHourScheduler executed");
    }

    @Scheduled(cron = "0 0 0 */1 * *")
    @SchedulerLock(name = "EveryDayScheduler", lockAtMostFor = "PT30m", lockAtLeastFor = "PT30m")
    public void everyDayScheduler() {
        LockAssert.assertLocked();
        logger.info("EveryDayScheduler executed");
    }
}
