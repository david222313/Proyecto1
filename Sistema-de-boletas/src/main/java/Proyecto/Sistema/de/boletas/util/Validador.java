package Proyecto.Sistema.de.boletas.util;

public class Validador {

    public static boolean esNumero(String texto){

        try{
            Long.parseLong(texto);

            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}