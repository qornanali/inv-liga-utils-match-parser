package com.aliqornan.inv.match.parser.opendota.model;

import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class Player {

    String matchId;
    String accountId;
    String personaName;
    int kills;
    int deaths;
    int assists;
    boolean radiantWin;
    boolean isRadiant;
    int heroId;
    OffsetDateTime startTime;
}
