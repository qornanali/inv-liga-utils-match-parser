package com.aliqornan.inv.match.parser.model;

import com.aliqornan.inv.match.parser.opendota.model.Player;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder(toBuilder = true)
public class PlayerStat {

    OffsetDateTime dateTime;
    String accountId;
    String name;
    boolean isWinner;
    int kills;
    int deaths;
    int assists;

    public static PlayerStat createFrom(Player player) {
        boolean isWinner = determineWinnerStatus(player.isRadiant(), player.isRadiantWin());
        return PlayerStat.builder()
                .dateTime(player.getStartTime())
                .accountId(player.getAccountId())
                .isWinner(isWinner)
                .kills(player.getKills())
                .deaths(player.getDeaths())
                .assists(player.getAssists())
                .build();
    }

    private static boolean determineWinnerStatus(boolean isPlayerARadiant, boolean isRadiantWin) {
        if (isPlayerARadiant) {
            return isRadiantWin;
        }

        return !isRadiantWin;
    }
}
