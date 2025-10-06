package Formativas_DuocUC.Semana8;

import java.util.Scanner;

public class HomeTeatroMoro {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do{

            int opcionMenu;


            System.out.println("========================================================================");
            System.out.println("||                     BIENVENIDO AL TEATRO  MORO                     ||");
            System.out.println("========================================================================");
            System.out.println(" ");
            System.out.println("Por favor escoge una opción");
            System.out.println("1) Rerserva de entradas");
            System.out.println("2) Resumen de Ventas");
            System.out.println("3) Imprimir boleta");
            System.out.println("4) Eliminar reserva");
            System.out.println("5) Salir");

            System.out.print("Opción: ");
            opcionMenu = FormatValidadors.ValidarNroEntero(sc);

            switch (opcionMenu){
                case 1:
                    ShowEvents.GestorEventos.inicializarEventos();
                    ShowEvents.GestorEventos.mostrarEventos();

                    if (!bucleMenuVolver(sc)) {
                        salir = true;
                    }
                    break;

                case 2:
                    Ticket.mostrarResumenGeneral();
                    if (!bucleMenuVolver(sc)) {
                        salir = true;
                    }
                    break;
                case 3:
                    Ticket.imprimirBoletas();
                    if (!bucleMenuVolver(sc)) {
                        salir = true;
                    }
                    break;
                case 4:
                    Ticket.eliminarBoletaPorID();
                    break;
                case 5:
                    salir = bucleopcioncuatro(sc);
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");


            }




        }while(!salir);

        sc.close();



    }

    private static boolean bucleMenuVolver(Scanner sc) {
        char confirma;

        System.out.print("¿Deseas volver al menú principal? (S/N): ");
        confirma = sc.next().charAt(0);
        sc.nextLine();

        while (confirma != 'S' && confirma != 's' && confirma != 'N' && confirma != 'n') {
            System.out.println("Opción no válida");
            System.out.print("Ingrese una opción válida (S/N): ");
            confirma = sc.next().charAt(0);
            sc.nextLine();
            System.out.println(" ");
        }

        if (confirma == 'N' || confirma == 'n') {
            System.out.println("Gracias por su compra. ¡Hasta pronto!");
            return false; // Mensaje de despedida al usuario
        }

        return true; // Volver al menú
    }


    private static boolean bucleopcioncuatro(Scanner sc) {

        char salirMenu;

        System.out.print("¿Estás seguro que deseas salir? (S/N): ");
        salirMenu = sc.next().charAt(0);
        sc.nextLine();

        while (salirMenu != 'S' && salirMenu != 's' && salirMenu != 'N' && salirMenu != 'n') {
            System.out.println("Opción no válida");
            System.out.print("Ingrese una opción válida (S/N): ");
            salirMenu = sc.next().charAt(0);
            sc.nextLine();
        }



        if (salirMenu == 'N' || salirMenu == 'n') {
            System.out.println("Volviendo al menú principal");
            return false;
        }

        if (salirMenu == 'S' || salirMenu == 's') {
            System.out.println("Gracias por visitarnos. ¡Hasta pronto! ");
        }

        return true;

    }



}








