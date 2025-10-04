package Formativas_DuocUC.Semana8;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static Formativas_DuocUC.Semana8.TicketSale.ventasPorEvento;

public class Ticket {

    public static ArrayList<Ticketdata> Tickets = new ArrayList<>();

    public static class Ticketdata {
        String asiento;
        String zona;
        int precioOriginal;
        double descuento;
        double totalPagar;
        int rutCliente;
        String nombreCliente;
        int edadCliente;
        int idticket;
        int idEvent;
        public String evento;
        public String fecha;

        public Ticketdata(String asiento, String zona, int precioOriginal, double descuento,
                          double totalPagar, int edadCliente, int rutCliente, String nombreCliente,
                          int idticket, int idEvent, String evento, String fecha) {
            this.asiento = asiento;
            this.zona = zona;
            this.precioOriginal = precioOriginal;
            this.descuento = descuento;
            this.totalPagar = totalPagar;
            this.rutCliente = rutCliente;
            this.nombreCliente = nombreCliente;
            this.edadCliente = edadCliente;
            this.idticket = idticket;
            this.idEvent = idEvent;
            this.evento = evento;
            this.fecha = fecha;
        }
    }

    public Ticketdata Descuento(Scanner sc, int precioAsiento, String codigoAsiento,
                                String zonaAsiento, String evento, String fecha) {
        int edad = 0;
        boolean edadValida = false;
        double descuentoaplicado = 0;

        while (!edadValida) {
            try {
                System.out.print("\n");
                System.out.print("================ PROMOCIONES ===============");
                System.out.print("\n");
                System.out.print("\na) Descuento del 10% por ser estudiantes ");
                System.out.print("\nb) Descuento del 15% Adulto Mayor ");
                System.out.print("\n");
                System.out.print("\nPor favor ingrese su edad para obtener tu descuento: ");
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
            }
        }

        // Calcular descuento
        if (edad >= 60) {
            descuentoaplicado = 0.15;
            System.out.println(" ");
            System.out.println("Descuento del 15% aplicado (adulto mayor)");
            System.out.println(" ");
        } else if (edad <= 25) {
            descuentoaplicado = 0.10;
            System.out.println(" ");
            System.out.println("Descuento del 10% aplicado (Estudiante)");
            System.out.println(" ");
        } else {
            descuentoaplicado = 0;
            System.out.println("Sin descuento aplicado");
        }

        // Cálculos
        double descuento = precioAsiento * descuentoaplicado;
        double totalapagar = precioAsiento - descuento;

        System.out.println("========= RESUMEN DE COMPRA =========");
        System.out.println("Precio original: $" + precioAsiento);
        System.out.println("Evento: " + evento);
        System.out.println("Fecha: " + fecha);
        System.out.println("Descuento: $" + descuento);
        System.out.println("Total a pagar: $" + totalapagar);
        System.out.println("=====================================");
        System.out.println(" ");

        // Solicitar datos del cliente
        System.out.print("Ingrese su RUT (sin puntos ni guión): ");
        int rut = sc.nextInt();

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        return new Ticketdata(codigoAsiento, zonaAsiento, precioAsiento, descuento,
                totalapagar, edad, rut, nombre, Tickets.size() + 1, 0, evento, fecha);
    }



    public static void mostrarResumenGeneral() {
        System.out.println("\n=== RESUMEN GENERAL DE VENTAS ===");
        if (ventasPorEvento.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("    Aún no hay ventas realizadas.    ");
            System.out.println("=====================================");
            return;
        }

        double totalGeneral = 0;
        int asientosTotales = 0;

        for (TicketSale.VentaPorEvento venta : ventasPorEvento) {
            System.out.println("Evento: " + venta.evento);
            System.out.println("Fecha: " + venta.fecha);
            System.out.println("Asientos vendidos: " + venta.getCantidadAsientos());
            System.out.println("Total: $" + venta.total);
            System.out.println("--------------------------------");

            totalGeneral += venta.total;
            asientosTotales += venta.getCantidadAsientos();
        }

        System.out.println("TOTAL GENERAL:");
        System.out.println("Asientos vendidos: " + asientosTotales);
        //agregar descuentos
        System.out.println("Recaudación total: $" + totalGeneral);
        System.out.println("=================================");
    }

    public static void imprimirBoletas() {
        if (Tickets.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("       No hay compras realizadas.    ");
            System.out.println("=====================================");
            return;
        }

        System.out.println("\n==========  Teatro Moro ==============");

        for (int i = 0; i < Tickets.size(); i++) {
            Ticketdata ticketdata = Tickets.get(i);
            System.out.println("\n--- Boleta #" + (i + 1) + " ---");
            System.out.println("N° de compra   : " + ticketdata.idticket);
            System.out.println("Evento         : " + ticketdata.evento);
            System.out.println("Fecha          : " + ticketdata.fecha);
            System.out.println("Asiento        : " + ticketdata.asiento);
            System.out.println("Rut            : " + ticketdata.rutCliente);
            System.out.println("Nombre         : " + ticketdata.nombreCliente);
            System.out.println("Zona           : " + ticketdata.zona);
            System.out.println("Edad cliente   : " + ticketdata.edadCliente);
            System.out.println("Precio original: $" + ticketdata.precioOriginal);
            System.out.println("Descuento      : $" + ticketdata.descuento);
            System.out.println("Total a pagar  : $" + ticketdata.totalPagar);
        }

        System.out.println("\n=======================================");
        System.out.println("¡Gracias por su visita al teatro Moro!");
        System.out.println("=======================================");
    }
}