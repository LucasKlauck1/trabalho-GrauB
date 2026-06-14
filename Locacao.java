import java.util.ArrayList;

public class Locacao {

    private Veiculo veiculo;
    private String cliente;
    private String origem;
    private String destino;
    private int km_rodado;
    private int qt_dias_reserva;
    private int qt_dias_realizado;

    public Locacao(Veiculo veiculo, String cliente, String origem, String destino, int km_rodado, int qt_dias_reserva, int qt_dias_realizado) {

        this.veiculo = veiculo;
        this.cliente = cliente;
        this.origem = origem;
        this.destino = destino;
        this.km_rodado = km_rodado;
        this.qt_dias_reserva = qt_dias_reserva;
        this.qt_dias_realizado = qt_dias_realizado;
    }

    //sobrecarga do construtor
    public Locacao(Veiculo veiculo, String cliente, String origem, int qt_dias_reserva) { 
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.origem = origem;

        this.destino = "";
        this.km_rodado = 0;

        this.qt_dias_reserva = qt_dias_reserva;
        this.qt_dias_realizado = 0;
    }

    public double calcularValorDiarias() {

        int dias;

        if (qt_dias_realizado > 0) {
            dias = qt_dias_realizado;
        } else {
            dias = qt_dias_reserva;
        }

        return dias * veiculo.getValorDiaria();
    }

    public double calcularValorKmRodado() {
        return km_rodado * veiculo.getValorKmRodado();
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public int getKm_rodado() {
        return km_rodado;
    }

    public int getQt_dias_reserva() {
        return qt_dias_reserva;
    }

    public int getQt_dias_realizado() {
        return qt_dias_realizado;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setKm_rodado(int km_rodado) {
        this.km_rodado = km_rodado;
    }

    public void setQt_dias_reserva(int qt_dias_reserva) {
        this.qt_dias_reserva = qt_dias_reserva;
    }

    public void setQt_dias_realizado(int qt_dias_realizado) {
        this.qt_dias_realizado = qt_dias_realizado;
    }

    public boolean isAtiva() {
        return qt_dias_realizado == 0;
    }

    public String serializar() {

        return veiculo.getCodigo() + "\t" +
               cliente + "\t" +
               origem + "\t" +
               destino + "\t" +
               km_rodado + "\t" +
               qt_dias_reserva + "\t" +
               qt_dias_realizado;
    }

    public static Locacao deserializar(
            String linha,
            ArrayList<Veiculo> veiculos) {

        String[] partes = linha.split("\t");

        int codigoVeiculo = Integer.parseInt(partes[0]);

        Veiculo veiculo = null;

        for (Veiculo v : veiculos) {
            if (v.getCodigo() == codigoVeiculo) {
                veiculo = v;
                break;
            }
        }

        return new Locacao(
                veiculo,
                partes[1],
                partes[2],
                partes[3],
                Integer.parseInt(partes[4]),
                Integer.parseInt(partes[5]),
                Integer.parseInt(partes[6]));
    }

    @Override
    public String toString() {

        return "Cliente: " + cliente +
               "\nVeículo: " + veiculo.getModelo() +
               "\nOrigem: " + origem +
               "\nDestino: " + destino +
               "\nKm Rodado: " + km_rodado +
               "\nDias Reservados: " + qt_dias_reserva +
               "\nDias Utilizados: " + qt_dias_realizado;
    }
}