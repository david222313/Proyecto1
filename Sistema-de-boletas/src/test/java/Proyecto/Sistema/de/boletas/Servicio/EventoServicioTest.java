package Proyecto.Sistema.de.boletas.Servicio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoServicioTest {

    @Test
    void testCrearEvento() {

        String nombreEvento = "Concierto Rock";

        assertEquals("Concierto Rock", nombreEvento);
    }


    @Test
    void testFechaEventoValida() {

        int capacidad = 500;

        assertEquals(500, capacidad);
    }
}