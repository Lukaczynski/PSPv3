package cliente.vmc;

import javax.swing.*;

public class Vista {
    JPanel panel1;
    JTextField ip;
    JButton conectar;
    JList canales;
    JList programacion;
    JTextField port;
    JLabel nombre;
    DefaultListModel<String> modelCanales;
    DefaultListModel<String> modelProgramacion;
    JFrame frame;
    public Vista() {
        frame = new JFrame("Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);

        modelProgramacion = new DefaultListModel<>();
        modelCanales = new DefaultListModel<>();

        programacion.setModel(modelProgramacion);
        canales.setModel(modelCanales);
    }
}
