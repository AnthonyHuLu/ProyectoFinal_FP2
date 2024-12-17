
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mostrarPantallaInicial();
            }
        });
    }

    private static void mostrarPantallaInicial() {
        String[] opcionesDificultad = {"Fácil", "Intermedio", "Difícil"};
        int seleccionDificultad = JOptionPane.showOptionDialog(null
                , "Seleccione la dificultad:", "Dificultad",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null
                , opcionesDificultad, opcionesDificultad[0]);
        Dificultad dificultad;
        switch (seleccionDificultad) {
            case 0:
                dificultad = Dificultad.FACIL;
                break;
            case 1:
                dificultad = Dificultad.INTERMEDIO;
                break;
            case 2:
                dificultad = Dificultad.DIFICIL;
                break;
            default:
                dificultad = Dificultad.FACIL;
                break;
        }
        String[] opcionesModoJuego = {"Solitario", "2 Jugadores", "Contra IA"};
        int seleccionModoJuego = JOptionPane.showOptionDialog(null
                , "Seleccione el modo de juego:", "Modo de Juego",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null            
                , opcionesModoJuego, opcionesModoJuego[0]);
        boolean esModoSolitario = false;
        boolean contraIA = false;
        int numJugadores = 2;
        switch (seleccionModoJuego) {
            case 0:
                esModoSolitario = true;
                break;
            case 2:
                esModoSolitario = true;
                contraIA = true;
                break;
            case 1:
            default:
                numJugadores = 2;
                break;
        }
        int tiempo = 0;
        boolean tiempoValido = false;
        while (!tiempoValido) {
            String tiempoStr = JOptionPane.showInputDialog("¿Cuánto tiempo durará la partida?");
            if (tiempoStr != null && !tiempoStr.isEmpty()) {
                try {
                    tiempo = Integer.parseInt(tiempoStr);
                    if (tiempo > 0) {
                        tiempoValido = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Entrada inválida.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Entrada inválida..");
            }
        }
        Juego juego = new Juego(10, 10, numJugadores, tiempo, 10, dificultad
                , esModoSolitario, contraIA);
        InterfazJuego interfaz = new InterfazJuego(juego);
        interfaz.setVisible(true);
    }

}
