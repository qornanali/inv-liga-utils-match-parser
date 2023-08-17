package com.aliqornan.inv.match.parser.model.config;

import lombok.Value;

import java.util.List;

@Value
public class PlayerConfig {

    String name;
    List<String> accountIds;
}
