public class SubEcuacion {
    private String ecuacion;
    private double resultado;

    public SubEcuacion(String ecuacion, double resultado) {
        this.ecuacion = ecuacion;
        this.resultado = redondearA3Decimales(resultado);
    }

    public String getEcuacion() {
        return ecuacion;
    }
    public double getResultado() {
        return resultado;
    }
    public boolean validarRespuesta(double respuesta) {
        return Double.compare(redondearA3Decimales(respuesta), resultado) == 0;
    }
    private double redondearA3Decimales(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }
}
