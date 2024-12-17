import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class InterfazJuego extends JFrame {
    private Juego juego;
    private JPanel panelTablero;
    private JLabel[][] etiquetasCasillas;
    private JLabel etiquetaTurno;
    private JLabel etiquetaPuntuacion;
    private JLabel etiquetaTiempo;
    private JLabel etiquetaTurnosRestantes;
    private JLabel etiquetaMovimientosRestantes;
    private Timer temporizador;

    public InterfazJuego(Juego juego) {
        this.juego = juego;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Juego de Estrategia en Turnos");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        panelTablero = new JPanel(new GridLayout(10, 10));
        etiquetasCasillas = new JLabel[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                etiquetasCasillas[i][j] = new JLabel("", SwingConstants.CENTER);
                etiquetasCasillas[i][j].setBorder(BorderFactory
                        .createLineBorder(Color.BLACK));
                etiquetasCasillas[i][j].setOpaque(true);
                etiquetasCasillas[i][j].setBackground(Color.WHITE);
                panelTablero.add(etiquetasCasillas[i][j]);
            }
        }
        add(panelTablero, BorderLayout.CENTER);
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new GridLayout(5, 1));
        etiquetaTurno = new JLabel("Turno de: " + juego.getJugadorActual().getNombre());
        etiquetaPuntuacion = new JLabel("Puntuación: " + juego.getJugadorActual()
                .getPuntuacion());
        etiquetaTiempo = new JLabel("Tiempo restante: " + juego.getJugadorActual()
                .getTiempoRestante());
        etiquetaTurnosRestantes = new JLabel("Turnos restantes: " + calcularTurnosRestantes());
        etiquetaMovimientosRestantes = new JLabel("Movimientos restantes: " 
                + juego.getJugadorActual().getMovimientosPermitidos());
        panelControl.add(etiquetaTurno);
        panelControl.add(etiquetaPuntuacion);
        panelControl.add(etiquetaTiempo);
        panelControl.add(etiquetaTurnosRestantes);
        panelControl.add(etiquetaMovimientosRestantes);
        JButton botonRealizarOperacion = new JButton("Realizar Operación");
        botonRealizarOperacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarOperacion();
            }
        });
        panelControl.add(botonRealizarOperacion);
        add(panelControl, BorderLayout.SOUTH);
        actualizarTablero();
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
        });
        temporizador.start();
    }

    private void actualizarTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Numero numero = juego.getTablero().getCasilla(i, j).getNumero();
                if (numero != null) {
                    etiquetasCasillas[i][j].setText(numero.getRepresentacion());
                } else {
                    etiquetasCasillas[i][j].setText("");
                }
            }
        }
    }

    private void realizarOperacion() {
        Jugador jugadorActual = juego.getJugadorActual();
        if (jugadorActual instanceof JugadorIA) {
            ((JugadorIA) jugadorActual).realizarOperacionIA(juego.getTablero());
            String accionIA = ((JugadorIA) jugadorActual).getUltimaAccion();
            JOptionPane.showMessageDialog(this, "La IA realizó la siguiente acción: " + accionIA);
            actualizarTablero();
            juego.cambiarTurno();
            actualizarEtiquetas();
            if (juego.juegoTerminado()) {
                temporizador.stop();
                return;
            }
        } else {
            try {
                int fila1 = Integer.parseInt(JOptionPane.showInputDialog(this
                        , "Ingrese la fila de la primera casilla:"));
                int columna1 = Integer.parseInt(JOptionPane.showInputDialog(this
                        , "Ingrese la columna de la primera casilla:"));
                int fila2 = Integer.parseInt(JOptionPane.showInputDialog(this
                        , "Ingrese la fila de la segunda casilla:"));
                int columna2 = Integer.parseInt(JOptionPane.showInputDialog(this
                        , "Ingrese la columna de la segunda casilla:"));
                Casilla casilla1 = juego.getTablero().getCasilla(fila1, columna1);
                Casilla casilla2 = juego.getTablero().getCasilla(fila2, columna2);
                if (casilla1.getSubEcuacion() != null) {
                    String ecuacion = casilla1.getSubEcuacion().getEcuacion();
                    String respuestaStr = JOptionPane.showInputDialog(this
                            , "Resuelve la ecuación: " + ecuacion);
                    if (respuestaStr != null && !respuestaStr.isEmpty()) {
                        double respuesta = Double.parseDouble(respuestaStr);
                        if (!casilla1.getSubEcuacion().validarRespuesta(respuesta)) {
                            JOptionPane.showMessageDialog(this
                                    , "Respuesta incorrecta. Intente de nuevo.");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Operación cancelada.");
                        return;
                    }
                }
                if (casilla2.getSubEcuacion() != null) {
                    String ecuacion = casilla2.getSubEcuacion().getEcuacion();
                    String respuestaStr = JOptionPane.showInputDialog(this
                            , "Resuelve la ecuación: " + ecuacion);
                    if (respuestaStr != null && !respuestaStr.isEmpty()) {
                        double respuesta = Double.parseDouble(respuestaStr);
                        if (!casilla2.getSubEcuacion().validarRespuesta(respuesta)) {
                            JOptionPane.showMessageDialog(this
                                    , "Respuesta incorrecta. Intente de nuevo.");
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Operación cancelada.");
                        return;
                    }
                }
                Operacion operacion;
                if (juego.getDificultad() == Dificultad.DIFICIL) {
                    String[] operaciones = {"Suma", "Resta", "Multiplicación", "División"};
                    int tipoOperacion = new Random().nextInt(4);
                    String operacionTexto = operaciones[tipoOperacion];
                    JOptionPane.showMessageDialog(this, "Operación seleccionada: " 
                            + operacionTexto);
                    switch (tipoOperacion) {
                        case 0:
                            operacion = new Suma(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                        case 1:
                            operacion = new Resta(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                        case 2:
                            operacion = new Multiplicacion(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                        case 3:
                            operacion = new Division(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                        default:
                            operacion = new Suma(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                    }
                } else {
                    String[] opcionesOperacion = {"Suma", "Resta"};
                    int tipoOperacion = JOptionPane.showOptionDialog(this
                            , "Seleccione la operación:", "Operación",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                            , null, opcionesOperacion, opcionesOperacion[0]);
                    switch (tipoOperacion) {
                        case 0:
                            operacion = new Suma(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                        case 1:
                            operacion = new Resta(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                        default:
                            operacion = new Suma(casilla1.getNumero()
                                    , casilla2.getNumero());
                            break;
                    }
                }
                double resultado = Double.parseDouble(JOptionPane.showInputDialog(this
                        , "Ingrese el resultado de la operación:"));
                if (juego.getTablero().realizarOperacion(fila1, columna1, fila2
                        , columna2, operacion, resultado)) {
                    JOptionPane.showMessageDialog(this
                            , "Operación correcta. Casillas eliminadas.");
                    jugadorActual.incrementarPuntuacion(10); 
                    actualizarTablero();
                } else {
                    JOptionPane.showMessageDialog(this
                            , "Operación incorrecta. Intente de nuevo.");
                }
                juego.cambiarTurno();
                actualizarEtiquetas();
                if (juego.juegoTerminado()) {
                    temporizador.stop();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this
                        , "Entrada inválida. Por favor, ingrese un número válido.");
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Operación cancelada.");
            }
        }
    }

    private double convertirAValor(String input) {
        if (input.contains("/")) {
            String[] partes = input.split("/");
            int numerador = Integer.parseInt(partes[0]);
            int denominador = Integer.parseInt(partes[1]);
            if (denominador == 0) {
                throw new ArithmeticException("El denominador no puede ser cero");
            }
            return (double) numerador / denominador;
        } else {
            return Double.parseDouble(input);
        }
    }

    private void actualizarTiempo() {
        Jugador jugadorActual = juego.getJugadorActual();
        jugadorActual.reducirTiempo(1);
        if (jugadorActual.getTiempoRestante() <= 0) {
            JOptionPane.showMessageDialog(this, "¡Tiempo terminado para " 
                    + jugadorActual.getNombre() + "!");
            juego.cambiarTurno();
            if (juego.juegoTerminado()) {
                temporizador.stop();
                return;
            }
        }
        actualizarEtiquetas();
    }

    private int calcularTurnosRestantes() {
        int totalJugadores = juego.getNumeroDeJugadores();
        int tiempoTotalRestante = 0;
        for (Jugador jugador : juego.getJugadores()) {
            tiempoTotalRestante += jugador.getTiempoRestante();
        }
        return tiempoTotalRestante / totalJugadores;
    }

    private void actualizarEtiquetas() {
        etiquetaTurno.setText("Turno de: " + juego.getJugadorActual().getNombre());
        etiquetaPuntuacion.setText("Puntuación: " + juego.getJugadorActual()
                .getPuntuacion());
        etiquetaTiempo.setText("Tiempo restante: " + juego.getJugadorActual()
                .getTiempoRestante());
        etiquetaTurnosRestantes.setText("Turnos restantes: " + calcularTurnosRestantes());
        etiquetaMovimientosRestantes.setText("Movimientos restantes: " 
                + juego.getJugadorActual().getMovimientosPermitidos());
    }
    
}
