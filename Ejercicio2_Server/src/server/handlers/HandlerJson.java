package server.handlers;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.Controlador;

import server.util.Util;


public class HandlerJson implements HttpHandler {


    private String controlador;



    public HandlerJson(String controlador) {

        this.controlador = controlador;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        final Headers headers = httpExchange.getResponseHeaders();
        final String requestMethod = httpExchange.getRequestMethod().toUpperCase();
        switch (requestMethod) {
            case Util.METHOD_GET:

                headers.set(Util.HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", Util.CHARSET));
                final byte[] rawResponseBody =  new String(Files.readAllBytes(Paths.get(controlador))).getBytes(Util.CHARSET);
                httpExchange.sendResponseHeaders(Util.STATUS_OK, rawResponseBody.length);
                httpExchange.getResponseBody().write(rawResponseBody);
                break;
            case Util.METHOD_OPTIONS:
                headers.set(Util.HEADER_ALLOW, Util.ALLOWED_METHODS);
                httpExchange.sendResponseHeaders(Util.STATUS_OK, Util.NO_RESPONSE_LENGTH);
                break;
            default:
                headers.set(Util.HEADER_ALLOW, Util.ALLOWED_METHODS);
                httpExchange.sendResponseHeaders(Util.STATUS_METHOD_NOT_ALLOWED, Util.NO_RESPONSE_LENGTH);
                break;
        }

        httpExchange.close();

    }


}
