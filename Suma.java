public class Suma extends Operacion {

    public Suma(Numero numero1, Numero numero2) {
        super(numero1, numero2);
    }

    @Override
    public double realizarOperacion() {
        return numero1.getValor() + numero2.getValor();
    }

    @Override
    public boolean validarResultado(double resultado) {
        return Double.compare(resultado, realizarOperacion()) == 0;
    }
}
