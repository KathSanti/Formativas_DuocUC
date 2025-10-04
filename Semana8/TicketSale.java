package Formativas_DuocUC.Semana8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketSale {

    public static class VentaPorEvento {
        public String evento;
        public String fecha;
        public List<Ticket.Ticketdata> tickets;
        public double total;

        public VentaPorEvento(String evento, String fecha) {
            this.evento = evento;
            this.fecha = fecha;
            this.tickets = new ArrayList<>();
            this.total = 0;
        }

        public void agregarTicket(Ticket.Ticketdata ticket) {
            this.tickets.add(ticket);
            this.total += ticket.totalPagar;
        }

        public int getCantidadAsientos() {
            return tickets.size();
        }
    }

    public static List<VentaPorEvento> ventasPorEvento = new ArrayList<>();

    public static void VentaEntradas(Scanner sc, String evento, String fecha) {
        boolean continuarComprando = true;
        List<Ticket.Ticketdata> ticketsVentaActual = new ArrayList<>();

        while (continuarComprando) {
            SeatingMap.MostrarMapaAsientos();
            FormatValidadors validadors = new FormatValidadors();

            System.out.print("Ingrese asiento (ej: A1, B5, E6): ");
            String coordenadaAsiento = sc.nextLine().trim().toUpperCase();

            if (!validadors.validarFormatoCodigo(coordenadaAsiento)) {
                System.out.println("Código inválido. Formato correcto: Letra (A-E) + Número (1-6)");
                continue;
            }

            char filaChar = coordenadaAsiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

            if (!validadors.validarRangoAsiento(fila, columna)) {
                System.out.println("Asiento fuera de rango (fila A-E, columnas 1-6)");
                continue;
            }

            if (SeatingMap.asientos[fila][columna]) {
                System.out.println("[x] Ese asiento ya está ocupado, elija otro.");
                continue;
            }

            if (SeatingMap.reservaPendiente[fila][columna]) {
                System.out.println("[R] Ese asiento ya está reservado, escoja otro o espere a que se libere");
                continue;
            }

            int precioAsiento = SeatingMap.preciosUnitarios[fila];
            String zonaAsiento = SeatingMap.RowEntryType[fila];
            System.out.println("Asiento " + coordenadaAsiento + " - Precio: $" + precioAsiento + " - Zona: " + zonaAsiento);

            // Preguntar si quiere comprar este asiento
            System.out.print("¿Deseas comprar este asiento? (S/N): ");
            char respuestaCompra = sc.next().charAt(0);
            sc.nextLine();

            if (respuestaCompra != 'S' && respuestaCompra != 's'){
                System.out.println("Compra cancelada para este asiento.");
                continue;
            }

            // Procesar descuento y crear boleta
            Ticket ticketManager = new Ticket();
            Ticket.Ticketdata ticketData = ticketManager.Descuento(sc, precioAsiento, coordenadaAsiento, zonaAsiento, evento, fecha);

            // Guardar información boletas y agregar al listado
            ticketsVentaActual.add(ticketData);
            Ticket.Tickets.add(ticketData);

            // Reservar asiento
            SeatingMap.asientos[fila][columna] = true;
            System.out.println("¡Compra confirmada! Asiento " + coordenadaAsiento + " reservado.");

            // Preguntar si quiere comprar otro asiento
            System.out.print("¿Desea comprar otro asiento? (S/N): ");
            char respuestaContinuar = sc.next().charAt(0);
            sc.nextLine();

            if (respuestaContinuar == 'n' || respuestaContinuar == 'N' ) {
                continuarComprando = false;
                System.out.println("Gracias por su compra!");
                guardarResumenVenta(evento, fecha, ticketsVentaActual);
            }
        }
    }

    public static void guardarResumenVenta(String evento, String fecha, List<Ticket.Ticketdata> tickets) {

        VentaPorEvento ventaExistente = null;
        for (VentaPorEvento venta : ventasPorEvento) {
            if (venta.evento.equals(evento) && venta.fecha.equals(fecha)) {
                ventaExistente = venta;
                break;
            }
        }

        if (ventaExistente == null) {
            ventaExistente = new VentaPorEvento(evento, fecha);
            ventasPorEvento.add(ventaExistente);
        }

        // Agregar todos los tickets a la venta
        for (Ticket.Ticketdata ticket : tickets) {
            ventaExistente.agregarTicket(ticket);
        }

        System.out.println("\n=== RESUMEN DE VENTA ===");
        System.out.println("Evento: " + evento);
        System.out.println("Fecha: " + fecha);
        System.out.println("Asientos vendidos: " + tickets.size());
        System.out.println("Total venta: $" + ventaExistente.total);
        System.out.println("========================");
    }


}