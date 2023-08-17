package com.aliqornan.inv.match.parser.common.okhttp;

import com.gojek.ApplicationConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

public class OkHttpClientFactory {

    public static OkHttpClient build(ApplicationConfiguration config, String prefix) {
        long readTimeout = config.getValueAsLong(String.format("%s_%s", prefix, "CLIENT_READ_TIMEOUT_IN_MS"), 10_000L);
        long writeTimeout = config.getValueAsLong(String.format("%s_%s", prefix, "CLIENT_WRITE_TIMEOUT_IN_MS"), 10_000L);
        long connectTimeout = config.getValueAsLong(String.format("%s_%s", prefix, "CLIENT_CONNECT_TIMEOUT_IN_MS"), 10_000L);
        long callTimeout = config.getValueAsLong(String.format("%s_%s", prefix, "CLIENT_CALL_TIMEOUT_IN_MS"), 10_000L);
        boolean isRetryOnFailure = config.getValueAsBoolean(String.format("%s_%s", prefix, "CLIENT_ENABLE_RETRY_ON_FAILURE"), false);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .callTimeout(callTimeout, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(isRetryOnFailure)
                .addInterceptor(logging);

        return builder.build();
    }
}
