package org.example;;

public enum Zona {
    A(200),
    B(100),
    C(50);

    private final int precio;

    Zona(int precio) {
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return name() + " ($" + precio + ")";
    }
}