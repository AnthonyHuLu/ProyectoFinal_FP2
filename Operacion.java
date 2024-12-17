public abstract class Operacion {
    protected Numero numero1;
    protected Numero numero2;

    public Operacion(Numero numero1, Numero numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
    }

    public Numero getNumero1() {
        return numero1;
    }
    public Numero getNumero2() {
        return numero2;
    }
    public void setNumeros(Numero numero1, Numero numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
    }

    public abstract double realizarOperacion();

    public abstract boolean validarResultado(double resultado);
}
