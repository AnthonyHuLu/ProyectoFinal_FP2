import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Juego {
    private Tablero tablero;
    private List<Jugador> jugadores;
    private int turnoActual;
    private Dificultad dificultad;

    public Juego(int filas, int columnas, int numJugadores, int tiempoPorJugador
            , int movimientosPorJugador, Dificultad dificultad, boolean modoSolitario
            , boolean contraIA) {
        this.tablero = new Tablero(filas, columnas, dificultad);
        this.jugadores = new ArrayList<>();
        if (modoSolitario) {
            if (contraIA) {
                jugadores.add(new Jugador("Jugador", tiempoPorJugador, movimientosPorJugador));
                jugadores.add(new JugadorIA("IA", tiempoPorJugador, movimientosPorJugador));
            } else {
                jugadores.add(new Jugador("Jugador", tiempoPorJugador, movimientosPorJugador));
            }
        } else {
            for (int i = 0; i < numJugadores; i++) {
                jugadores.add(new Jugador("Jugador " + (i + 1), tiempoPorJugador
                        , movimientosPorJugador));
            }
        }
        this.turnoActual = 0; 
        this.dificultad = dificultad;
    }
    
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (!juegoTerminado()) {
            Jugador jugadorActual = jugadores.get(turnoActual);
            System.out.println("Turno de " + jugadorActual.getNombre());
            System.out.println("Puntuación: " + jugadorActual.getPuntuacion());
            if (jugadorActual instanceof JugadorIA) { 
                ((JugadorIA) jugadorActual).realizarOperacionIA(tablero);
                System.out.println(((JugadorIA) jugadorActual).getUltimaAccion());
            } else {              
                System.out.println("Selecciona la fila y columna de la primera casilla:");
                int fila1 = scanner.nextInt();
                int columna1 = scanner.nextInt();
                System.out.println("Selecciona la fila y columna de la segunda casilla:");
                int fila2 = scanner.nextInt();
                int columna2 = scanner.nextInt();
                System.out.println("Selecciona el tipo de operación "
                        + "(1: Suma, 2: Resta, 3: Multiplicación, 4: División):");
                int tipoOperacion = scanner.nextInt();
                Operacion operacion;
                switch (tipoOperacion) {
                    case 1:
                        operacion = new Suma(tablero.getCasilla(fila1, columna1).getNumero()
                                , tablero.getCasilla(fila2, columna2).getNumero());
                        break;
                    case 2:
                        operacion = new Resta(tablero.getCasilla(fila1, columna1).getNumero()
                                , tablero.getCasilla(fila2, columna2).getNumero());
                        break;
                    case 3:
                        operacion = new Multiplicacion(tablero.getCasilla(fila1, columna1)
                                .getNumero(), tablero.getCasilla(fila2, columna2).getNumero());
                        break;
                    case 4:
                        operacion = new Division(tablero.getCasilla(fila1, columna1).getNumero()
                                , tablero.getCasilla(fila2, columna2).getNumero());
                        break;
                    default:
                        System.out.println("Operación no válida");
                        continue;
                }
                System.out.println("Ingresa el resultado de la operación:");
                double resultado = scanner.nextDouble();
                if (tablero.realizarOperacion(fila1, columna1, fila2, columna2
                        , operacion, resultado)) {
                    System.out.println("Operación correcta. Casillas eliminadas.");
                    jugadorActual.incrementarPuntuacion(10);
                } else {
                    System.out.println("Operación incorrecta. Intenta de nuevo.");
                }
            }
            jugadorActual.reducirMovimientos();
            if (jugadorActual.getMovimientosPermitidos() <= 0) {
                jugadorActual.reducirTiempo(30);
            }
            turnoActual = (turnoActual + 1) % jugadores.size();
            if (juegoTerminado()) {
                break;
            }
        }
        mostrarResultados();
        scanner.close();
    }

    public boolean juegoTerminado() {
        boolean tiempoRestante = jugadores.stream().allMatch(jugador 
                -> jugador.getTiempoRestante() <= 0);
        boolean casillasVacias = tablero.estaVacio();
        if (tiempoRestante || casillasVacias) {
            JOptionPane.showMessageDialog(null, "¡El juego ha terminado!");
            mostrarResultados();
            return true;
        }
        return false;
    }

    private void mostrarResultados() {
        StringBuilder resultados = new StringBuilder("Juego terminado. Resultados:\n");
        for (Jugador jugador : jugadores) {
            resultados.append(jugador.getNombre()).append(" - Puntuación: ")
                    .append(jugador.getPuntuacion()).append("\n");
        }
        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public Tablero getTablero() {
        return tablero;
    }
    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }
    public void cambiarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }
    public List<Jugador> getJugadores() {
        return jugadores;
    }
    public int getNumeroDeJugadores() {
        return jugadores.size();
    }
    public Dificultad getDificultad() {
        return dificultad;
    }

}
