package com.aliqornan.inv.match.parser.opendota.model;

import lombok.Value;

import java.util.List;

@Value
public class Match {

    String matchId;
    List<Player> players;
}
