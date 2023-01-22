package com.nleachdev.historicaldata.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class StopWatchTimer {
    private static final Logger logger = LoggerFactory.getLogger(StopWatchTimer.class);
    private final StopWatch stopWatch;

    public StopWatchTimer(final String name) {
        stopWatch = new StopWatch(name);
    }

    public StopWatchTimer doWithTime(final String processName, final Runnable process) {
        stopWatch.start(processName);
        process.run();
        stopWatch.stop();
        logger.info("{} took {} seconds\n\n\n", processName, stopWatch.getLastTaskTimeMillis() / 1000);
        return this;
    }
}
