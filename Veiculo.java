public class Veiculo {
    private int codigo;
    private String modelo;
    private String cor;
    private int ano;
    private int odometro;
    private String cidade;
    private boolean disponivel;
    private double valor_diaria;
    private double valor_km_rodado;

    
    public Veiculo(int codigo, String modelo, String cor, int ano, int odometro, String cidade, boolean disponivel, double valor_diaria, double valor_km_rodado) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.odometro = odometro;
        this.cidade = cidade;
        this.disponivel = disponivel;
        this.valor_diaria = valor_diaria;
        this.valor_km_rodado = valor_km_rodado;
    }
    
    public int getCodigo() { return codigo; }
    public String getModelo() { return modelo; }
    public String getCor() { return cor; }
    public int getAno() { return ano; }
    
    public int getOdometro() { return odometro; } 
    
    public String getCidade() { return cidade; } 
    
    public boolean isDisponivel() { return disponivel; } 
    
    public double getValorDiaria() { return valor_diaria; }

    public double getValorKmRodado() { return valor_km_rodado; }

    public void setDisponivel(boolean disponivel) { 
        this.disponivel = disponivel; 
    }

    public void setCidade(String cidade) { 
        this.cidade = cidade; 
    }

    public void setOdometro(int odometro) { 
        this.odometro = odometro; 
    }

    public String serializar() {

        return codigo + "\t" +
                modelo + "\t" +
                cor + "\t" +
                ano + "\t" +
                odometro + "\t" +
                cidade + "\t" +
                disponivel + "\t" +
                valor_diaria + "\t" +
                valor_km_rodado;
    }

    public static Veiculo deserializar(String linha) {
    String[] partes = linha.split("\t");
    int codigo = Integer.parseInt(partes[0]);
    String modelo = partes[1];
    String cor = partes[2];
    int ano = Integer.parseInt(partes[3]);
    int odometro = Integer.parseInt(partes[4]);
    String cidade = partes[5];
    boolean disponivel = Boolean.parseBoolean(partes[6]);
    double valorDiaria = Double.parseDouble(partes[7]);
    double valorKm = Double.parseDouble(partes[8]);
    
    return new Veiculo(codigo, modelo, cor, ano, odometro, cidade, disponivel, valorDiaria, valorKm);
    
    }

     @Override
    public String toString() {

        return "Código: " + codigo +
                "\nModelo: " + modelo +
                "\nCor: " + cor +
                "\nAno: " + ano +
                "\nOdômetro: " + odometro +
                "\nCidade: " + cidade +
                "\nDisponível: " + disponivel +
                "\nValor diária: R$ " + valor_diaria +
                "\nValor KM: R$ " + valor_km_rodado;
    }
}