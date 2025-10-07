package Formativas_DuocUC.Semana8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class ShowEvents {

    //Clase para manejar lista de eventos y fechas y poder utilizarla para manejarla junto los asientos en Mapaasiento estado

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


    }
    //Clase array para añadir evento y fechas por eventos

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





        //Clase para imprimir por pantalla y escoger evntos por el usuario

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

        //Clase para recorrer las fechas por eventos con for e imprimir por pantalla

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


        //Metodo para impimir por pnatalla la opción esgogida al usuario y guardar la información en la boleta en clase tiketsale

        public static void seleccionarButacas(ShowEvent evento, String fecha) {
            System.out.println("\n========== Seleciona tu asiento ==========");
            System.out.println("Evento: " + evento.nameEvent);
            System.out.println("Fecha: " + fecha);

            TicketSale.VentaEntradas(sc, evento.nameEvent, fecha);

        }



        //Metodo para buscar ID del evento para que luego el usuario pueda eliminar su reserva si lo desea

        public static ShowEvent buscarEvento(int idEvent) {
            for (ShowEvent evento : eventos) {
                if (evento.idEvent == idEvent) {
                    return evento;
                }
            }
            return null;
        }


    }
}