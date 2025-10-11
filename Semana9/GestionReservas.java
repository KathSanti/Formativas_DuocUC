package Formativas_DuocUC.Semana9;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class GestionReservas {



    public static class VentaGeneral {
        public List<BoletaVentas.Ticketdata> tickets;
        public double total;
        public double totalDescuentos;
        public int cantidadAsientos;

        public VentaGeneral() {

            this.tickets = new ArrayList<>();
            this.total = 0;
            this.totalDescuentos = 0;
            this.cantidadAsientos = 0;

        }

        //Metodo para almacenar descuentos

        public void agregarTicket(BoletaVentas.Ticketdata BoletaVentas) {
            this.tickets.add(BoletaVentas);
            this.total += BoletaVentas.totalPagar;
            this.totalDescuentos += BoletaVentas.descuento;
        }

        //Metodo rapido para obtener la cantidad de asientos vendidos en la clase ticket

        public int getCantidadAsientos() {
            return tickets.size();
        }

        //Metodo rapido para obtener total descuento en la clase ticket

        public double getTotalDescuentos() {
            return totalDescuentos;
        }
    }

    public static List<GestionReservas.VentaGeneral> VentaGeneral = new ArrayList<>();




    public static void ventasReserva (Scanner sc){

        boolean continuarComprando = true;

        while (continuarComprando) {

            MapaEstadoAsiento.MostrarMapaAsientos();
            ValidacionesEntradas validador = new ValidacionesEntradas();


            System.out.print("Ingrese asiento (ej: A1, B5, E6): ");
            String coordenadaAsiento = sc.nextLine().trim().toUpperCase();

            if (!validador.validarFormatoCodigoEntrada(coordenadaAsiento)) {
                System.out.println("Código inválido. Formato correcto: Letra (A-E) + Número (1-6)");
                continue;
            }

            // Verificar disponibilidad usando el nuevo sistema haspMap Mejora sugerida por el profe jorge :)
            if (!MapaEstadoAsiento.verificarAsientoDisponible(coordenadaAsiento)) {
                System.out.println("Asiento no disponible");
                continue;
            }



            char filaChar = coordenadaAsiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

            int precioAsiento = MapaEstadoAsiento.preciosUnitarios[fila];
            String zonaAsiento = MapaEstadoAsiento.RowEntryType[fila];
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
            BoletaVentas ticketManager = new BoletaVentas();
            BoletaVentas.Ticketdata ticketData = ticketManager.AlamacenaDatosTickets(sc, precioAsiento, coordenadaAsiento, zonaAsiento);

            // Guardar información boletas y agregar al listado
            BoletaVentas.agregarTicket(ticketData);


            MapaEstadoAsiento.reservaPendiente[fila][columna] = true;
            System.out.println("¡Reserva confirmada! Asiento " + coordenadaAsiento + " reservado.");

            // Preguntar si quiere comprar otro asiento
            System.out.print("¿Deseas reservar otro asiento? (S/N): ");
            char respuestaContinuar = sc.next().charAt(0);
            sc.nextLine();

            if (respuestaContinuar == 'n' || respuestaContinuar == 'N') {
                continuarComprando = false;
                System.out.println("Por favor dirigete a imprimir boletas en menu para confirmar tu compra");


            }
        }
    }




}





