package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZonaTest {

    @Test
    void precioDeCadaZonaEsCorrecto() {
        assertEquals(200, Zona.A.getPrecio(), "Zona A debe costar 200");
        assertEquals(100, Zona.B.getPrecio(), "Zona B debe costar 100");
        assertEquals(50, Zona.C.getPrecio(), "Zona C debe costar 50");
    }

    @Test
    void toStringDevuelveFormatoEsperado() {
        assertEquals("A ($200)", Zona.A.toString());
        assertEquals("B ($100)", Zona.B.toString());
        assertEquals("C ($50)", Zona.C.toString());
    }
}