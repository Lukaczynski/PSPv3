package cliente.vmc;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Controlador implements ActionListener, ListSelectionListener {
    Vista vista ;

    public Controlador(Vista vista) {
        this.vista = vista;
        vista.conectar.addActionListener(this);
        vista.canales.addListSelectionListener(this);
    }

    /**
     * Carga el json seleccionado desde la ip proporcionada en el capo host ip
     */
    String load(String comand) throws IOException {
        return new Scanner(new URL("http://"+vista.ip.getText()+":"+vista.port.getText()+comand).openStream(), "UTF-8").useDelimiter("\\A").next();
    }


    /**
     * Devuelve el objeto en formato json para su posterior tratamiento
     * @return JSONObject
     * @throws IOException
     */
    JSONObject toJson(String comand) throws IOException {
        return new JSONObject(load(comand));
    }

    void cargarCanales(String comand){
        try {
            JSONObject json = toJson(comand);
            vista.modelCanales.removeAllElements();
            for(Object j : json.getJSONArray("canales")){
                vista.modelCanales.addElement((String) j);
            }
            System.out.println(json.length());
            System.out.println(json.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista.frame, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.conectar){
            cargarCanales("/");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) {
            System.out.println("entra");
            if(e.getSource() == vista.canales) {
                System.out.println("esta en canales");
                try {
                    JSONObject json =toJson((String) vista.canales.getSelectedValue());
                    vista.nombre.setText(json.getString("nombre"));
                    vista.modelProgramacion.removeAllElements();
                    for( Object g : json.getJSONArray("programacion")){
                        vista.modelProgramacion.addElement(((JSONObject)g).getString("hora")
                                +" "+((JSONObject)g).getString("nombre")
                                +" "+((JSONObject)g).getString("descripcion"));
                        System.out.println("añade ->"+((JSONObject)g).getString("nombre"));
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(vista.frame, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
