package com.aliqornan.inv.match.parser;

import com.aliqornan.inv.match.parser.common.FileContentReader;
import com.aliqornan.inv.match.parser.model.PlayerStat;
import com.aliqornan.inv.match.parser.model.config.HeroConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChosenHeroEnricher {

    private static final String FILE_CONFIG_URL = "/db_heroes.json";
    private final List<HeroConfig> heroConfigs;

    public ChosenHeroEnricher(Gson gson) throws IOException {
        this.heroConfigs = loadConfig(gson);
    }

    private List<HeroConfig> loadConfig(Gson gson) throws IOException {
        URL configUrl = getClass().getResource(FILE_CONFIG_URL);
        String configValue = FileContentReader.read(configUrl);
        return gson.fromJson(configValue, new TypeToken<List<HeroConfig>>() {
        }.getType());
    }

    public PlayerStat enrichPlayerStat(PlayerStat playerStat) {
        return playerStat.toBuilder()
                .heroName(findNameFromConfig(playerStat.getHeroId()))
                .role(fetchRoleFromConfig(playerStat.getHeroId()))
                .build();
    }

    private String findNameFromConfig(int heroId) {
        return Optional.ofNullable(heroConfigs)
                .orElse(Collections.emptyList())
                .stream()
                .filter(config -> config.getId() == heroId)
                .findFirst()
                .map(HeroConfig::getLocalizedName)
                .orElse("UNKNOWN-" + heroId);
    }

    private String fetchRoleFromConfig(int heroId) {
        return Optional.ofNullable(heroConfigs)
                .orElse(Collections.emptyList())
                .stream()
                .filter(config -> config.getId() == heroId)
                .findFirst()
                .map(HeroConfig::fetchAnyRole)
                .orElse("UNKNOWN");
    }
}
