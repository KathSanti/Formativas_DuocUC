package Formativas_DuocUC.Semana8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class ShowEvents {

    static class ShowEvent {
        int idEvent;
        String nameEvent;
        List<String> fechas;
        List<Ticket.Ticketdata> tickets;

        public ShowEvent(int idEvent, String nameEvent) {
            this.idEvent = idEvent;
            this.nameEvent = nameEvent;
            this.fechas = new ArrayList<>();
            this.tickets = new ArrayList<>();
        }


        public void addFechas(String fecha) {
            if(this.fechas.size() < 3) {
                this.fechas.add(fecha);
            } else {
                System.out.println("Máximo 3 fechas permitidas para: " + this.nameEvent);
            }
        }

        //eliminar clase que no corresponden y optimizar codigo

        public void addTicket(Ticket.Ticketdata ticket) {
            tickets.add(ticket);
        }

        public double getTotal(){
            double total = 0;
            for (Ticket.Ticketdata ticket : tickets) {
                total += ticket.totalPagar;
            }
            return total;
        }

        public int getCantidadTickets() {
            return tickets.size();
        }
    }

    static class GestorEventos {
        static List<ShowEvent> eventos = new ArrayList<>();
        static Scanner sc = new Scanner(System.in);

        // Inicializar eventos con sus fechas
        public static void inicializarEventos() {
            // Evento 1
            ShowEvent event1 = new ShowEvent(1, "Studio Ghibli Sinfónico - 40 años");
            event1.addFechas("13-11-2025");
            event1.addFechas("24-11-2025");
            event1.addFechas("30-11-2025");
            eventos.add(event1);

            // Evento 2
            ShowEvent event2 = new ShowEvent(2, "Cascanueces - Ballet y danza clásica");
            event2.addFechas("16-12-2025");
            event2.addFechas("20-12-2025");
            event2.addFechas("23-12-2025");
            eventos.add(event2);

            // Evento 3
            ShowEvent event3 = new ShowEvent(3, "Un poco Chill - Juan Pablo Lopez");
            event3.addFechas("16-10-2025");
            event3.addFechas("07-11-2025");
            event3.addFechas("16-11-2025");
            eventos.add(event3);
        }


        public static void mostrarEventos() {


            System.out.println("\n========== EVENTOS DISPONIBLES ==========");
            for (ShowEvent evento : eventos) {
                System.out.println("ID: " + evento.idEvent + " - " + evento.nameEvent);
                System.out.println("  Fechas: " + evento.fechas);
                System.out.println("-----------------------------------------");
            }

            System.out.print("Seleccione el ID del evento: ");
            int idEvento = FormatValidadors.ValidarNroEntero(sc);


            ShowEvent eventoSeleccionado = buscarEvento(idEvento);
            if (eventoSeleccionado != null) {
                seleccionarFecha(eventoSeleccionado);
            } else {
                System.out.println("Evento no encontrado. Intente nuevamente.");
            }

        }

        public static void seleccionarFecha(ShowEvent evento) {
            System.out.println("\n========== FECHAS DISPONIBLES ==========");
            System.out.println("Evento: " + evento.nameEvent);

            for (int i = 0; i < evento.fechas.size(); i++) {
                System.out.println((i + 1) + ") " + evento.fechas.get(i));
            }

            System.out.print("Seleccione el número de la fecha: ");
            int opcionFecha = FormatValidadors.ValidarNroEntero(sc);

            if (opcionFecha >= 1 && opcionFecha <= evento.fechas.size()) {
                String fechaSeleccionada = evento.fechas.get(opcionFecha - 1);
                System.out.println("Fecha seleccionada: " + fechaSeleccionada);
                seleccionarButacas(evento, fechaSeleccionada);
            } else {
                System.out.println("Fecha no válida. Intente nuevamente.");
                seleccionarFecha(evento);
            }
        }

        public static void seleccionarButacas(ShowEvent evento, String fecha) {
            System.out.println("\n========== Seleciona tu asiento ==========");
            System.out.println("Evento: " + evento.nameEvent);
            System.out.println("Fecha: " + fecha);

            TicketSale.VentaEntradas(sc, evento.nameEvent, fecha);

        }




        // Buscar evento por ID
        public static ShowEvent buscarEvento(int idEvent) {
            for (ShowEvent evento : eventos) {
                if (evento.idEvent == idEvent) {
                    return evento;
                }
            }
            return null;
        }

        // obtener todos los eventos
        public static List<ShowEvent> getEventos() {
            return eventos;
        }
    }
}