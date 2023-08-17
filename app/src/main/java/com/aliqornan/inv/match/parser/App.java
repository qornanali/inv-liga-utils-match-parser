package com.aliqornan.inv.match.parser;

import com.aliqornan.inv.match.parser.common.gson.GsonFactory;
import com.aliqornan.inv.match.parser.model.PlayerStat;
import com.aliqornan.inv.match.parser.opendota.OpenDotaClient;
import com.aliqornan.inv.match.parser.opendota.OpenDotaClientFactory;
import com.gojek.ApplicationConfiguration;
import com.gojek.Figaro;
import com.google.gson.Gson;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class App {

    public static void main(String[] args) {
        try {
            ApplicationConfiguration configuration = Figaro.configure(Collections.emptySet());
            Gson gson = GsonFactory.create();
            OpenDotaClient openDotaClient = OpenDotaClientFactory.create(configuration);
            CsvGenerator csvGenerator = new CsvGenerator();
            PlayerNameEnricher playerNameEnricher = new PlayerNameEnricher(gson);
            MatchParser matchParser = new MatchParser(openDotaClient, playerNameEnricher);
            PlayerStatReportGenerator playerStatReportGenerator = new PlayerStatReportGenerator(csvGenerator, configuration);

            /* SAMPLE: 7281893156 */
            String matchId = args[0];

            System.out.println("Parsing match " + matchId);
            List<PlayerStat> parsedStats = matchParser.parsePlayerStats(matchId);
            System.out.println("Successfully parsed with result " + parsedStats);

            System.out.println("Generating CSV");
            File reportResult = playerStatReportGenerator.generate(parsedStats);
            System.out.println("Successfully generated CSV with result " + reportResult.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
