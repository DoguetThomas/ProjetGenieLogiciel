package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserProfileDto;
import exceptions.ActivityNotFoundException;
import launch.ApplicationConfig;

/**
 * Base class for all controllers, providing common functionality for routing, request handling, and JSON processing.
 * 
 * Do not modify this class.
 */
public abstract class AbstractController implements HttpHandler {

    protected final ObjectMapper mapper = new ObjectMapper();

    private final List<Route> routes = new ArrayList<>();

    protected AbstractController() {
        defineRoutes();
    }

    @Override
    public final void handle(HttpExchange exchange) throws IOException {

        System.out.println("[" + LocalDateTime.now().toString().replace("T", " ").split("\\.")[0] + "] [INFO] -> "
                + exchange.getRequestMethod() + " " + exchange.getRequestURI().getRawPath());

        if (HttpJsonUtil.handlePreflight(exchange))
            return;

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getRawPath();

        for (Route route : routes) {
            Map<String, String> params = route.match(method, path);

            if (params != null) {
                try {
                    String json = route.handler.handle(exchange, params);
                    HttpJsonUtil.sendJson(exchange, 200, json);
                    return;

                } catch (ActivityNotFoundException e) {
                    HttpJsonUtil.sendJson(exchange, 404, error(e.getMessage()));
                    return;
                } catch (BadRequestException e) {
                    HttpJsonUtil.sendJson(exchange, 400, error(e.getMessage()));
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    HttpJsonUtil.sendJson(exchange, 500, error(e.getMessage()));
                    return;
                }
            }
        }

        HttpJsonUtil.sendJson(exchange, 404, error("Route not found"));
    }

    // --------- ROUTE DEFINITION ---------

    protected abstract void defineRoutes();

    protected void get(String path, RouteHandler handler) {
        routes.add(new Route("GET", path, handler));
    }

    protected void post(String path, RouteHandler handler) {
        routes.add(new Route("POST", path, handler));
    }

    // --------- ROUTE CLASS ---------

    private static class Route {
        String method;
        String pattern;
        RouteHandler handler;

        List<String> parts;

        Route(String method, String pattern, RouteHandler handler) {
            this.method = method;
            this.pattern = pattern;
            this.handler = handler;
            this.parts = split(pattern);
        }

        Map<String, String> match(String method, String path) {

            if (!this.method.equalsIgnoreCase(method))
                return null;

            List<String> pathParts = split(path);

            if (pathParts.size() != parts.size())
                return null;

            Map<String, String> params = new HashMap<>();

            for (int i = 0; i < parts.size(); i++) {
                String p = parts.get(i);
                String v = pathParts.get(i);

                if (p.startsWith("{") && p.endsWith("}")) {
                    String key = p.substring(1, p.length() - 1);
                    params.put(key, v);
                } else if (!p.equals(v)) {
                    return null;
                }
            }

            return params;
        }

        private static List<String> split(String path) {
            String normalized = path.startsWith("/") ? path.substring(1) : path;
            String[] raw = normalized.split("/");
            List<String> result = new ArrayList<>();
            for (String s : raw) {
                if (!s.isEmpty())
                    result.add(s);
            }
            return result;
        }
    }

    @FunctionalInterface
    protected interface RouteHandler {
        String handle(HttpExchange exchange, Map<String, String> params) throws Exception;
    }

    // --------- HELPERS ---------

    protected <T> T readBody(HttpExchange exchange, Class<T> clazz) throws IOException {
        String raw = readRawBody(exchange);
        return mapper.readValue(raw, clazz);
    }

    private String readRawBody(HttpExchange exchange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }

    protected String ok(String msg) {
        return "{\"success\":\"" + msg + "\"}";
    }

    private String error(String msg) {
        return "{\"error\":\"" + msg + "\"}";
    }

    protected String json(Object data) throws IOException {
        return mapper.writeValueAsString(data);
    }

    protected static class BadRequestException extends RuntimeException {
        public BadRequestException(String msg) {
            super(msg);
        }
    }
}