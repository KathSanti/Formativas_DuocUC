package Formativas_DuocUC.Semana8;

public class SeatingMap {

    static boolean[][] asientos = new boolean[5][6];
    static boolean [][] reservaPendiente = new boolean[5][6];
    static int[] preciosUnitarios = {20000, 18000, 16000, 14000, 11000};
    static String[] RowEntryType = {"VIP", "Platea Alta", "Platea Baja", "Palcos", "Balcon"};

    public static void MostrarMapaAsientos() {


        System.out.println("\n==========  RESERVA DE ASIENTOS  ==========");
        System.out.println("\n   [ ]Libre - [R] Rerservado - [X] Ocupado ");
        System.out.println(" ");
        System.out.println("   " + "  1    2    3    4    5    6");

        char[] letrasFila = {'A', 'B', 'C', 'D', 'E'};

        for (int i = 0; i < asientos.length; i++) {
            System.out.print(letrasFila[i] + " | ");
            for (int j = 0; j < asientos[i].length; j++) {
                if  (asientos[i][j]) {
                    System.out.print("[X]  ");
                }else if (reservaPendiente[i][j]) {
                    System.out.print("[R]  ");
                }else{
                    System.out.print("[ ]  ");
                }

            }

            System.out.println("($" + "(" + preciosUnitarios[i] +")" + " Zona: " + RowEntryType[i] );

        }
        System.out.println();
    }

}
