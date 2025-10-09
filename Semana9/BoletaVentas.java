package Formativas_DuocUC.Semana9;



import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class BoletaVentas {

    //Array para almacenar la información de forma variable por los usuarios

    public static ArrayList<BoletaVentas.Ticketdata> Tickets = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);


    //Clase para manejar los elementos del ticket

    public static class Ticketdata {
        String asiento;
        String zona;
        int precioOriginal;
        double descuento;
        double totalPagar;
        int rutCliente;
        String nombreCliente;
        int edadCliente;
        String idticket;

        public Ticketdata(String asiento, String zona, int precioOriginal, double descuento,  double totalPagar, int edadCliente, int rutCliente, String nombreCliente) {

            this.asiento = asiento;
            this.zona = zona;
            this.precioOriginal = precioOriginal;
            this.descuento = descuento;
            this.totalPagar = totalPagar;
            this.rutCliente = rutCliente;
            this.nombreCliente = nombreCliente;
            this.edadCliente = edadCliente;

        }


    }

    public BoletaVentas.Ticketdata AlamacenaDatosTickets(Scanner sc, int precioAsiento, String codigoAsiento, String zonaAsiento) {
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

        // Solicitar datos del cliente
        System.out.print("Ingrese su RUT (sin puntos ni guión): ");
        int rut = sc.nextInt();
        sc.nextLine();

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        // Cálculos
        double descuento = precioAsiento * descuentoaplicado;
        double totalapagar = precioAsiento - descuento;


        return new BoletaVentas().Ticketdata(codigoAsiento, zonaAsiento, precioAsiento, descuento, totalapagar, edad, rut, nombre);
    }


    public static void mostrarResumenGeneral() {
        System.out.println("\n==== RESUMEN GENERAL DE VENTAS ====");
        if (ventaGeneral.isEmpty()) {
            System.out.println("    Aún no hay ventas realizadas.    ");
            System.out.println("=====================================");
            return;
        }

        double totalGeneral = 0;
        double totalDescuentosGeneral = 0;
        int asientosTotales = 0;

        for (GestionReservas.VentaGeneral venta : ventaGeneral) {
            System.out.println("Asientos vendidos : " + venta.getCantidadAsientos());
            System.out.println("Descuentos        : $" + venta.totalDescuentos);
            System.out.println("Total             : $" + venta.total);
            System.out.println("--------------------------------");

            totalGeneral += venta.total;
            totalDescuentosGeneral += venta.getTotalDescuentos();
            asientosTotales += venta.getCantidadAsientos();
        }

        System.out.println("============= Total General =================");
        System.out.println("Asientos vendidos : " + asientosTotales);
        System.out.println("Descuentos        : $" + totalDescuentosGeneral);
        System.out.println("Recaudación total : $" + totalGeneral);
        System.out.println("=============================================");
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
            BoletaVentas.Ticketdata ticketdata = Tickets.get(i);
            System.out.println("\n--- Boleta #" + (i + 1) + " ---");
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

        System.out.print("¿Desea confirmar la compra de tus asientos reservados? (S/N): ");
        char respuestaCompra = sc.next().toUpperCase().charAt(0);
        sc.nextLine();

        if (respuestaCompra == 'S') {
            confirmarCompraAsientos();
            System.out.println("¡Compra confirmada! Los asientos ahora están ocupados.");
            Tickets.clear();
        } else {
            System.out.println("Compra no confirmada. Los asientos permanecen como reservados.");
            System.out.println("Si deseas eliminar tu reserva dirigete en menú opción : 3) Eliminar rerserva");
            System.out.println("Allí con tú Número de orden podrás eliminarla");
            System.out.println(" ");
        }
    }


    public static void confirmarCompraAsientos() {
        for (BoletaVentas.Ticketdata ticket : Tickets) {
            MapaEstadoAsiento.marcarAsientoComoOcupado(ticket.asiento);
        }


    }

}
