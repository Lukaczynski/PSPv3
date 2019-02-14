package server.handlers;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.Controlador;
import server.util.Util;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class HandlerAdmin implements HttpHandler {



    public HandlerAdmin(Controlador controlador) {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
            final Headers headers = httpExchange.getResponseHeaders();
            final String requestMethod = httpExchange.getRequestMethod().toUpperCase();
            switch (requestMethod) {
                case Util.METHOD_GET:
                    System.out.println("Metodo get");
                    System.out.println(httpExchange.getRemoteAddress().getAddress());
                    System.out.println(httpExchange.getRequestURI());
                    final Map<String, List<String>> parametros = Util.getRequestParameters(httpExchange.getRequestURI());
                    parametros.forEach((key, value)->{
                        System.out.println("Key:: "+key);
                        value.forEach((s)->{
                            System.out.println("\t"+s);
                        });

                    });
                    final String responseBody = "{\"Mensaje!\":\"Hola \"}";
                    headers.set(Util.HEADER_CONTENT_TYPE, String.format("text/html; charset=%s", Util.CHARSET));
                    final byte[] rawResponseBody = responseBody.getBytes(Util.CHARSET);
                    httpExchange.sendResponseHeaders(Util.STATUS_OK, rawResponseBody.length);
                    httpExchange.getResponseBody().write(rawResponseBody);
                    break;
                case Util.METHOD_OPTIONS:
                    System.out.println("method options");
                    headers.set(Util.HEADER_ALLOW, Util.ALLOWED_METHODS);
                    httpExchange.sendResponseHeaders(Util.STATUS_OK, Util.NO_RESPONSE_LENGTH);
                    break;
                default:
                    System.out.println("locazooo");
                    headers.set(Util.HEADER_ALLOW, Util.ALLOWED_METHODS);
                    httpExchange.sendResponseHeaders(Util.STATUS_METHOD_NOT_ALLOWED, Util.NO_RESPONSE_LENGTH);
                    break;
            }

            httpExchange.close();

    }


}
