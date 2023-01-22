package com.nleachdev.historicaldata.service;

import com.nleachdev.historicaldata.domain.Candle;
import com.nleachdev.historicaldata.domain.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class ReaderService {

    private final String rootPath;

    @Autowired
    public ReaderService(final String rootPath) {
        this.rootPath = rootPath;
    }

    public List<Candle> getCandlesForSeries(final TimeSeries series) {
        final String fileName = rootPath + series.getFileName() + ".txt";
        try (final Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return lines
                    .map(line -> deserializeLine(series, line))
                    .collect(toList());
        } catch (final IOException e) {
            throw new RuntimeException("Fuck", e);
        }
    }

    private static Candle deserializeLine(final TimeSeries series, final String line) {
        final String[] fields = line.split(",");

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        final LocalDateTime beginTimestamp = LocalDateTime.parse(fields[0], formatter);
        final BigDecimal open = new BigDecimal(fields[1]);
        final BigDecimal high = new BigDecimal(fields[2]);
        final BigDecimal low = new BigDecimal(fields[3]);
        final BigDecimal close = new BigDecimal(fields[4]);
        final int volume = Integer.parseInt(fields[5]);

        return new Candle(
                series, beginTimestamp, open, close, high, low, volume
        );
    }
}
