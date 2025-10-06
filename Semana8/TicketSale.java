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
        public double totalDescuentos;
        public int cantidadAsientos;

        public VentaPorEvento(String evento, String fecha) {
            this.evento = evento;
            this.fecha = fecha;
            this.tickets = new ArrayList<>();
            this.total = 0;
            this.totalDescuentos = 0;
            this.cantidadAsientos = 0;

        }

        public void agregarTicket(Ticket.Ticketdata ticket) {
            this.tickets.add(ticket);
            this.total += ticket.totalPagar;
            this.totalDescuentos += ticket.descuento; // ← NUEVO: acumular descuentos
        }

        public int getCantidadAsientos() {
            return tickets.size();
        }

        public double getTotalDescuentos() {
            return totalDescuentos;
        }
    }

    public static List<VentaPorEvento> ventasPorEvento = new ArrayList<>();

    public static void VentaEntradas(Scanner sc, String evento, String fecha) {
        boolean continuarComprando = true;
        List<Ticket.Ticketdata> ticketsVentaActual = new ArrayList<>();

        int idEvent = obtenerIdEventoPorNombre(evento);

        // Cargar el estado de asientos para este evento y fecha
        MapaAsientosEstados.cargarEstado(evento, fecha);

        while (continuarComprando) {
            // Mostrar mapa específico para este evento
            System.out.println("\n=== MAPA DE ASIENTOS: " + evento + " - " + fecha + " ===");
            SeatingMap.MostrarMapaAsientos();

            FormatValidadors validadors = new FormatValidadors();

            System.out.print("Ingrese asiento (ej: A1, B5, E6): ");
            String coordenadaAsiento = sc.nextLine().trim().toUpperCase();

            if (!validadors.validarFormatoCodigo(coordenadaAsiento)) {
                System.out.println("Código inválido. Formato correcto: Letra (A-E) + Número (1-6)");
                continue;
            }

            // Verificar disponibilidad usando el nuevo sistema
            if (!MapaAsientosEstados.verificarAsientoDisponible(evento, fecha, coordenadaAsiento)) {
                System.out.println("Asiento no disponible para este evento/fecha.");
                continue;
            }

            char filaChar = coordenadaAsiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

            int precioAsiento = SeatingMap.preciosUnitarios[fila];
            String zonaAsiento = SeatingMap.RowEntryType[fila];
            System.out.println("Asiento " + coordenadaAsiento + " - Precio: $" + precioAsiento + " - Zona: " + zonaAsiento);

            // Preguntar si quiere comprar este asiento
            System.out.print("¿Deseas reservar este asiento? (S/N): ");
            char respuestaCompra = sc.next().charAt(0);
            sc.nextLine();

            if (respuestaCompra != 'S' && respuestaCompra != 's') {
                System.out.println("Reserva cancelada para este asiento.");
                continue;
            }

            // Procesar descuento y crear boleta
            Ticket ticketManager = new Ticket();
            Ticket.Ticketdata ticketData = ticketManager.AlamacenaDatosTickets(sc, precioAsiento, coordenadaAsiento, zonaAsiento, evento, fecha, idEvent);

            // Guardar información boletas y agregar al listado
            ticketsVentaActual.add(ticketData);
            Ticket.Tickets.add(ticketData);

            // Reservar asiento (esto modifica SeatingMap temporalmente)
            SeatingMap.reservaPendiente[fila][columna] = true;
            System.out.println("¡Reserva confirmada! Asiento " + coordenadaAsiento + " reservado.");

            // Preguntar si quiere comprar otro asiento
            System.out.print("¿Deseas reservar otro asiento? (S/N): ");
            char respuestaContinuar = sc.next().charAt(0);
            sc.nextLine();

            if (respuestaContinuar == 'n' || respuestaContinuar == 'N') {
                continuarComprando = false;
                System.out.println("Por favor dirigete a imprimir boletas en menu para confirmar tu compra");


                MapaAsientosEstados.guardarEstadoActual(evento, fecha);
                guardarResumenVenta(evento, fecha, ticketsVentaActual);
            }
        }
    }

    private static int obtenerIdEventoPorNombre(String nombreEvento) {
        for (ShowEvents.ShowEvent evento : ShowEvents.GestorEventos.eventos) {
            if (evento.nameEvent.equals(nombreEvento)) {
                return evento.idEvent;
            }
        }
        return 0; // O manejar el error
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

        System.out.println("\n========== RESUMEN DE VENTA ==========");
        System.out.println("Evento            : " + evento);
        System.out.println("Fecha             : " + fecha);
        System.out.println("Asientos vendidos : " + tickets.size());
        System.out.println("Total Descuentos  : $" + ventaExistente.getTotalDescuentos()); // ← NUEVO
        System.out.println("Total venta       : $" + ventaExistente.total);
        System.out.println("======================================");
    }


}