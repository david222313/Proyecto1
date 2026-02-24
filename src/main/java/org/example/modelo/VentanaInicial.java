package org.example.modelo;

import org.example.modelo.Evento;
import org.example.modelo.Zona;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VentanaInicial extends JFrame {

    private List<Evento> eventos = new ArrayList<>();
    private JTextArea areaEventos;
    private JLabel lblEstado;

    public VentanaInicial() {
        setTitle("Sistema de Boletas - Avance 1");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel titulo = new JLabel("Avance 1 - Reserva de Boletas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Área donde se muestran los eventos
        areaEventos = new JTextArea();
        areaEventos.setEditable(false);
        areaEventos.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaEventos.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(areaEventos);
        add(scroll, BorderLayout.CENTER);

        // Panel de botones y estado
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));

        JButton btnCrearNuevo = new JButton("Crear Nuevo Evento");
        JButton btnReservarA = new JButton("Reservar 5 en Zona A");
        JButton btnReservarB = new JButton("Reservar 5 en Zona B");
        JButton btnReservarC = new JButton("Reservar 5 en Zona C");

        panelBotones.add(btnCrearNuevo);
        panelBotones.add(btnReservarA);
        panelBotones.add(btnReservarB);
        panelBotones.add(btnReservarC);

        lblEstado = new JLabel("Estado: Listo para usar", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 14));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        panelInferior.add(lblEstado, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // Crear 3 eventos de ejemplo al iniciar
        crearEventosEjemplo();

        // Acciones de botones
        btnCrearNuevo.addActionListener(e -> {
            crearEventoNuevo();
            actualizarListaEventos();
            lblEstado.setText("Estado: Nuevo evento creado");
        });

        btnReservarA.addActionListener(e -> reservarBoletas(Zona.A, 5, "Zona A"));
        btnReservarB.addActionListener(e -> reservarBoletas(Zona.B, 5, "Zona B"));
        btnReservarC.addActionListener(e -> reservarBoletas(Zona.C, 5, "Zona C"));

        // Mostrar información inicial
        actualizarListaEventos();
    }

    private void crearEventosEjemplo() {
        // Evento 1
        Evento ev1 = new Evento("Concierto Bad Bunny", LocalDate.now().plusDays(10), LocalTime.of(20, 0), "Estadio El Campín", "Coca-Cola", 150, 250, 400);
        eventos.add(ev1);

        // Evento 2
        Evento ev2 = new Evento("Karol G - Mañana Será Bonito", LocalDate.now().plusDays(15), LocalTime.of(19, 30), "Movistar Arena", "Pepsi", 80, 200, 300);
        eventos.add(ev2);

        // Evento 3
        Evento ev3 = new Evento("Feid - Ferxxo Vol. 1", LocalDate.now().plusDays(20), LocalTime.of(21, 0), "Coliseo Medellín", "Red Bull", 120, 180, 350);
        eventos.add(ev3);
    }

    private void crearEventoNuevo() {
        // Formulario simple para nuevo evento
        String nombre = JOptionPane.showInputDialog(this, "Nombre del evento:", "Nuevo Evento", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) return;

        String lugar = JOptionPane.showInputDialog(this, "Lugar:", "Nuevo Evento", JOptionPane.QUESTION_MESSAGE);
        if (lugar == null || lugar.trim().isEmpty()) return;

        String patrocinador = JOptionPane.showInputDialog(this, "Patrocinador:", "Nuevo Evento", JOptionPane.QUESTION_MESSAGE);
        if (patrocinador == null) patrocinador = "Sin patrocinador";

        Evento nuevo = new Evento(nombre, LocalDate.now().plusDays(7), LocalTime.of(20, 0), lugar, patrocinador, 100, 200, 300);
        eventos.add(nuevo);
    }

    private void reservarBoletas(Zona zona, int cantidad, String nombreZona) {
        if (eventos.isEmpty()) {
            lblEstado.setText("Estado: Crea un evento primero");
            return;
        }

        Evento ultimoEvento = eventos.get(eventos.size() - 1);
        boolean exito = ultimoEvento.reservarBoletas(zona, cantidad);

        if (exito) {
            lblEstado.setText("Estado: Reserva exitosa - " + cantidad + " boletas en " + nombreZona);
        } else {
            lblEstado.setText("Estado: No hay suficientes boletas en " + nombreZona);
        }

        actualizarListaEventos();
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
        SwingUtilities.invokeLater(() -> new VentanaInicial().setVisible(true));
    }
}