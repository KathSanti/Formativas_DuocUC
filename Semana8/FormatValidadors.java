package Formativas_DuocUC.Semana8;

import java.util.Scanner;

public class FormatValidadors {

    public boolean validarFormatoCodigo(String coodenadaAsiento) {
        return coodenadaAsiento.length() == 2 &&
                Character.isLetter(coodenadaAsiento.charAt(0)) && //verifica que el primer digito sea letra
                Character.isDigit(coodenadaAsiento.charAt(1)); //verifica que el segundo digito sea nro
    }

    public boolean validarRangoAsiento(int fila, int columna) {
        return fila >= 0 && fila < 5 && columna >= 0 && columna < 6;
    }

    public static int ValidarNroEntero(Scanner sc) {
        while (true) {
            String linea = sc.nextLine().trim();
            sc.nextLine();

            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException var2) {
                System.out.println("Opción invalida, por favor intente nuevamente");
            }
        }
    }




}
