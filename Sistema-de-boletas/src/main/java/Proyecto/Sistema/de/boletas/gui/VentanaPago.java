package Proyecto.Sistema.de.boletas.gui;

import Proyecto.Sistema.de.boletas.Servicio.CompraServicio;

import javax.swing.*;

public class VentanaPago extends JDialog {

    private CompraServicio compraServicio;

    public VentanaPago(CompraServicio compraServicio) {
        this.compraServicio = compraServicio;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Pago");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(50, 50, 100, 30);
        add(lblCedula);

        JTextField txtCedula = new JTextField();
        txtCedula.setBounds(150, 50, 200, 30);
        add(txtCedula);

        JLabel lblComprobante = new JLabel("Comprobante:");
        lblComprobante.setBounds(50, 100, 100, 30);
        add(lblComprobante);

        JTextField txtComprobante = new JTextField();
        txtComprobante.setBounds(150, 100, 250, 30);
        add(txtComprobante);

        JButton btnPagar = new JButton("Registrar Pago");
        btnPagar.setBounds(100, 150, 150, 40);
        add(btnPagar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(260, 150, 120, 40);
        add(btnCancelar);

        btnPagar.addActionListener(e -> {
            registrarPago(txtCedula, txtComprobante);
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarPago(JTextField txtCedula, JTextField txtComprobante) {
        try {
            Long cedula = Long.parseLong(txtCedula.getText().trim());
            String comprobante = txtComprobante.getText().trim();

            if (comprobante.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el comprobante", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            compraServicio.registrarPago(cedula, comprobante);

            JOptionPane.showMessageDialog(this,
                    "Pago registrado exitosamente!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cédula inválida", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar pago: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}