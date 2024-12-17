import java.util.Locale;
import java.util.Random;

public class Tablero {
    private int filas;
    private int columnas;
    private Casilla[][] casillas;

    public Tablero(int filas, int columnas, Dificultad dificultad) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new Casilla[filas][columnas];
        inicializarTablero(dificultad);
    }
    
    private void inicializarTablero(Dificultad dificultad) {
        Random random = new Random();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Numero numero;
                SubEcuacion subEcuacion = null;
                if (dificultad == Dificultad.FACIL) {
                    numero = new Numero(random.nextInt(50) + 1);
                } else if (dificultad == Dificultad.INTERMEDIO) {
                    numero = new Numero(random.nextInt(201) - 100);
                    if (random.nextDouble() < 0.3) { 
                        int num1 = random.nextInt(201) - 100;
                        int num2 = random.nextInt(201) - 100;
                        String operador = random.nextBoolean() ? " + " : " - ";
                        int resultado = operador.equals(" + ") ? (num1 + num2) : (num1 - num2);
                        subEcuacion = new SubEcuacion(num1 + operador + num2, resultado);
                    }
                } else {
                    if (random.nextInt(3) == 0) { 
                        int numerador = random.nextInt(20) + 1;
                        int denominador = random.nextInt(19) + 1;
                        numero = new Numero(numerador, denominador);
                    } else if (random.nextInt(3) == 0 || random.nextInt(3) == 1) { 
                        double valor = random.nextDouble() * 400 - 200;
                        numero = new Numero(redondearA3Decimales(valor));
                    } else {
                        double entero = random.nextInt(10) + 1; 
                        double decimal = random.nextDouble();
                        numero = Numero.crearNumeroPeriodico(entero, decimal, 3); 
                    }
                    if (random.nextDouble() < 0.3) { 
                        double num1 = random.nextDouble() * 400 - 200;
                        double num2 = random.nextDouble() * 400 - 200;
                        String operador = obtenerOperadorAleatorio();
                        double resultado = redondearA3Decimales(calcularResultado(
                                redondearA3Decimales(num1), redondearA3Decimales(num2), operador));
                        subEcuacion = new SubEcuacion(redondearA3Decimales(num1) 
                                + " " + operador + " " + redondearA3Decimales(num2), resultado);
                    }
                }
                Casilla casilla = new Casilla(numero);
                if (subEcuacion != null) {
                    casilla.setSubEcuacion(subEcuacion);
                }
                casillas[i][j] = casilla;
            }
        }
    }

    private String obtenerOperadorAleatorio() {
        String[] operadores = {"+", "-", "*", "/"};
        return operadores[new Random().nextInt(operadores.length)];
    }

    private double calcularResultado(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num2 != 0 ? num1 / num2 : 0;
            default:
                return 0;
        }
    }
    
    private double redondearA3Decimales(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }

    public Casilla getCasilla(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return casillas[fila][columna];
        } else {
            throw new IndexOutOfBoundsException("Posición fuera del rango del tablero");
        }
    }

    public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(casillas[i][j].getNumero().getValor() + "\t");
            }
            System.out.println();
        }
    }

    public boolean realizarOperacion(int fila1, int columna1, int fila2
            , int columna2, Operacion operacion, double resultado) {
        Casilla casilla1 = getCasilla(fila1, columna1);
        Casilla casilla2 = getCasilla(fila2, columna2);
        operacion.setNumeros(casilla1.getNumero(), casilla2.getNumero());
        if (operacion.validarResultado(resultado)) {
            casilla1.setNumero(null);
            casilla2.setNumero(null);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean estaVacio() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].getNumero() != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getFilas() { 
        return filas; 
    } 
    public int getColumnas() { 
        return columnas; 
    }
}
