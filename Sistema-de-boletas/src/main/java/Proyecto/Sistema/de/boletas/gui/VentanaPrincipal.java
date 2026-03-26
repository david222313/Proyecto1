package Proyecto.Sistema.de.boletas.gui;

import Proyecto.Sistema.de.boletas.Servicio.CompraServicio;
import Proyecto.Sistema.de.boletas.Servicio.EventoServicio;
import Proyecto.Sistema.de.boletas.SistemaDeBoletasApplication;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

    private EventoServicio eventoServicio;
    private CompraServicio compraServicio;

    public VentanaPrincipal() {
        System.out.println("=== INICIALIZANDO VENTANA PRINCIPAL ===");

        // Intentar obtener los servicios con reintentos
        obtenerServiciosConReintentos();
        initComponents();

        // Verificar si los servicios están disponibles
        if (eventoServicio == null || compraServicio == null) {
            System.err.println("ADVERTENCIA: Servicios no disponibles");
            System.err.println("EventoServicio: " + (eventoServicio != null ? "OK" : "NULL"));
            System.err.println("CompraServicio: " + (compraServicio != null ? "OK" : "NULL"));

            JOptionPane.showMessageDialog(this,
                    "Los servicios no están disponibles. Algunas funcionalidades pueden no funcionar.\n" +
                            "Verifique que la base de datos H2 esté configurada correctamente.\n\n" +
                            "Presione Aceptar para continuar o cierre la aplicación.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println("✓ Servicios cargados correctamente");
        }
    }

    private void obtenerServiciosConReintentos() {
        int intentos = 0;
        int maxIntentos = 20;

        while (intentos < maxIntentos && (eventoServicio == null || compraServicio == null)) {
            try {
                if (eventoServicio == null && SistemaDeBoletasApplication.isSpringReady()) {
                    eventoServicio = SistemaDeBoletasApplication.getBean(EventoServicio.class);
                    if (eventoServicio != null) {
                        System.out.println("✓ EventoServicio obtenido en intento " + (intentos + 1));
                    }
                }

                if (compraServicio == null && SistemaDeBoletasApplication.isSpringReady()) {
                    compraServicio = SistemaDeBoletasApplication.getBean(CompraServicio.class);
                    if (compraServicio != null) {
                        System.out.println("✓ CompraServicio obtenido en intento " + (intentos + 1));
                    }
                }

                if (eventoServicio == null || compraServicio == null) {
                    Thread.sleep(500);
                    intentos++;
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Error obteniendo servicios: " + e.getMessage());
                intentos++;
            }
        }
    }

    private void initComponents() {
        setTitle("Sistema de Venta de Boletas");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Botón para gestionar eventos
        JButton btnEventos = new JButton("Gestionar Eventos");
        btnEventos.setBounds(100, 50, 250, 40);
        btnEventos.addActionListener(e -> {
            if (eventoServicio != null) {
                VentanaEvento ventana = new VentanaEvento(eventoServicio);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Servicio de eventos no disponible.\n" +
                                "La aplicación no está completamente inicializada.\n" +
                                "Intente reiniciar la aplicación.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnEventos);

        // Botón para listar eventos
        JButton btnListarEventos = new JButton("Listar Eventos");
        btnListarEventos.setBounds(100, 100, 250, 40);
        btnListarEventos.addActionListener(e -> {
            if (eventoServicio != null) {
                VentanaListadoEventos ventana = new VentanaListadoEventos(eventoServicio);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Servicio de eventos no disponible.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnListarEventos);

        // Botón para comprar boletas
        JButton btnComprar = new JButton("Comprar Boletas");
        btnComprar.setBounds(100, 150, 250, 40);
        btnComprar.addActionListener(e -> {
            if (compraServicio != null && eventoServicio != null) {
                VentanaCompra ventana = new VentanaCompra(compraServicio, eventoServicio);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Servicios no disponibles.\n" +
                                "CompraServicio: " + (compraServicio != null ? "OK" : "NO") + "\n" +
                                "EventoServicio: " + (eventoServicio != null ? "OK" : "NO"),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnComprar);

        // Botón para registrar pago
        JButton btnPagar = new JButton("Registrar Pago");
        btnPagar.setBounds(100, 200, 250, 40);
        btnPagar.addActionListener(e -> {
            if (compraServicio != null) {
                VentanaPago ventana = new VentanaPago(compraServicio);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Servicio de compras no disponible.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnPagar);

        // Botón para ver compras
        JButton btnVerCompras = new JButton("Mis Compras");
        btnVerCompras.setBounds(100, 250, 250, 40);
        btnVerCompras.addActionListener(e -> {
            if (compraServicio != null) {
                VentanaListadoCompras ventana = new VentanaListadoCompras(compraServicio);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Servicio de compras no disponible.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnVerCompras);

        // Botón para salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(100, 310, 250, 40);
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro que desea salir?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        add(btnSalir);

        // Manejar cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        VentanaPrincipal.this,
                        "¿Está seguro que desea salir?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}