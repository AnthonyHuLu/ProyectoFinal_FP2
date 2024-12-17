public class Multiplicacion extends Operacion {

    public Multiplicacion(Numero numero1, Numero numero2) {
        super(numero1, numero2);
    }
    
    private double redondearA3Decimales(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }

    @Override
    public double realizarOperacion() {
        return redondearA3Decimales(numero1.getValor() * numero2.getValor());
    }

    @Override
    public boolean validarResultado(double resultado) {
        return Double.compare(resultado, realizarOperacion()) == 0;
    }
}
