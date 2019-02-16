package server;

import com.sun.net.httpserver.HttpServer;
import server.base.Canal;
import server.handlers.HandlerAdmin;
import server.handlers.HandlerJson;
import server.util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controlador {
    public List<String> canales;

    HttpServer server;

    public Controlador(HttpServer server) {
        this.server= server;
        canales = new ArrayList<>();
       server.createContext("/",new HandlerAdmin(this));

    }

    public void update() {
        File folder = new File("canales");
        File[] listOfFiles = folder.listFiles();
        if(!canales.isEmpty()){
            for(String s: canales) {
                server.removeContext(s);
            }
        }
        canales.clear();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                if(fileName.substring(fileName.indexOf("."), fileName.length()).equalsIgnoreCase(".json")) {
                    System.out.println(fileName);
                    server.createContext("/" + fileName.substring(0, fileName.indexOf(".")), new HandlerJson(listOfFiles[i].getAbsolutePath()));
                    canales.add("/" + fileName.substring(0, fileName.indexOf(".")));
                }else{
                    System.out.println("fichero ignorado :: "+fileName);
                }
            }
        }
        System.out.println("==========");

    }
}
