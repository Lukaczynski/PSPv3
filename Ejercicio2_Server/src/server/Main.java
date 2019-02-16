package server;

import com.sun.net.httpserver.HttpServer;
import server.handlers.HandlerJson;
import server.util.Util;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    private static String hostname = "localhost";
    private static int potr = 8080;
    private static final int BACKLOG = 1;


    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            if (args.length % 2 == 0) {

                for (int i = 0; i < args.length; i = i + 2) {
                    if (args[i].equalsIgnoreCase("--listen_in")) {
                        if (Util.validate(args[i + 1])) {
                            hostname = args[i + 1];
                        } else {
                            System.err.println("ip no valida:" + args[i + 1]);
                        }
                    } else if (args[i].equalsIgnoreCase("--port")) {
                        try {
                            potr = Integer.parseInt(args[i + 1]);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.err.println("No se reconoce el parametro :" + args[i]);
                        i = 40;
                    }
                }
            } else {
                System.err.println("No se reconocen los parametros...");
            }
        }
        final HttpServer server = HttpServer.create(new InetSocketAddress(hostname, potr), BACKLOG);
        System.out.println("Server on " + server.getAddress().getAddress() + " at " + server.getAddress().getPort());



        System.out.println("Server Start");
        server.start();
        Controlador controlador = new Controlador(server);
        //server.createContext("/alabama", new HandlerJson(controlador));
    }
}
