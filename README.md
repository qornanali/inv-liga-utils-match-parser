# Match Parser for Invicible Dota 2 Lobay

[![build](https://github.com/qornanali/inv-liga-utils-match-parser/actions/workflows/build.yml/badge.svg)](https://github.com/qornanali/inv-liga-utils-match-parser/actions/workflows/build.yml)

It takes match-id as argument and write out a CSV!

To register more users, add them to [db_player.json](app/src/main/resources/db_player.json)

## Prequisites

JDK 11

## How to build

```
./gradlew clean build
```

## How to test

```
./gradlew test
```

## How to run

```
OPENDOTA_BASE_URL=https://api.opendota.com/ \
 PLAYER_STAT_REPORT_PATH="/tmp/" \
 APP_ENVIRONMENT=production \
 java -jar app/build/libs/app-all.jar <MATCH_ID>
```

Example match ID: `7287818324`

It will generate the report in `/tmp/playerstat.csv`
