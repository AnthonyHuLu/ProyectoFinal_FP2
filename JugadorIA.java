import java.util.Random;

public class JugadorIA extends Jugador {
    private Random random;
    private String ultimaAccion;

    public JugadorIA(String nombre, int tiempoRestante, int movimientosPermitidos) {
        super(nombre, tiempoRestante, movimientosPermitidos);
        this.random = new Random();
        this.ultimaAccion = "";
    }

    public void realizarOperacionIA(Tablero tablero) {
        boolean operacionRealizada = false;
        while (!operacionRealizada) {
            int fila1 = random.nextInt(tablero.getFilas());
            int columna1 = random.nextInt(tablero.getColumnas());
            int fila2 = random.nextInt(tablero.getFilas());
            int columna2 = random.nextInt(tablero.getColumnas());
            if (fila1 == fila2 && columna1 == columna2) {
                continue;
            }
            Operacion operacion;
            int tipoOperacion = random.nextInt(4);
            switch (tipoOperacion) {
                case 0: operacion = new Suma(tablero.getCasilla(fila1, columna1)
                        .getNumero(), tablero.getCasilla(fila2, columna2).getNumero());
                        ultimaAccion = "Suma entre [" + fila1 + "," + columna1 
                                + "] y [" + fila2 + "," + columna2 + "]"; break;
                case 1: operacion = new Resta(tablero.getCasilla(fila1, columna1)
                        .getNumero(), tablero.getCasilla(fila2, columna2).getNumero());
                        ultimaAccion = "Resta entre [" + fila1 + "," + columna1 
                                + "] y [" + fila2 + "," + columna2 + "]"; break;
                case 2: operacion = new Multiplicacion(tablero.getCasilla(fila1, columna1)
                        .getNumero(), tablero.getCasilla(fila2, columna2).getNumero());
                        ultimaAccion = "Multiplicación entre [" + fila1 + "," 
                                + columna1 + "] y [" + fila2 + "," + columna2 + "]"; break;
                case 3: operacion = new Division(tablero.getCasilla(fila1, columna1)
                        .getNumero(), tablero.getCasilla(fila2, columna2).getNumero());
                        ultimaAccion = "División entre [" + fila1 + "," + columna1 
                                + "] y [" + fila2 + "," + columna2 + "]"; break;
                default: operacion = new Suma(tablero.getCasilla(fila1, columna1)
                        .getNumero(), tablero.getCasilla(fila2, columna2).getNumero());
                         ultimaAccion = "Suma entre [" + fila1 + "," + columna1 
                                 + "] y [" + fila2 + "," + columna2 + "]"; break;
            }
            double resultado = realizarOperacionConErrores(operacion);
            if (tablero.realizarOperacion(fila1, columna1, fila2, columna2, operacion, resultado)) {
                operacionRealizada = true;
            }
        }
    }

    private double realizarOperacionConErrores(Operacion operacion) {
        double resultado = operacion.realizarOperacion();
        if (random.nextDouble() < 0.2) { 
            resultado += (random.nextInt(5) - 2); 
        }
        return resultado;
    }

    public String getUltimaAccion() {
        return ultimaAccion;
    }
}
