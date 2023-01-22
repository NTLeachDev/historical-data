package com.nleachdev.historicaldata.dao;

import com.nleachdev.historicaldata.domain.Candle;
import com.nleachdev.historicaldata.domain.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;

@Repository
public class UploadDao {

    private final NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public UploadDao(final NamedParameterJdbcTemplate namedTemplate) {
        this.namedTemplate = namedTemplate;
    }

    public int saveSeriesData(final TimeSeries series, final Collection<Candle> candles) {
        final String sql =
                " INSERT INTO HISTORICAL_DATA." + series.getTableName() +
                " (BEGIN_TIMESTAMP, OPEN, CLOSE, HIGH, LOW, VOLUME)" +
                " VALUES (:beginTimestamp, :open, :close, :high, :low, :volume)";

        return Arrays.stream(namedTemplate.batchUpdate(sql, getCandleParams(candles)))
                .filter(i -> i != 0)
                .sum();
    }

    public int deleteSeriesData(final TimeSeries series) {
        final String sql =
                " DELETE FROM HISTORICAL_DATA." + series.getTableName();

        return namedTemplate.getJdbcOperations().update(sql);
    }

    private SqlParameterSource[] getCandleParams(final Collection<Candle> candles) {
        return candles.stream()
                .map(this::getCandleParams)
                .toArray(SqlParameterSource[]::new);
    }

    private SqlParameterSource getCandleParams(final Candle candle) {
        return new MapSqlParameterSource("beginTimestamp", candle.getBeginTimestamp())
                .addValue("open", candle.getOpen())
                .addValue("close", candle.getClose())
                .addValue("high", candle.getHigh())
                .addValue("low", candle.getLow())
                .addValue("volume", candle.getVolume());
    }
}
