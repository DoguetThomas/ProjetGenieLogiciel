package controllers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for handling JSON responses and CORS headers in HTTP exchanges.
 * 
 * Do not modify this class.
 */
public final class HttpJsonUtil {

    private static void addCors(Headers headers) {
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "GET, OPTIONS, POST");
        headers.set("Access-Control-Allow-Headers", "Content-Type, X-Activity-Name");
        headers.set("Content-Type", "application/json; charset=utf-8");
    }

    public static boolean handlePreflight(HttpExchange exchange) throws IOException {
        addCors(exchange.getResponseHeaders());
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return true;
        }
        return false;
    }

    public static void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        addCors(exchange.getResponseHeaders());
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
