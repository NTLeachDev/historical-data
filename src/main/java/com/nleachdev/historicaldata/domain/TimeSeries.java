package com.nleachdev.historicaldata.domain;

public enum TimeSeries {
    HOURLY("ES_continuous_UNadjusted_1hour", "HOURLY_SERIES"),
    THIRTY_MIN("ES_continuous_UNadjusted_30min", "THIRTY_MIN_SERIES"),
    FIVE_MIN("ES_continuous_UNadjusted_5min", "FIVE_MIN_SERIES"),
    ONE_MIN("ES_continuous_UNadjusted_1min", "ONE_MIN_SERIES"),
    ;

    private final String fileName;
    private final String tableName;

    TimeSeries(final String fileName, final String tableName) {
        this.fileName = fileName;
        this.tableName = tableName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTableName() {
        return tableName;
    }
}
