package Proyecto.Sistema.de.boletas.gui;

import Proyecto.Sistema.de.boletas.Entidades.*;
import Proyecto.Sistema.de.boletas.Servicio.CompraServicio;
import Proyecto.Sistema.de.boletas.Servicio.EventoServicio;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaCompra extends JDialog {

    private CompraServicio compraServicio;
    private EventoServicio eventoServicio;
    private Evento eventoSeleccionado;

    // Guardamos referencia al combo para usarlo en cargarEventos()
    private JComboBox<Evento> cmbEventos;

    public VentanaCompra(CompraServicio compraServicio, EventoServicio eventoServicio) {
        this.compraServicio = compraServicio;
        this.eventoServicio = eventoServicio;
        initComponents();
        cargarEventos(); // cargar DESPUÉS de initComponents para que cmbEventos exista
    }

    private void initComponents() {
        setTitle("Compra de Boletas");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        JLabel lblEvento = new JLabel("Seleccionar Evento:");
        lblEvento.setBounds(50, 30, 150, 30);
        add(lblEvento);

        // CORRECCIÓN: guardamos referencia en campo de instancia
        cmbEventos = new JComboBox<>();
        cmbEventos.setBounds(200, 30, 250, 30);
        add(cmbEventos);

        JLabel lblZonaA = new JLabel("Zona A ($200):");
        lblZonaA.setBounds(50, 80, 150, 30);
        add(lblZonaA);

        JSpinner spnZonaA = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spnZonaA.setBounds(200, 80, 100, 30);
        add(spnZonaA);

        JLabel lblZonaB = new JLabel("Zona B ($100):");
        lblZonaB.setBounds(50, 120, 150, 30);
        add(lblZonaB);

        JSpinner spnZonaB = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spnZonaB.setBounds(200, 120, 100, 30);
        add(spnZonaB);

        JLabel lblZonaC = new JLabel("Zona C ($50):");
        lblZonaC.setBounds(50, 160, 150, 30);
        add(lblZonaC);

        JSpinner spnZonaC = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spnZonaC.setBounds(200, 160, 100, 30);
        add(spnZonaC);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(50, 210, 150, 30);
        add(lblCedula);

        JTextField txtCedula = new JTextField();
        txtCedula.setBounds(200, 210, 150, 30);
        add(txtCedula);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 250, 150, 30);
        add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(200, 250, 250, 30);
        add(txtNombre);

        JLabel lblMetodoPago = new JLabel("Método de Pago:");
        lblMetodoPago.setBounds(50, 290, 150, 30);
        add(lblMetodoPago);

        JComboBox<MetodoPago> cmbMetodoPago = new JComboBox<>(MetodoPago.values());
        cmbMetodoPago.setBounds(200, 290, 200, 30);
        add(cmbMetodoPago);

        JButton btnReservar = new JButton("Reservar");
        btnReservar.setBounds(100, 340, 120, 40);
        add(btnReservar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(250, 340, 120, 40);
        add(btnCancelar);

        cmbEventos.addActionListener(e ->
                eventoSeleccionado = (Evento) cmbEventos.getSelectedItem()
        );

        btnReservar.addActionListener(e ->
                realizarReserva(txtCedula, txtNombre, cmbMetodoPago, spnZonaA, spnZonaB, spnZonaC)
        );

        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarEventos() {
        try {
            List<Evento> eventos = eventoServicio.listarEventos();
            cmbEventos.removeAllItems(); // limpiar antes de agregar
            for (Evento evento : eventos) {
                cmbEventos.addItem(evento);
            }
            if (cmbEventos.getItemCount() > 0) {
                eventoSeleccionado = cmbEventos.getItemAt(0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar eventos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarReserva(JTextField txtCedula, JTextField txtNombre,
                                 JComboBox<MetodoPago> cmbMetodoPago,
                                 JSpinner spnZonaA, JSpinner spnZonaB, JSpinner spnZonaC) {
        try {
            if (eventoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un evento", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long cedula = Long.parseLong(txtCedula.getText().trim());
            String nombre = txtNombre.getText().trim();
            MetodoPago metodoPago = (MetodoPago) cmbMetodoPago.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<Zona, Integer> zonas = new HashMap<>();
            int totalBoletas = 0;

            int cantidadA = (int) spnZonaA.getValue();
            if (cantidadA > 0) { zonas.put(Zona.A, cantidadA); totalBoletas += cantidadA; }

            int cantidadB = (int) spnZonaB.getValue();
            if (cantidadB > 0) { zonas.put(Zona.B, cantidadB); totalBoletas += cantidadB; }

            int cantidadC = (int) spnZonaC.getValue();
            if (cantidadC > 0) { zonas.put(Zona.C, cantidadC); totalBoletas += cantidadC; }

            if (totalBoletas == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione al menos una boleta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Compra compra = compraServicio.reservarCompra(cedula, nombre, eventoSeleccionado, metodoPago, zonas);

            JOptionPane.showMessageDialog(this,
                    "¡Reserva exitosa!\n" +
                            "Total a pagar: $" + compra.getValorTotal() + "\n" +
                            "Fecha límite de pago: " + compra.getFechaLimitePago(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cédula inválida", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error en la reserva: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}