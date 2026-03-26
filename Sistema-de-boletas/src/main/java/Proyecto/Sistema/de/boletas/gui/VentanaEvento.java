package Proyecto.Sistema.de.boletas.gui;

import Proyecto.Sistema.de.boletas.Entidades.Evento;
import Proyecto.Sistema.de.boletas.Servicio.EventoServicio;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VentanaEvento extends JDialog {

    private EventoServicio eventoServicio;

    public VentanaEvento(EventoServicio eventoServicio) {
        this.eventoServicio = eventoServicio;
        initComponents();
    }

    private void initComponents() {
        setTitle("Crear Evento");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos del formulario
        JLabel lblNombre = new JLabel("Nombre del Evento:");
        JTextField txtNombre = new JTextField(20);

        JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
        JTextField txtFecha = new JTextField(20);

        JLabel lblHora = new JLabel("Hora (HH:MM):");
        JTextField txtHora = new JTextField(20);

        JLabel lblLugar = new JLabel("Lugar:");
        JTextField txtLugar = new JTextField(20);

        JLabel lblPatrocinador = new JLabel("Patrocinador:");
        JTextField txtPatrocinador = new JTextField(20);

        JLabel lblTotalBoletas = new JLabel("Total Boletas:");
        JTextField txtTotalBoletas = new JTextField(20);

        JLabel lblBoletasA = new JLabel("Boletas Zona A:");
        JTextField txtBoletasA = new JTextField(20);

        JLabel lblBoletasB = new JLabel("Boletas Zona B:");
        JTextField txtBoletasB = new JTextField(20);

        JLabel lblBoletasC = new JLabel("Boletas Zona C:");
        JTextField txtBoletasC = new JTextField(20);

        JButton btnGuardar = new JButton("Guardar Evento");
        JButton btnCancelar = new JButton("Cancelar");

        // Agregar componentes
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblNombre, gbc);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblFecha, gbc);
        gbc.gridx = 1;
        add(txtFecha, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblHora, gbc);
        gbc.gridx = 1;
        add(txtHora, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblLugar, gbc);
        gbc.gridx = 1;
        add(txtLugar, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblPatrocinador, gbc);
        gbc.gridx = 1;
        add(txtPatrocinador, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblTotalBoletas, gbc);
        gbc.gridx = 1;
        add(txtTotalBoletas, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblBoletasA, gbc);
        gbc.gridx = 1;
        add(txtBoletasA, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblBoletasB, gbc);
        gbc.gridx = 1;
        add(txtBoletasB, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(lblBoletasC, gbc);
        gbc.gridx = 1;
        add(txtBoletasC, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, gbc);

        // Acciones
        btnGuardar.addActionListener(e -> guardarEvento(txtNombre, txtFecha, txtHora,
                txtLugar, txtPatrocinador, txtTotalBoletas, txtBoletasA, txtBoletasB, txtBoletasC));

        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarEvento(JTextField txtNombre, JTextField txtFecha, JTextField txtHora,
                               JTextField txtLugar, JTextField txtPatrocinador, JTextField txtTotalBoletas,
                               JTextField txtBoletasA, JTextField txtBoletasB, JTextField txtBoletasC) {
        try {
            Evento evento = Evento.builder()
                    .nombre(txtNombre.getText())
                    .fecha(LocalDate.parse(txtFecha.getText()))
                    .hora(LocalTime.parse(txtHora.getText()))
                    .lugar(txtLugar.getText())
                    .patrocinador(txtPatrocinador.getText())
                    .totalBoletas(Integer.parseInt(txtTotalBoletas.getText()))
                    .boletasZonaA(Integer.parseInt(txtBoletasA.getText()))
                    .boletasZonaB(Integer.parseInt(txtBoletasB.getText()))
                    .boletasZonaC(Integer.parseInt(txtBoletasC.getText()))
                    .build();

            eventoServicio.guardar(evento);
            JOptionPane.showMessageDialog(this, "Evento guardado exitosamente!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}