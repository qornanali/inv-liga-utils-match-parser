package com.aliqornan.inv.match.parser;

import com.aliqornan.inv.match.parser.common.FileContentReader;
import com.aliqornan.inv.match.parser.model.PlayerStat;
import com.aliqornan.inv.match.parser.model.config.PlayerConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerNameEnricher {

    private static final String FILE_CONFIG_URL = "/db_player.json";
    private final List<PlayerConfig> playerConfigs;
    private Gson gson;

    public PlayerNameEnricher(Gson gson) throws IOException {
        this.playerConfigs = loadConfig(gson);
    }

    private List<PlayerConfig> loadConfig(Gson gson) throws IOException {
        URL configUrl = getClass().getResource(FILE_CONFIG_URL);
        String configValue = FileContentReader.read(configUrl);
        return gson.fromJson(configValue, new TypeToken<List<PlayerConfig>>() {
        }.getType());
    }

    public PlayerStat enrichPlayerStat(PlayerStat playerStat) {
        return playerStat.toBuilder()
                .name(findNameFromConfig(playerStat.getAccountId()))
                .build();
    }

    private String findNameFromConfig(String accountId) {
        return Optional.ofNullable(playerConfigs)
                .orElse(Collections.emptyList())
                .stream()
                .filter(config -> config.getAccountIds().stream().anyMatch(id -> id.equals(accountId)))
                .findFirst()
                .map(PlayerConfig::getName)
                .orElse("UNKNOWN-" + accountId);
    }
}
