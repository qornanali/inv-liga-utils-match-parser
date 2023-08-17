package com.aliqornan.inv.match.parser;

import com.aliqornan.inv.match.parser.model.PlayerStat;
import com.gojek.ApplicationConfiguration;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class PlayerStatReportGenerator {

    private static final String FILE_NAME = "playerstat.csv";
    private final CsvGenerator csvGenerator;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ApplicationConfiguration configuration;

    public File generate(List<PlayerStat> playerStats) throws IOException {
        String filePath = configuration.getValueAsString("PLAYER_STAT_REPORT_PATH") + FILE_NAME;
        return csvGenerator.generate(filePath, write(playerStats));
    }

    private Consumer<CSVWriter> write(List<PlayerStat> playerStats) {
        return csvWriter -> {
            csvWriter.writeNext(getHeader());
            playerStats.forEach(playerStat -> {
                String[] row = getRow(playerStat);
                csvWriter.writeNext(row);
            });
        };
    }

    private String[] getRow(PlayerStat playerStat) {
        ZonedDateTime timeAtWib = playerStat.getDateTime().atZoneSameInstant(ZoneId.of("Asia/Jakarta"));
        String gameStatus = playerStat.isWinner() ? "W" : "L";
        return new String[]{
                dateTimeFormatter.format(timeAtWib),
                "",
                playerStat.getName(),
                gameStatus,
                String.valueOf(playerStat.getKills()),
                String.valueOf(playerStat.getDeaths()),
                String.valueOf(playerStat.getAssists()),
                playerStat.getRole(),
                playerStat.getHeroName(),
                playerStat.getMatchId()
        };
    }

    private String[] getHeader() {
        return new String[]{
                "Date",
                "GAME NUM",
                "PLAYER NAME",
                "GAME STATUS",
                "Kill",
                "Death",
                "Assist",
                "Role",
                "Hero",
                "Match ID"
        };
    }

}
