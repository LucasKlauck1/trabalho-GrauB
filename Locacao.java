public class Locacao {
    private Veiculo veiculo;
    private int codigo;
    private String cliente;
    private String origem;
    private String destino;
    private int km_rodado;
    private int qt_dias_reserva;
    private int qt_dias_realizado;

    public Locacao(int codigo, String cliente, String origem, String destino, int km_rodado, int qt_dias_reserva, int qt_dias_realizado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.origem = origem;
        this.destino = destino;
        this.km_rodado = km_rodado;
        this.qt_dias_reserva = 0;
        this.qt_dias_realizado = 0;
    }

    public double calcularValorDiarias() {
        int dias = (this.qt_dias_realizado > 0) ? this.qt_dias_realizado : this.qt_dias_reserva;
        //return; 
    }
    
    public double calcularValorKmRodado() {
        //return ;
    }

}
