package Formativas_DuocUC.Semana9;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static Formativas_DuocUC.Semana9.GestionReservas.VentaGeneral;


public class BoletaVentas {

    public static HashMap<String, Ticketdata> ticketsMap = new HashMap<>();

    //Array para almacenar la información de forma variable por los usuarios

    public static ArrayList<BoletaVentas.Ticketdata> Tickets = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);


    //Contador para generar clase unicas y no afecte a la subprocesos

    private static AtomicInteger contadorTickets = new AtomicInteger(1000);


    //Clase para manejar los elementos del ticket

    public static class Ticketdata {
        String idTicket;
        String asiento;
        String zona;
        int precioOriginal;
        double descuento;
        double totalPagar;
        int rutCliente;
        String nombreCliente;
        int edadCliente;
        char sexo;

        public Ticketdata(String idTicket, String asiento, String zona, int precioOriginal, double descuento,  double totalPagar, int edadCliente, int rutCliente, String nombreCliente, char sexo) {

            this.idTicket = idTicket;
            this.asiento = asiento;
            this.zona = zona;
            this.precioOriginal = precioOriginal;
            this.descuento = descuento;
            this.totalPagar = totalPagar;
            this.rutCliente = rutCliente;
            this.nombreCliente = nombreCliente;
            this.edadCliente = edadCliente;
            this.sexo = sexo;

        }

        public String getNumeroTicket() {
            return idTicket;
        }

        public static String numerodeOrden() {
            return "TKT-" + contadorTickets.getAndIncrement();
        }

    }




    public BoletaVentas.Ticketdata AlamacenaDatosTickets(Scanner sc,  int precioAsiento, String codigoAsiento, String zonaAsiento) {
        int edad = 0;
        char sexo = 'a';
        boolean edadValida = false;
        double descuentoaplicado;

        while (!edadValida) {
            try {
                System.out.print("\n");
                System.out.print("================ PROMOCIONES ===============");
                System.out.print("\n");
                System.out.print("\na) Descuento del 25% por ser estudiantes ");
                System.out.print("\nb) Descuento del 30% Adulto Mayor ");
                System.out.print("\nb) Descuento del 7% para Mujeres ");
                System.out.print("\nb) Descuento del 5% para niños menores de 8 años");
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
            descuentoaplicado = 0.30;
            System.out.println(" ");
            System.out.println("Descuento del 30% aplicado (adulto mayor)");
            System.out.println(" ");
        } else if (edad <=8 ) {
            descuentoaplicado = 0.05;
            System.out.println(" ");
            System.out.println("Descuento del 5% aplicado (Niño)");
            System.out.println(" ");

        } else if (edad >=9 && edad <= 25) {
            descuentoaplicado = 0.25;
            System.out.println(" ");
            System.out.println("Descuento del 25% aplicado (Estudiante)");
            System.out.println(" ");
        } else {
            descuentoaplicado = 0;
            System.out.println("Sin descuento aplicado");
            System.out.println("Por favor ingresa tu sexo (F/M): ");
            sexo = sc.next().toUpperCase().charAt(0);
            if (sexo == 'M' || sexo == 'm') {
                descuentoaplicado = 0;
                System.out.println("Sin descuento aplicado");
            }else if (sexo == 'F' || sexo == 'f') {
                descuentoaplicado = 0.07;
                System.out.println("Descuento del 7% aplicado");
            }
        }



        // Solicitar datos del cliente
        System.out.print("Ingrese su RUT (sin puntos ni guión): ");
        int rut = ValidacionesEntradas.validarangorut(sc);



        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        String claveUnica = Ticketdata.numerodeOrden();



        // Cálculos
        double descuento = precioAsiento * descuentoaplicado;
        double totalapagar = precioAsiento - descuento;




        return new Ticketdata(claveUnica, codigoAsiento, zonaAsiento, precioAsiento, descuento, totalapagar, edad, rut, nombre,sexo);
    }

    public static  void agregarTicket(Ticketdata ticket) {
        Tickets.add(ticket);
        ticketsMap.put(ticket.getNumeroTicket(), ticket);
        System.out.println("Número de orden generado" + ticket.getNumeroTicket());
    }

    public static void eliminarReserva(){
        if (ticketsMap.isEmpty()) {
            System.out.println("=====================================");
            System.out.println("    No hay reservas para eliminar.   ");
            System.out.println("=====================================");
            return;
        }

        System.out.println("\n======== ELIMINAR RESERVA ========");
        System.out.print("Ingrese el número de ticket: ");
        String numeroTicket = sc.nextLine().trim();

        if (ticketsMap.containsKey(numeroTicket)) {
            Ticketdata ticket = ticketsMap.get(numeroTicket);

            System.out.println("====== Detalle reserva a eliminar ======");
            System.out.println("Número de orden : " + ticket.idTicket);
            System.out.println("Asiento         : " + ticket.asiento);
            System.out.println("Zona            : " + ticket.zona);
            System.out.println("Rut             : " + ticket.rutCliente);
            System.out.println("Nombre          : " + ticket.nombreCliente);
            System.out.println("Total a pagar   : " + ticket.totalPagar);
            System.out.println("=========================================");

            System.out.print("¿Está seguro de eliminar esta reserva? (S/N): ");
            char confirmacion = sc.next().toUpperCase().charAt(0);
            sc.nextLine();

            if (confirmacion == 'S' || confirmacion == 's') {
                // Liberar el asiento en el mapa
                liberarAsiento(ticket.asiento);

                // Eliminar de ambas estructuras
                ticketsMap.remove(numeroTicket);
                Tickets.remove(ticket);

            } else  {
                System.out.println("No fue eliminada su reserva, puedes volver al menú principal para confirmar tu compra");


            }

        }else {
            System.out.println("El número de orden " + numeroTicket +
                    " no existe" + "\nPara poder ver el número de orden de tu boleta");
                HomeTeatroM.bucleMenuVolver(sc);



        }


    }

    private static void liberarAsiento(String asiento) {
        try {
            char filaChar = asiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(asiento.substring(1)) - 1;

            MapaEstadoAsiento.asientos[fila][columna] = false;
            MapaEstadoAsiento.reservaPendiente[fila][columna] = false;

            System.out.println("Asiento " + asiento + " liberado.");
        } catch (Exception e) {
            System.out.println("Error al liberar el asiento, pero la reserva fue eliminada.");
        }
    }

    public static void mostrarResumenGeneral() {
        System.out.println("\n==== RESUMEN GENERAL DE VENTAS ====");
        if (VentaGeneral.isEmpty()) {
            System.out.println("    Aún no hay ventas realizadas.    ");
            System.out.println("=====================================");
            return;
        }

        double totalGeneral = 0;
        double totalDescuentosGeneral = 0;
        int asientosTotales = 0;

        for (VentaGeneral venta : VentaGeneral) {
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

        System.out.println("\n=============  Teatro Moro =================");

        for (int i = 0; i < Tickets.size(); i++) {
            BoletaVentas.Ticketdata ticketdata = Tickets.get(i);
            System.out.println("\n--- Boleta #" + (i + 1) + " ---");
            System.out.println("Número de orden : " + ticketdata.idTicket);
            System.out.println("Asiento         : " + ticketdata.asiento);
            System.out.println("Rut             : " + ticketdata.rutCliente);
            System.out.println("Nombre          : " + ticketdata.nombreCliente);
            System.out.println("Zona            : " + ticketdata.zona);
            System.out.println("Edad cliente    : " + ticketdata.edadCliente);
            System.out.println("Precio original : $" + ticketdata.precioOriginal);
            System.out.println("Descuento       : $" + ticketdata.descuento);
            System.out.println("Total a pagar   : $" + ticketdata.totalPagar);
        }

        System.out.println("\n=============================================");
        System.out.println("  ¡Gracias por su visita al teatro Moro!     ");
        System.out.println("=============================================");

        System.out.print("¿Desea confirmar la compra de tus asientos reservados? (S/N): ");
        char respuestaCompra = sc.next().toUpperCase().charAt(0);
        sc.nextLine();

        if (respuestaCompra == 'S') {

            GestionReservas.VentaGeneral ventaGeneral = new GestionReservas.VentaGeneral();

            for (Ticketdata ticket : Tickets) {
                ventaGeneral.agregarTicket(ticket);
            }

            // Agregar la venta general a la lista de ventas
            VentaGeneral.add(ventaGeneral);

            confirmarCompraAsientos();
            System.out.println("¡Compra confirmada! Los asientos ahora están ocupados.");
            Tickets.clear();
            ticketsMap.clear();

        } else {
            System.out.println("Compra no confirmada. Los asientos permanecen como reservados.");
            System.out.println("Si deseas eliminar tu reserva dirigete en menú opción : 4) Eliminar rerserva");
            System.out.println("Allí con tú Número de orden podrás eliminarla");
            System.out.println(" ");
        }


    }


    public static void confirmarCompraAsientos() {
        for (BoletaVentas.Ticketdata ticket : Tickets) {

            char filaChar = ticket.asiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(ticket.asiento.substring(1)) - 1;

            MapaEstadoAsiento.asientos[fila][columna] = true;
            MapaEstadoAsiento.reservaPendiente[fila][columna] = false;
        }
    }







}
