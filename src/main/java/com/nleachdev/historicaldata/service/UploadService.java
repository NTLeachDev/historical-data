package com.nleachdev.historicaldata.service;

import com.nleachdev.historicaldata.dao.UploadDao;
import com.nleachdev.historicaldata.domain.Candle;
import com.nleachdev.historicaldata.domain.TimeSeries;
import com.nleachdev.historicaldata.util.StopWatchTimer;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private final ReaderService readerService;
    private final UploadDao uploadDao;
    private final StopWatchTimer stopWatch;

    @Autowired
    public UploadService(final ReaderService readerService,
                         final UploadDao uploadDao) {
        this.readerService = readerService;
        this.uploadDao = uploadDao;
        stopWatch = new StopWatchTimer("UploadService");
    }

    @Transactional
    public void uploadData() {
        stopWatch.doWithTime("Uploading Hourly Data", () -> uploadSeriesData(TimeSeries.HOURLY))
                .doWithTime("Uploading 30 Minute Data", () -> uploadSeriesData(TimeSeries.THIRTY_MIN))
                .doWithTime("Uploading 5 Minute Data", () -> uploadSeriesData(TimeSeries.FIVE_MIN))
                .doWithTime("Uploading 1 Minute Data", () -> uploadSeriesData(TimeSeries.ONE_MIN));
    }

    private void uploadSeriesData(final TimeSeries series) {
        log("Beginning upload of %s series data", series.name());
        deleteSeriesData(series);
        final List<Candle> data = readSeriesData(series);
        insertSeriesData(series, data);
    }

    private void insertSeriesData(final TimeSeries series, final List<Candle> candles) {
        final List<List<Candle>> partitioned = ListUtils.partition(candles, 50_000);

        final AtomicInteger count = new AtomicInteger(0);
        partitioned.parallelStream()
                .forEach(partition ->
                        count.addAndGet(uploadDao.saveSeriesData(series, partition))
                );

        log("Saved %,d rows for %s series data", count.get(), series.name());
    }

    private List<Candle> readSeriesData(final TimeSeries series) {
        log("Reading %s series data", series.name());
        final List<Candle> candles = readerService.getCandlesForSeries(series);
        log("Pulled %,d rows for %s series data", candles.size(), series.name());

        return candles;
    }

    private void deleteSeriesData(final TimeSeries series) {
        log("Deleting %s series data", series.name());
        final int deletedCount = uploadDao.deleteSeriesData(series);
        log("Deleted %,d rows for %s series data", deletedCount, series.name());
    }

    private void log(final String string, final Object... args) {
        logger.info(String.format(string, args));
    }
}
