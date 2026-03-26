package Proyecto.Sistema.de.boletas.gui;

import Proyecto.Sistema.de.boletas.Entidades.Evento;
import Proyecto.Sistema.de.boletas.Servicio.EventoServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListadoEventos extends JDialog {

    private EventoServicio eventoServicio;
    private DefaultTableModel model;

    public VentanaListadoEventos(EventoServicio eventoServicio) {
        this.eventoServicio = eventoServicio;
        initComponents();
        cargarEventos();
    }

    private void initComponents() {
        setTitle("Listado de Eventos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // Crear el modelo de la tabla con columnas
        String[] columnas = {"ID", "Nombre", "Fecha", "Hora", "Lugar",
                "Total Boletas", "Zona A", "Zona B", "Zona C"};
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Crear la tabla con el modelo
        JTable tabla = new JTable(model);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarEventos());

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarEventos() {
        try {
            if (eventoServicio == null) {
                JOptionPane.showMessageDialog(this,
                        "Servicio de eventos no disponible.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Evento> eventos = eventoServicio.listarEventos();

            // Limpiar la tabla
            model.setRowCount(0);

            if (eventos == null || eventos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay eventos registrados.\n" +
                                "Puede crear eventos desde el menú principal.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Agregar eventos a la tabla
            for (Evento evento : eventos) {
                model.addRow(new Object[]{
                        evento.getId(),
                        evento.getNombre(),
                        evento.getFecha(),
                        evento.getHora(),
                        evento.getLugar(),
                        evento.getTotalBoletas(),
                        evento.getBoletasZonaA(),
                        evento.getBoletasZonaB(),
                        evento.getBoletasZonaC()
                });
            }

            System.out.println("✓ Eventos cargados: " + eventos.size());

        } catch (Exception ex) {
            System.err.println("Error al cargar eventos: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar eventos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}