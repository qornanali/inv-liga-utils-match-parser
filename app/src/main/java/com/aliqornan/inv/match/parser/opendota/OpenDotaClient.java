package com.aliqornan.inv.match.parser.opendota;

import com.aliqornan.inv.match.parser.opendota.model.Match;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenDotaClient {

    @GET("/api/matches/{match_id}")
    Call<Match> getMatchDetail(@Path("match_id") String matchId);
}
