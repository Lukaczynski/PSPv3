package server.base;

import java.io.Serializable;
import java.util.ArrayList;

public class Canal implements Serializable {

    public String name;
    public ArrayList<Programacion> programacion;

    public Canal() {
    }

    public Canal(String name) {
        this.name = name;
        programacion=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Programacion> getProgramacion() {
        return programacion;
    }

    public void setProgramacion(ArrayList<Programacion> programacion) {
        this.programacion = programacion;
    }
}
