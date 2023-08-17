package com.aliqornan.inv.match.parser;

import com.aliqornan.inv.match.parser.error.UpstreamFailedException;
import com.aliqornan.inv.match.parser.model.PlayerStat;
import com.aliqornan.inv.match.parser.opendota.OpenDotaClient;
import com.aliqornan.inv.match.parser.opendota.model.Match;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MatchParser {

    private final OpenDotaClient openDotaClient;
    private final PlayerNameEnricher playerNameEnricher;

    public List<PlayerStat> parsePlayerStats(String matchId)
            throws IOException, UpstreamFailedException {
        Call<Match> apiCall = openDotaClient.getMatchDetail(matchId);

        Response<Match> result = apiCall.execute();

        Match responseBody = result.body();
        if (result.isSuccessful()) {
            return mapMatchIntoPlayerStats(responseBody);
        }

        Map<String, String> errorMetadata = Map.of("http_status", String.valueOf(result.code()));
        throw new UpstreamFailedException(errorMetadata);
    }

    private List<PlayerStat> mapMatchIntoPlayerStats(Match match) {
        return Optional.ofNullable(match)
                .map(Match::getPlayers)
                .orElse(Collections.emptyList())
                .stream()
                .map(PlayerStat::createFrom)
                .map(playerNameEnricher::enrichPlayerStat)
                .collect(Collectors.toList());
    }
}
