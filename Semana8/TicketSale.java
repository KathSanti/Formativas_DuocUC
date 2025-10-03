package Formativas_DuocUC.Semana8;

import java.util.Scanner;

public class TicketSale {

    public void VentaEntradas(Scanner sc) {

        int interactiveButton = 1;
        boolean continuarComprando = true;

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
            Ticket.Ticketdata ticketData = ticketManager.Descuento(sc, precioAsiento, coordenadaAsiento, zonaAsiento);

            //Guardar información boletas y agregar al listado
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
            }
        }
    }
}