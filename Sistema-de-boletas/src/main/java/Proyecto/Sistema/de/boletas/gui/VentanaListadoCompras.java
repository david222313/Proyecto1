package Proyecto.Sistema.de.boletas.gui;

import Proyecto.Sistema.de.boletas.Entidades.Compra;
import Proyecto.Sistema.de.boletas.Servicio.CompraServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListadoCompras extends JDialog {

    private CompraServicio compraServicio;

    public VentanaListadoCompras(CompraServicio compraServicio) {
        this.compraServicio = compraServicio;
        initComponents();
    }

    private void initComponents() {
        setTitle("Mis Compras");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // Panel superior para ingresar cédula
        JPanel panelSuperior = new JPanel(new FlowLayout());
        JLabel lblCedula = new JLabel("Cédula:");
        JTextField txtCedula = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");

        panelSuperior.add(lblCedula);
        panelSuperior.add(txtCedula);
        panelSuperior.add(btnBuscar);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla para mostrar compras
        String[] columnas = {"ID", "Fecha Compra", "Estado", "Método Pago", "Valor Total", "Fecha Límite"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);

        // Evento búsqueda
        btnBuscar.addActionListener(e -> {
            buscarCompras(txtCedula, model);
        });
    }

    private void buscarCompras(JTextField txtCedula, DefaultTableModel model) {
        try {
            Long cedula = Long.parseLong(txtCedula.getText().trim());
            List<Compra> compras = compraServicio.listarComprasUsuario(cedula);

            model.setRowCount(0);

            if (compras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron compras para esta cédula",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Compra compra : compras) {
                model.addRow(new Object[]{
                        compra.getId(),
                        compra.getFechaCompra(),
                        compra.getEstado(),
                        compra.getMetodoPago(),
                        compra.getValorTotal(),
                        compra.getFechaLimitePago()
                });
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cédula inválida", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar compras: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}