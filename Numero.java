import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Numero {
    private double valor;
    private String representacion;

    public Numero(double valor) {
        this.valor = valor;
        DecimalFormat df = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));
        this.representacion = df.format(valor);
    }

    public Numero(int numerador, int denominador) {
        if (denominador == 0) {
            throw new ArithmeticException("El denominador no puede ser cero");
        }
        this.valor = redondearA3Decimales((double) numerador / denominador);
        this.representacion = numerador + "/" + denominador;
    }

    public static Numero crearNumeroPeriodico(double entero, double decimal, int repeatLength) {
        DecimalFormat df = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));
        double valor = Double.parseDouble(df.format(entero + decimal));
        String decimalStr = String.format("%.3f", decimal).substring(2, 2 + repeatLength);
        String representacion = String.format(Locale.US, "%.0f.%s(%s)*", entero
                , decimalStr.substring(0, repeatLength), decimalStr);
        return new Numero(valor, representacion);
    }

    private Numero(double valor, String representacion) {
        this.valor = valor;
        this.representacion = representacion;
    }
    public double getValor() {
        return valor;
    }
    public String getRepresentacion() {
        return representacion;
    }
    private double redondearA3Decimales(double valor) {
        return Math.round(valor * 1000.0) / 1000.0;
    }
}
