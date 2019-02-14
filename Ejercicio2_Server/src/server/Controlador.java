package server;

import server.base.Canal;
import server.util.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Controlador {
    public ArrayList<Canal> canales;

    public Controlador() {
        read();
    }

    public void save() {

        try {

            FileOutputStream fileOut = new FileOutputStream(Util.PATH);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(canales);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void read() {

        try {

            FileInputStream fileIn = new FileInputStream(Util.PATH);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            canales = (ArrayList<Canal>) objectIn.readObject();
            objectIn.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
