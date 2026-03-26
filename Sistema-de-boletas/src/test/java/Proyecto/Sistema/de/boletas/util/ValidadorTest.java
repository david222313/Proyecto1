package Proyecto.Sistema.de.boletas.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorTest {

    @Test
    void testNumeroValido() {

        boolean resultado = Validador.esNumero("12345");

        assertTrue(resultado);
    }


    @Test
    void testNumeroInvalido() {

        boolean resultado = Validador.esNumero("ABC123");

        assertFalse(resultado);
    }


    @Test
    void testNumeroVacio() {

        boolean resultado = Validador.esNumero("");

        assertFalse(resultado);
    }
}