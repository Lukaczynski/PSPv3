package server;

import com.sun.net.httpserver.HttpServer;
import server.handlers.HandlerAdmin;
import server.handlers.HandlerJson;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    private static final String HOSTNAME = "0.0.0.0";
    private static final int PORT = 8080;
    private static final int BACKLOG = 1;
    public static enum Chanels{Canal1,Canal2,Canal3};



    public static void main(String[] args) throws IOException {

        Controlador controlador = new Controlador();
        final HttpServer server = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), BACKLOG);
        server.createContext("/admin",new HandlerAdmin(controlador));
        server.createContext("/canal1",new HandlerJson(controlador,Chanels.Canal1));
        server.createContext("/canal2",new HandlerJson(controlador,Chanels.Canal2));
        server.createContext("/canal3",new HandlerJson(controlador,Chanels.Canal3));
        server.start();
    }
}
