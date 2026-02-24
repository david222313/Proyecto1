package org.example.modelo;

import org.example.modelo.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class VentanaInicial extends JFrame {

    private Evento eventoActual;
    private JTextArea areaInfo;
    private JLabel lblEstado;

    public VentanaInicial() {
        // Configuración de la ventana
        setTitle("Sistema de Boletas - Avance 1");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel titulo = new JLabel("Avance 1 - Reserva de Boletas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Área donde se muestra la información del evento
        areaInfo = new JTextArea();
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaInfo.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(areaInfo);
        add(scroll, BorderLayout.CENTER);

        // Panel de botones y estado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnReservarA = new JButton("Reservar 5 Zona A");
        JButton btnReservarB = new JButton("Reservar 5 Zona B");
        JButton btnReservarC = new JButton("Reservar 5 Zona C");

        panelBotones.add(btnReservarA);
        panelBotones.add(btnReservarB);
        panelBotones.add(btnReservarC);

        lblEstado = new JLabel("Estado: Listo para reservar", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 14));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(lblEstado, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // Crear evento de ejemplo al iniciar
        crearEventoEjemplo();

        // Acciones de botones
        btnReservarA.addActionListener(e -> reservarBoletas(Zona.a, 5, "Zona A"));
        btnReservarB.addActionListener(e -> reservarBoletas(Zona.b, 5, "Zona B"));
        btnReservarC.addActionListener(e -> reservarBoletas(Zona.c, 5, "Zona C"));

        // Mostrar información inicial
        actualizarInfoEvento();
    }

    private void crearEventoEjemplo() {
        // Usamos el constructor que tienes (aunque esté vacío por ahora)
        eventoActual = new Evento(
                "Concierto Ejemplo",
                LocalDate.now().plusDays(7),
                LocalTime.of(20, 0),
                "Estadio Ejemplo",
                "Patrocinador Demo",
                100, 200, 300   // cantA, cantB, cantC
        );

        // Como tu constructor está vacío, inicializamos manualmente las boletas
        // (esto es temporal hasta que arregles el constructor)
        Map<Zona, Integer> boletas = new HashMap<>();
        boletas.put(Zona.a, 100);
        boletas.put(Zona.b, 200);
        boletas.put(Zona.c, 300);
        eventoActual.setBoletasPorZona(boletas);
    }

    private void reservarBoletas(Zona zona, int cantidad, String nombreZona) {
        if (eventoActual == null) {
            lblEstado.setText("Error: No hay evento creado");
            return;
        }

        Map<Zona, Integer> boletas = eventoActual.getBoletasPorZona();
        if (boletas == null) {
            lblEstado.setText("Error: No hay boletas cargadas");
            return;
        }

        int disponibles = boletas.getOrDefault(zona, 0);
        if (cantidad > disponibles) {
            lblEstado.setText("No hay suficientes boletas en " + nombreZona);
            return;
        }

        // Actualiza el stock
        boletas.put(zona, disponibles - cantidad);
        eventoActual.setBoletasPorZona(boletas);

        lblEstado.setText("¡Reserva exitosa! " + cantidad + " boletas en " + nombreZona);
        actualizarInfoEvento();
    }

    private void actualizarInfoEvento() {
        if (eventoActual == null) {
            areaInfo.setText("No hay evento creado aún.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Evento: ").append(eventoActual.getNombre()).append("\n");
        sb.append("Fecha: ").append(eventoActual.getFecha()).append(" ").append(eventoActual.getHora()).append("\n");
        sb.append("Lugar: ").append(eventoActual.getLugar()).append("\n");
        sb.append("Patrocinador: ").append(eventoActual.getPatrocinador()).append("\n\n");
        sb.append("Boletas disponibles:\n");

        Map<Zona, Integer> boletas = eventoActual.getBoletasPorZona();
        if (boletas != null) {
            sb.append("  Zona A: ").append(boletas.getOrDefault(Zona.a, 0)).append(" ($200)\n");
            sb.append("  Zona B: ").append(boletas.getOrDefault(Zona.b, 0)).append(" ($100)\n");
            sb.append("  Zona C: ").append(boletas.getOrDefault(Zona.c, 0)).append(" ($50)\n");
        }

        areaInfo.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaInicial().setVisible(true));
    }
}