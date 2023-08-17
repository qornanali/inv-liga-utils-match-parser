package com.aliqornan.inv.match.parser.opendota;

import com.aliqornan.inv.match.parser.common.gson.OffsetDatetimeTypeAdapter;
import com.aliqornan.inv.match.parser.common.okhttp.OkHttpClientFactory;
import com.gojek.ApplicationConfiguration;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.OffsetDateTime;

public class OpenDotaClientFactory {

    private static final String CLIENT_NAME = "OPENDOTA";

    public static OpenDotaClient create(ApplicationConfiguration configuration) {
        OkHttpClient httpClient = OkHttpClientFactory.build(configuration, CLIENT_NAME);
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(configuration.getValueAsString(String.format("%s_%s", CLIENT_NAME, "BASE_URL")))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        return retrofit.create(OpenDotaClient.class);
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDatetimeTypeAdapter())
                .create();
    }
}
