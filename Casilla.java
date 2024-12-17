public class Casilla {
    private Numero numero;
    private boolean esEspecial;
    private SubEcuacion subEcuacion;

    public Casilla(Numero numero) {
        this.numero = numero;
        this.esEspecial = false;
        this.subEcuacion = null;
    }

    public Numero getNumero() {
        return numero;
    }
    public void setNumero(Numero numero) {
        this.numero = numero;
    }
    public void setEsEspecial(boolean esEspecial) {
        this.esEspecial = esEspecial;
    }
    public boolean esEspecial() {
        return esEspecial;
    }
    public SubEcuacion getSubEcuacion() {
        return subEcuacion;
    }
    public void setSubEcuacion(SubEcuacion subEcuacion) {
        this.subEcuacion = subEcuacion;
    }
}
