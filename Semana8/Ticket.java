package Formativas_DuocUC.Semana8;

import java.util.*;


import static Formativas_DuocUC.Semana8.ShowEvents.GestorEventos.sc;
import static Formativas_DuocUC.Semana8.TicketSale.ventasPorEvento;

public class Ticket {


    //Array para almacenar la información de forma variable por los usuarios

    public static ArrayList<Ticketdata> Tickets = new ArrayList<>();


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
        int idEvent;
        public String evento;
        public String fecha;

        public Ticketdata(String asiento, String zona, int precioOriginal, double descuento,  double totalPagar, int edadCliente, int rutCliente, String nombreCliente, int idEvent, String evento, String fecha) {

            this.asiento = asiento;
            this.zona = zona;
            this.precioOriginal = precioOriginal;
            this.descuento = descuento;
            this.totalPagar = totalPagar;
            this.rutCliente = rutCliente;
            this.nombreCliente = nombreCliente;
            this.edadCliente = edadCliente;
            this.idEvent = idEvent;
            this.evento = evento;
            this.fecha = fecha;




            this.idticket = generarNumeroOrden(idEvent, rutCliente, asiento);
        }


        public String getNumeroOrden() {
            return this.idticket;
        }
    }

    public Ticketdata AlamacenaDatosTickets(Scanner sc, int precioAsiento, String codigoAsiento, String zonaAsiento, String evento, String fecha, int idEvent) {
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


        return new Ticketdata(codigoAsiento, zonaAsiento, precioAsiento, descuento, totalapagar, edad, rut, nombre, idEvent, evento, fecha);
    }


    public static void mostrarResumenGeneral() {
        System.out.println("\n==== RESUMEN GENERAL DE VENTAS ====");
        if (ventasPorEvento.isEmpty()) {
            System.out.println("    Aún no hay ventas realizadas.    ");
            System.out.println("=====================================");
            return;
        }

        double totalGeneral = 0;
        double totalDescuentosGeneral = 0;
        int asientosTotales = 0;

        for (TicketSale.VentaPorEvento venta : ventasPorEvento) {
            System.out.println("Evento            : " + venta.evento);
            System.out.println("Fecha             : " + venta.fecha);
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
            Ticketdata ticketdata = Tickets.get(i);
            System.out.println("\n--- Boleta #" + (i + 1) + " ---");
            System.out.println("N° de orden    : " + ticketdata.getNumeroOrden());
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

    public static String generarNumeroOrden(int idEvent, int rutCliente, String asiento) {
        return String.format("%d-%s-%d", idEvent, asiento, rutCliente); //Texto esturcturado visto en taller 2
    }

    public static void confirmarCompraAsientos() {
        for (Ticketdata ticket : Tickets) {
            MapaAsientosEstados.confirmarAsientoComoOcupado(ticket.evento, ticket.fecha, ticket.asiento);
        }

        // Actualizar resumen de ventas
        actualizarVentasPorEvento();
    }

    private static void actualizarVentasPorEvento() {
        for (Ticketdata ticket : Tickets) {
            boolean encontrado = false;

            // Buscar si ya existe una venta para este evento
            for (TicketSale.VentaPorEvento venta : ventasPorEvento) {
                if (venta.evento.equals(ticket.evento) && venta.fecha.equals(ticket.fecha)) {
                    venta.total += ticket.totalPagar;
                    venta.totalDescuentos += ticket.descuento;
                    venta.cantidadAsientos++;
                    encontrado = true;
                    break;
                    //Si existe no lo actualiza
                }
            }

            //Si no existe lo añade a lista para mostrar en resumen de ventas


            if (!encontrado) {
                TicketSale.VentaPorEvento nuevaVenta = new TicketSale.VentaPorEvento(ticket.evento, ticket.fecha);
                nuevaVenta.total = ticket.totalPagar;
                nuevaVenta.totalDescuentos = ticket.descuento;
                nuevaVenta.cantidadAsientos = 1;
                ventasPorEvento.add(nuevaVenta);
            }
        }
    }


    //Metodos para eliminar boletas por ID desde la opción tres en menu

    public static boolean eliminarBoletaPorID() {
        if (Tickets.isEmpty()) {
            System.out.println("No hay boletas para eliminar."); //Si no hay reservas mostrar este mensaje
            return false;
        }

        System.out.print("Ingrese número de boleta: "); //ingresa id especifico de su evento
        String id = sc.nextLine();

        // Buscar la boleta
        for (int i = 0; i < Tickets.size(); i++) {
            Ticketdata ticket = Tickets.get(i);
            if (id.equals(ticket.idticket)) {
                // Liberar el asiento
                liberarAsientoCompleto(ticket.asiento, ticket.evento, ticket.fecha);

                // Eliminar de las ventas por evento
                eliminarDeVentasPorEvento(ticket);

                // Eliminar la boleta
                Tickets.remove(i);

                System.out.println("Boleta #" + id + " eliminada correctamente");
                System.out.println("Asiento " + ticket.asiento + " liberado");
                return true;
            }
        }

        System.out.println("No se encontró la boleta con ID: " + id);
        System.out.println("Para poder ver el número de orden de tu boleta" + HomeTeatroMoro.bucleMenuVolver(sc));
        return false;
    }

    //  liberar asiento en todos los metodos y listas
    private static void liberarAsientoCompleto(String coordenadaAsiento, String evento, String fecha) {
        try {
            char filaChar = coordenadaAsiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

            if (fila >= 0 && fila < 5 && columna >= 0 && columna < 6) {
                // Liberar en mapa general
                SeatingMap.asientos[fila][columna] = false; //cambia estado
                SeatingMap.reservaPendiente[fila][columna] = false;//cambia estado

                // Liberar en estado específico del evento
                MapaAsientosEstados.EstadoAsientosEvento estado = MapaAsientosEstados.obtenerEstadoAsientos(evento, fecha);
                estado.asientos[fila][columna] = false;
                estado.reservaPendiente[fila][columna] = false;
                MapaAsientosEstados.guardarEstadoActual(evento, fecha);
            }
        } catch (Exception e) {
            System.out.println("Error al liberar el asiento: " + e.getMessage());
        }

    }

    // eliminar la boleta de las ventas por evento
    private static void eliminarDeVentasPorEvento(Ticketdata ticket) {
        // Buscar la venta correspondiente al evento y fecha
        for (int i = 0; i < ventasPorEvento.size(); i++) {
            TicketSale.VentaPorEvento venta = ventasPorEvento.get(i);

            if (venta.evento.equals(ticket.evento) && venta.fecha.equals(ticket.fecha)) {
                // Restar los valores de esta boleta de la venta
                venta.total -= ticket.totalPagar;
                venta.totalDescuentos -= ticket.descuento;
                venta.cantidadAsientos--;

                // Si ya no hay asientos en esta venta, eliminarla completamente
                if (venta.cantidadAsientos <= 0) {
                    ventasPorEvento.remove(i);
                    System.out.println("Venta del evento " + ticket.evento + " eliminada (sin asientos)");
                } else {
                    System.out.println("Venta actualizada: " + venta.cantidadAsientos + " asientos restantes");
                }
                break;
            }
        }
    }
}


