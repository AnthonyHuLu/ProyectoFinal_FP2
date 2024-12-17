public class Jugador {
    private String nombre;
    private int puntuacion;
    private int tiempoRestante;
    private int movimientosPermitidos;

    public Jugador(String nombre, int tiempoRestante, int movimientosPermitidos) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.tiempoRestante = tiempoRestante;
        this.movimientosPermitidos = movimientosPermitidos;
    }

    public String getNombre() {
        return nombre;
    }
    public int getPuntuacion() {
        return puntuacion;
    }
    public void incrementarPuntuacion(int puntos) {
        this.puntuacion += puntos;
    }
    public int getTiempoRestante() {
        return tiempoRestante;
    }
    public void reducirTiempo(int segundos) {
        this.tiempoRestante = Math.max(0, this.tiempoRestante - segundos);
    }
    public int getMovimientosPermitidos() {
        return movimientosPermitidos;
    }
    public void reducirMovimientos() {
        if (this.movimientosPermitidos > 0) {
            this.movimientosPermitidos--;
        }
    }
}
