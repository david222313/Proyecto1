package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    private Evento evento;

    @BeforeEach
    void setUp() {
        evento = new Evento(
                "Concierto Prueba",
                LocalDate.of(2025, 6, 15),
                LocalTime.of(20, 0),
                "Estadio Prueba",
                "Patrocinador Prueba",
                100, 200, 300
        );
    }

    @Test
    void gettersDevuelvenValoresCorrectos() {
        assertEquals("Concierto Prueba", evento.getNombre());
        assertEquals(LocalDate.of(2025, 6, 15), evento.getFecha());
        assertEquals(LocalTime.of(20, 0), evento.getHora());
        assertEquals("Estadio Prueba", evento.getLugar());
        assertEquals("Patrocinador Prueba", evento.getPatrocinador());
        assertEquals(100, evento.getBoletasDisponibles(Zona.A));
    }

    @Test
    void reservarBoletasReduceStockCuandoHayDisponibilidad() {
        boolean reservado = evento.reservarBoletas(Zona.A, 30);

        assertTrue(reservado);
        assertEquals(70, evento.getBoletasDisponibles(Zona.A));
        assertEquals(200, evento.getBoletasDisponibles(Zona.B)); // no cambió
    }

    @Test
    void reservarBoletasDevuelveFalseCuandoNoHayStockSuficiente() {
        boolean reservado = evento.reservarBoletas(Zona.B, 250); // más de 200

        assertFalse(reservado);
        assertEquals(200, evento.getBoletasDisponibles(Zona.B)); // no cambió
    }

    @Test
    void reservarBoletasDevuelveFalseCuandoCantidadEsNegativaOCero() {
        assertFalse(evento.reservarBoletas(Zona.C, 0));
        assertFalse(evento.reservarBoletas(Zona.C, -5));
        assertEquals(300, evento.getBoletasDisponibles(Zona.C)); // no cambió
    }

    @Test
    void liberarBoletasAumentaElStockCorrectamente() {
        evento.reservarBoletas(Zona.A, 40);
        evento.liberarBoletas(Zona.A, 40);

        assertEquals(100, evento.getBoletasDisponibles(Zona.A));
    }
}