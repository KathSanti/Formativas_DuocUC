package Formativas_DuocUC.Semana9;

import com.sun.source.tree.IfTree;

import java.util.Scanner;

public class ValidacionesEntradas {

    public boolean validarFormatoCodigoEntrada(String coodenadaAsiento) {

        if (coodenadaAsiento.length() != 2) return false;

        char letra = coodenadaAsiento.charAt(0);
        char numero = coodenadaAsiento.charAt(1);

        return letra >= 'A' && letra <= 'E' && numero >= '0' && numero <= '6';

    }

    public static int ValidarNroEntero(Scanner sc) {
        while (true) {
            String linea = sc.nextLine().trim();

            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException var2) {
                System.out.println("Opción invalida, por favor intente nuevamente");
            }
        }
    }

    public static int validarangorut(Scanner sc) {
        while (true) {
            String linea = sc.nextLine().trim();
            if (linea.matches("\\d{8,9}")) {
                try {
                    return Integer.parseInt(linea);
                } catch (NumberFormatException e) {
                    System.out.println("Formato Rut invalido, por favor intente nuevamente");
                }

            }else {
                System.out.println("Error: El RUT debe tener entre 8 y 9 dígitos. Ingrese nuevamente:");
            }
        }

    }
}
