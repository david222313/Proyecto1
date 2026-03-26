package Proyecto.Sistema.de.boletas.gui;

import javax.swing.*;

public class VentanaTabla extends JFrame {

    public VentanaTabla(){

        JTable tabla = new JTable();

        add(new JScrollPane(tabla));

        setSize(600,400);

        setVisible(true);
    }
}