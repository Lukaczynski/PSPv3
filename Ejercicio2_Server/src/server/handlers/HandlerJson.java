package server;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.util.Util;


public class HandlerJson implements HttpHandler {

    private static final String HEADER_ALLOW = "Allow";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";



    private static final int STATUS_OK = 200;
    private static final int STATUS_METHOD_NOT_ALLOWED = 405;

    private static final int NO_RESPONSE_LENGTH = -1;

    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String ALLOWED_METHODS = METHOD_GET + "," + METHOD_OPTIONS;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
            final Headers headers = httpExchange.getResponseHeaders();
            final String requestMethod = httpExchange.getRequestMethod().toUpperCase();
            switch (requestMethod) {
                case METHOD_GET:
                    System.out.println("Metodo get");
                    System.out.println(httpExchange.getRemoteAddress().getAddress());
                    final Map<String, List<String>> parametros = Util.getRequestParameters(httpExchange.getRequestURI());
                    parametros.forEach((key, value)->{
                        System.out.println("Key:: "+key);
                        value.forEach((s)->{
                            System.out.println("\t"+s);
                        });

                    });
                    final String responseBody = "{\"hello world!\":2}";
                    headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", Util.CHARSET));
                    final byte[] rawResponseBody = responseBody.getBytes(Util.CHARSET);
                    httpExchange.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
                    httpExchange.getResponseBody().write(rawResponseBody);
                    break;
                case METHOD_OPTIONS:
                    headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                    httpExchange.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
                    break;
                default:
                    headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                    httpExchange.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
                    break;
            }

            httpExchange.close();

    }


}
