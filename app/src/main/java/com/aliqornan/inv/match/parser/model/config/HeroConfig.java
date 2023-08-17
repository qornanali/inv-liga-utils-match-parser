package com.aliqornan.inv.match.parser.model.config;

import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Value
public class HeroConfig {

    int id;
    String localizedName;
    List<String> roles;

    public String fetchAnyRole() {
        return Optional.ofNullable(roles)
                .orElse(Collections.emptyList())
                .stream()
                .filter("Carry"::equalsIgnoreCase)
                .findAny()
                .orElse("Support");
    }
}
