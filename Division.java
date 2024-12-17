public class Division extends Operacion {

    public Division(Numero numero1, Numero numero2) {
        super(numero1, numero2);
    }
    
    private double redondearA3Decimales(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }

    @Override
    public double realizarOperacion() {
        if (numero2.getValor() == 0) {
            throw new ArithmeticException("División por cero no permitida");
        }
        return redondearA3Decimales(numero1.getValor() / numero2.getValor());
    }
    
    @Override
    public boolean validarResultado(double resultado) {
        if (numero2.getValor() == 0) {
            return false; 
        }
        return Double.compare(resultado, realizarOperacion()) == 0;
    }
}
