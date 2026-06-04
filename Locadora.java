import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Locadora {
    private ArrayList<Veiculo> veiculos;
    private ArrayList<Locacao> locacoes;


    public Locadora() {
        this.veiculos = new ArrayList<>();
        this.locacoes = new ArrayList<>();
    }


    public void carregarDados() {
        String caminho = "veiculos.txt";

        try( BufferedReader buffer =  new BufferedReader(new FileReader(caminho))) {
            String linha;

            while ((linha = buffer.readLine())!= null ) {
                String[] partes = linha.split("\t");

                int codigo = Integer.parseInt(partes[0]);
                String modelo = partes[1];
                String cor = partes[2];
                int ano = Integer.parseInt(partes[3]);
                int odometro = Integer.parseInt(partes[4]);
                String cidade = partes[5];
                boolean disponivel = Boolean.parseBoolean(partes[6]);
                double valorDiaria = Double.parseDouble(partes[7]);
                double valorKm =  Double.parseDouble(partes[8]);

                
                Veiculo v = new Veiculo(codigo, modelo, cor, ano, odometro, cidade, disponivel,valorDiaria, valorKm);

                this.veiculos.add(v);
            }
        } catch (IOException e) {
            System.out.println("Error ao carregar dados: " + e.getMessage());
        }
    }

    public Veiculo consultarVeiculo(int codigo) {
        for (Veiculo v : veiculos) {
            if (v.getCodigo() == codigo) {
                return v;
            }
        }
        return null;
    }


    }


    



