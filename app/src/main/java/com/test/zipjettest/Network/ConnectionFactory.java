package com.test.zipjettest.Network;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kavya, 17-04-2015.
 */
public class ConnectionFactory {
    private static final String BASE_URL = "https://api.goeuro.com/api/v2/position/suggest/de/";

    private void addCookieToConnection(HttpsURLConnection connection) {
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

    public HttpsURLConnection getConnectionForBaseUrl(String sName) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(BASE_URL + sName).openConnection();
        addCookieToConnection(connection);
        return connection;
    }
}