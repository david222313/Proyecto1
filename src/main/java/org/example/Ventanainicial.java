package org.example;

import org.example.modelo.Evento;
import org.example.modelo.Zona;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private List<Evento> eventos = new ArrayList<>();
    private JTextArea areaEventos;
    private JLabel lblEstado;

    public VentanaPrincipal() {
        // Configuración básica de la ventana
        setTitle("Sistema de Venta de Boletas - Avance 1");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior: título
        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Avance 1 - Interfaz gráfica básica", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Área central: lista de eventos
        areaEventos = new JTextArea();
        areaEventos.setEditable(false);
        areaEventos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaEventos.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(areaEventos);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior: botones y estado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnCrearEjemplo = new JButton("Crear evento de ejemplo");
        JButton btnReservarPrueba = new JButton("Reservar 10 boletas Zona A (prueba)");

        panelBotones.add(btnCrearEjemplo);
        panelBotones.add(btnReservarPrueba);

        lblEstado = new JLabel("Estado: Listo para usar", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 14));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(lblEstado, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // Acciones de botones
        btnCrearEjemplo.addActionListener(e -> {
            crearEventoEjemplo();
            actualizarListaEventos();
            lblEstado.setText("Estado: Evento de ejemplo creado");
        });

        btnReservarPrueba.addActionListener(e -> {
            if (eventos.isEmpty()) {
                lblEstado.setText("Estado: Crea un evento primero");
                return;
            }

            Evento ultimoEvento = eventos.get(eventos.size() - 1);
            boolean exito = ultimoEvento.reservarBoletas(Zona.A, 10);

            if (exito) {
                lblEstado.setText("Estado: Reserva exitosa - 10 boletas Zona A reservadas");
            } else {
                lblEstado.setText("Estado: No hay suficientes boletas en Zona A");
            }

            actualizarListaEventos();
        });

        // Crear un evento inicial para que no esté vacío
        crearEventoEjemplo();
        actualizarListaEventos();
    }

    private void crearEventoEjemplo() {
        Evento ev = new Evento(
                "Concierto Ejemplo - Avance 1",
                LocalDate.now().plusDays(10),
                LocalTime.of(20, 0),
                "Estadio Ejemplo",
                "Patrocinador Demo",
                150, 250, 400
        );
        eventos.add(ev);
    }

    private void actualizarListaEventos() {
        StringBuilder sb = new StringBuilder();
        sb.append("Eventos disponibles:\n\n");

        if (eventos.isEmpty()) {
            sb.append("No hay eventos aún.\n");
        } else {
            for (Evento ev : eventos) {
                sb.append("Nombre: ").append(ev.getNombre()).append("\n");
                sb.append("Fecha y hora: ").append(ev.getFecha()).append(" ").append(ev.getHora()).append("\n");
                sb.append("Lugar: ").append(ev.getLugar()).append("\n");
                sb.append("Patrocinador: ").append(ev.getPatrocinador()).append("\n");
                sb.append("Boletas restantes:\n");
                sb.append("  Zona A: ").append(ev.getBoletasDisponibles(Zona.A)).append(" ($200 c/u)\n");
                sb.append("  Zona B: ").append(ev.getBoletasDisponibles(Zona.B)).append(" ($100 c/u)\n");
                sb.append("  Zona C: ").append(ev.getBoletasDisponibles(Zona.C)).append(" ($50 c/u)\n");
                sb.append("----------------------------------------\n\n");
            }
        }

        areaEventos.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
    }
}