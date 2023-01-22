package com.nleachdev.historicaldata.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Candle {
    private final TimeSeries timeSeries;
    private final LocalDateTime beginTimestamp;
    private final BigDecimal open;
    private final BigDecimal close;
    private final BigDecimal high;
    private final BigDecimal low;
    private final int volume;


    public Candle(final TimeSeries timeSeries, final LocalDateTime beginTimestamp, final BigDecimal open,
                  final BigDecimal close, final BigDecimal high, final BigDecimal low, final int volume) {
        this.timeSeries = timeSeries;
        this.beginTimestamp = beginTimestamp;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public TimeSeries getTimeSeries() {
        return timeSeries;
    }

    public LocalDateTime getBeginTimestamp() {
        return beginTimestamp;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Candle candle = (Candle) o;
        return volume == candle.volume && timeSeries == candle.timeSeries
                && Objects.equals(beginTimestamp, candle.beginTimestamp)
                && Objects.equals(open, candle.open) && Objects.equals(close, candle.close)
                && Objects.equals(high, candle.high) && Objects.equals(low, candle.low);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSeries, beginTimestamp, open, close, high, low, volume);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Candle.class.getSimpleName() + "[", "]")
                .add("timeSeries=" + timeSeries)
                .add("beginTimestamp=" + beginTimestamp)
                .add("open=" + open)
                .add("close=" + close)
                .add("high=" + high)
                .add("low=" + low)
                .add("volume=" + volume)
                .toString();
    }
}
