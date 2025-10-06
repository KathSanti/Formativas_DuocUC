package Formativas_DuocUC.Semana8;

import java.util.HashMap;
import java.util.Map;

public class MapaAsientosEstados {

    // Clase para representar el estado de asientos para un evento-fecha específico
    public static class EstadoAsientosEvento {
        public boolean[][] asientos;
        public boolean[][] reservaPendiente;
        public String evento;
        public String fecha;

        public EstadoAsientosEvento(String evento, String fecha) {
            this.evento = evento;
            this.fecha = fecha;
            // Inicializar arrays de asientos
            this.asientos = new boolean[5][6];
            this.reservaPendiente = new boolean[5][6];
        }

        // Copiar el estado actual de SeatingMap a este evento
        public void copiarEstadoActual() {
            for (int i = 0; i < 5; i++) {
                System.arraycopy(SeatingMap.asientos[i], 0, this.asientos[i], 0, 6);
                System.arraycopy(SeatingMap.reservaPendiente[i], 0, this.reservaPendiente[i], 0, 6);
            }
        }

        // Restaurar este estado a SeatingMap
        public void restaurarEstado() {
            for (int i = 0; i < 5; i++) {
                System.arraycopy(this.asientos[i], 0, SeatingMap.asientos[i], 0, 6);
                System.arraycopy(this.reservaPendiente[i], 0, SeatingMap.reservaPendiente[i], 0, 6);
            }
        }


        public void marcarAsientoComoOcupado(String coordenadaAsiento) {
            try {
                char filaChar = coordenadaAsiento.charAt(0);
                int fila = filaChar - 'A';
                int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

                if (fila >= 0 && fila < 5 && columna >= 0 && columna < 6) {
                    this.asientos[fila][columna] = true;
                    this.reservaPendiente[fila][columna] = false;
                    System.out.println("Asiento " + coordenadaAsiento + " marcado como ocupado para " + evento + " - " + fecha);
                }
            } catch (Exception e) {
                System.out.println("Error al confirmar el asiento en el estado: " + e.getMessage());
            }
        }
    }

    // Mapa para almacenar los estados por evento y fecha
    private static Map<String, EstadoAsientosEvento> mapasPorEvento = new HashMap<>();

    // Generar clave única para el mapa
    private static String generarClave(String evento, String fecha) {
        return evento + "|" + fecha;
    }

    // Obtener o crear estado de asientos para un evento-fecha
    public static EstadoAsientosEvento obtenerEstadoAsientos(String evento, String fecha) {
        String clave = generarClave(evento, fecha);

        if (!mapasPorEvento.containsKey(clave)) {
            // Crear nuevo estado
            EstadoAsientosEvento nuevoEstado = new EstadoAsientosEvento(evento, fecha);
            mapasPorEvento.put(clave, nuevoEstado);
            return nuevoEstado;
        }

        return mapasPorEvento.get(clave);
    }

    // Guardar estado actual en el evento-fecha específico
    public static void guardarEstadoActual(String evento, String fecha) {
        EstadoAsientosEvento estado = obtenerEstadoAsientos(evento, fecha);
        estado.copiarEstadoActual();
    }

    // Cargar estado de asientos para un evento-fecha específico
    public static void cargarEstado(String evento, String fecha) {
        EstadoAsientosEvento estado = obtenerEstadoAsientos(evento, fecha);
        estado.restaurarEstado();
    }

    // Verificar si un asiento está disponible para un evento-fecha
    public static boolean verificarAsientoDisponible(String evento, String fecha, String coordenadaAsiento) {
        cargarEstado(evento, fecha);

        try {
            char filaChar = coordenadaAsiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

            if (fila < 0 || fila > 4 || columna < 0 || columna > 5) {
                return false;
            }

            return !SeatingMap.asientos[fila][columna] && !SeatingMap.reservaPendiente[fila][columna];

        } catch (Exception e) {
            return false;
        }
    }


    public static void confirmarAsientoComoOcupado(String evento, String fecha, String coordenadaAsiento) {
        // Actualizar el estado específico del evento
        EstadoAsientosEvento estado = obtenerEstadoAsientos(evento, fecha);
        estado.marcarAsientoComoOcupado(coordenadaAsiento);

        // Actualizar también el mapa general
        marcarAsientoComoOcupadoEnMapaGeneral(coordenadaAsiento);

        // Guardar el estado actualizado
        guardarEstadoActual(evento, fecha);
    }


    private static void marcarAsientoComoOcupadoEnMapaGeneral(String coordenadaAsiento) {
        try {
            char filaChar = coordenadaAsiento.charAt(0);
            int fila = filaChar - 'A';
            int columna = Integer.parseInt(coordenadaAsiento.substring(1)) - 1;

            if (fila >= 0 && fila < 5 && columna >= 0 && columna < 6) {
                SeatingMap.asientos[fila][columna] = true;
                SeatingMap.reservaPendiente[fila][columna] = false;
            }
        } catch (Exception e) {
            System.out.println("Error al confirmar el asiento en el mapa general: " + e.getMessage());
        }
    }
}