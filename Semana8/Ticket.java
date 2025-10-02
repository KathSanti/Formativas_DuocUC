package Formativas_DuocUC.Semana8;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ticket {

    static ArrayList<Ticketdata> Tickets = new ArrayList<>();

    static class Ticketdata {
        String asiento;
        String zona;
        int precioOriginal;
        double descuento;
        double totalPagar;
        int edadCliente;

        public Ticketdata(String asiento, String zona, int precioOriginal, double descuento, double totalPagar, int edadCliente) {
            this.asiento = asiento;
            this.zona = zona;
            this.precioOriginal = precioOriginal;
            this.descuento = descuento;
            this.totalPagar = totalPagar;
            this.edadCliente = edadCliente;
        }

    }

    private Ticketdata Descuento (Scanner sc, int precioAsiento, String codigoAsiento, String zonaAsiento){

        int edad = 0;
        boolean edadValida = false;
        double descuentoaplicado= 0;

        while (!edadValida) {
            try {
                System.out.print("\n");
                System.out.print("================ PROMOCIONES ===============");
                System.out.print("\n");
                System.out.print("\na) Descuento del 10% por ser estudiantes ");
                System.out.print("\nb) Descuento del 15% Adulto Mayor ");
                System.out.print("\n");
                System.out.print("\nPor favor ingrese su edad pata obtener tu descuento: ");
                edad = sc.nextInt();
                sc.nextLine();

                if (edad <= 0) {
                    System.out.println("Error: La edad no puede ser negativa o cero.");
                } else if (edad > 120) {
                    System.out.println("Error: La edad no puede ser mayor a 120 años.");
                } else {
                    edadValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
                sc.nextLine();
            }// Calcular descuento

            if (edad >= 60) {
                descuentoaplicado = 0.15;
                System.out.println(" " );
                System.out.println("Descuento del 15% aplicado (adulto mayor)");
                System.out.println(" " );

            } else if (edad<=25) {
                descuentoaplicado = 0.10;
                System.out.println(" " );
                System.out.println("Descuento del 10% aplicado (Estudiante)");
                System.out.println(" " );


            } else {
                descuentoaplicado = 0;
                System.out.println("Sin descuento aplicado");
            }


        }

        // Cálculos
        double descuento = precioAsiento * descuentoaplicado;   // Variable Local
        double totalapagar = precioAsiento - descuento;         // Variable Local

        System.out.println("========= RESUMEN DE COMPRA =========" );
        System.out.println("Precio original: $" + precioAsiento);
        System.out.println("Descuento: $" + descuento);
        System.out.println("Total a pagar: $" + totalapagar);
        System.out.println("=====================================" );
        System.out.println(" " );

        return new Ticketdata (codigoAsiento, zonaAsiento, precioAsiento, descuento, totalapagar, edad);

    }

    //asociarlo con ticketsale

    public void ResumenVentas() {

        if (Tickets.size() == 0) {
            System.out.println("=====================================");
            System.out.println("    Aún no hay ventas realizadas.    ");
            System.out.println("=====================================");
            return;
        }
        System.out.println("\n==========  RESUMEN VENTAS  ==========");
        double totalGeneral = 0;
        double totalDescuento = 0;// Variable Local

        for (int i = 0; i < Tickets.size(); i++) {
            Ticket.Ticketdata ticketdata = Tickets.get(i);
            totalGeneral += ticketdata.totalPagar;
            totalDescuento += ticketdata.descuento;
        }

        System.out.println("TOTAL GENERAL: $" + totalGeneral);
        System.out.println("TOTAL DESCUENTOS: $" + totalDescuento);
        System.out.println("Cantidad de boletas: " + Tickets.size());
        System.out.println("=======================================");
    }

    public void imprimirBoletas (){

        if (Tickets.size() == 0) {
            System.out.println("=====================================");
            System.out.println("       No hay compras realizadas.    ");
            System.out.println("=====================================");
            return;
        }


        System.out.println("\n==========  Teatro Moro ==============");

        for (int i = 0; i < Tickets.size(); i++) {
            Ticket.Ticketdata ticketdata = Tickets.get(i);
            System.out.println("\n--- Boleta #" + (i + 1) + " ---");
            System.out.println("Asiento        : " + ticketdata.asiento);
            System.out.println("Zona           : " + ticketdata.zona);
            System.out.println("Edad cliente   : " +ticketdata.edadCliente);
            System.out.println("Precio original: $" + ticketdata.precioOriginal);
            System.out.println("Descuento      : $" + ticketdata.descuento);
            System.out.println("Total a pagar  : $" + ticketdata.totalPagar);


        }

        System.out.println("\n=======================================");

        System.out.println("\n¡Gracias por su visita al teatro Moro!");

        System.out.println("\n=======================================");

    }












}//THE END CLASS TICKET
