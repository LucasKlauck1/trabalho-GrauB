import java.io.*;
import java.util.ArrayList;

public class Locadora {

    private ArrayList<Veiculo> veiculos;
    private ArrayList<Locacao> locacoes;

    public Locadora() {
        veiculos = new ArrayList<>();
        locacoes = new ArrayList<>();
    }

    public void carregaDados() {

        try {

            BufferedReader leDadosVeiculo = new BufferedReader(new FileReader("veiculos.txt"));

            String linha;

            while ((linha = leDadosVeiculo.readLine()) != null) {
                veiculos.add(Veiculo.deserializar(linha));
            }

            leDadosVeiculo.close();

        } catch (IOException e) {
            System.out.println("Arquivo veiculos.txt não encontrado.");
        }

        try {

            BufferedReader leDadosVeiculo = new BufferedReader(new FileReader("locacoes.txt"));

            String linha;

            while ((linha = leDadosVeiculo.readLine()) != null) {

                Locacao locacao = Locacao.deserializar(linha, veiculos);

                locacoes.add(locacao);
            }

            leDadosVeiculo.close();

        } catch (IOException e) {
            System.out.println("Arquivo locacoes.txt não encontrado.");
        }
    }

    public void salvaDados() {

        try {

            BufferedWriter escreveDadosVeiculo =
                    new BufferedWriter(new FileWriter("veiculos.txt"));

            for (Veiculo v : veiculos) {
                escreveDadosVeiculo.write(v.serializar());
                escreveDadosVeiculo.newLine();
            }

            escreveDadosVeiculo.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar veículos.");
        }

        try {

            BufferedWriter escreveDadosVeiculo =
                    new BufferedWriter(new FileWriter("locacoes.txt"));

            for (Locacao l : locacoes) {
                escreveDadosVeiculo.write(l.serializar());
                escreveDadosVeiculo.newLine();
            }

            escreveDadosVeiculo.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar locações.");
        }
    }

    public ArrayList<Veiculo> consultaVeiculo(
            String atributo,
            String valor) {

        ArrayList<Veiculo> resultado = new ArrayList<>();

        for (Veiculo v : veiculos) {

            if (atributo.equalsIgnoreCase("modelo")  && v.getModelo().equalsIgnoreCase(valor)) {

                resultado.add(v);
            }

            if (atributo.equalsIgnoreCase("cor") && v.getCor().equalsIgnoreCase(valor)) {

                resultado.add(v);
            }

            if (atributo.equalsIgnoreCase("ano") && String.valueOf(v.getAno()).equals(valor)) {

                resultado.add(v);
            }

            if (atributo.equalsIgnoreCase("cidade") && v.getCidade().equalsIgnoreCase(valor)) {

                resultado.add(v);
            }
        }

        return resultado;
    }

    public boolean realizaLocacao(
            int codigoVeiculo,
            String cliente,
            int diasReserva) {

        Veiculo veiculo = null;

        for (Veiculo v : veiculos) {

            if (v.getCodigo() == codigoVeiculo) {
                veiculo = v;
                break;
            }
        }

        if (veiculo == null) {
            return false;
        }

        if (!veiculo.isDisponivel()) {
            return false;
        }

        for (Locacao l : locacoes) {

            if (l.getCliente().equalsIgnoreCase(cliente) && l.isAtiva()) {

                return false;
            }
        }

        int codigoLocacao = locacoes.size() + 1;

        Locacao locacao =
                new Locacao(
                        veiculo,
                        codigoLocacao,
                        cliente,
                        veiculo.getCidade(),
                        diasReserva);

        locacoes.add(locacao);

        veiculo.setDisponivel(false);

        return true;
    }

    public ArrayList<Locacao> consultaLocacao(String pesquisa) {

        ArrayList<Locacao> resultado =
                new ArrayList<>();

        for (Locacao l : locacoes) {

            if (l.getCliente().equalsIgnoreCase(pesquisa) || l.getVeiculo().getModelo().equalsIgnoreCase(pesquisa)) {

                resultado.add(l);
            }
        }
        //ignoereCase -> tanto faz se for tudo maiusculo/minusculo iniciais maiusculas ou o jeito de escrever

        return resultado;
    }

    public double realizaDevolucao(
            String cliente,
            String cidadeDestino,
            int kmRodado,
            int diasRealizados) {

        Locacao locacao = null;

        for (Locacao l : locacoes) {

            if (l.getCliente().equalsIgnoreCase(cliente) && l.isAtiva()) {

                locacao = l;
                break;
            }
        }

        if (locacao == null) {
            return -1;
        }

        locacao.setDestino(cidadeDestino);
        locacao.setKm_rodado(kmRodado);
        locacao.setQt_dias_realizado(diasRealizados);

        Veiculo veiculo = locacao.getVeiculo();

        veiculo.setCidade(cidadeDestino);

        veiculo.setOdometro(veiculo.getOdometro() + kmRodado);

        veiculo.setDisponivel(true);

        double valorDiarias = locacao.calcularValorDiarias();

        double valorKm = locacao.calcularValorKmRodado();

        double valorFinal = valorDiarias + valorKm;

        int diasReserva = locacao.getQt_dias_reserva();

        if (diasRealizados < diasReserva) {

            int diasNaoUtilizados = diasReserva - diasRealizados;

            double desconto =
                    diasNaoUtilizados * veiculo.getValorDiaria() * 0.20;

            valorFinal -= desconto;
        }

        else if (diasRealizados > diasReserva) {

            int diasExtras = diasRealizados - diasReserva;

            double multa = diasExtras * veiculo.getValorDiaria() * 0.30;

            valorFinal += multa;
        }

        return valorFinal;
    }

    public void relatorioResumo() {

        int totalKm = 0;
        int totalDiasReserva = 0;
        int totalDiasRealizados = 0;

        double valorDiariasContratadas = 0;
        double valorDiariasExtras = 0;
        double valorKmRodado = 0;
        double valorTotal = 0;

        for (Locacao l : locacoes) {

            if (l.isAtiva()) {
                continue;
            }

            totalKm += l.getKm_rodado();

            totalDiasReserva += l.getQt_dias_reserva();

            totalDiasRealizados += l.getQt_dias_realizado();

            valorDiariasContratadas += l.getQt_dias_reserva() * l.getVeiculo().getValorDiaria();

            if (l.getQt_dias_realizado() > l.getQt_dias_reserva()) {

                int diasExtras =
                        l.getQt_dias_realizado() - l.getQt_dias_reserva();

                valorDiariasExtras += diasExtras * l.getVeiculo().getValorDiaria();
            }

            valorKmRodado += l.calcularValorKmRodado();

            valorTotal += l.calcularValorDiarias() + l.calcularValorKmRodado();
        }

        System.out.println("\n===== RESUMO =====");
        System.out.println("KM Rodados: " + totalKm);
        System.out.println("Dias Contratados: " + totalDiasReserva);
        System.out.println("Dias Realizados: " + totalDiasRealizados);
        System.out.println("Valor Diárias Contratadas: R$ " + valorDiariasContratadas);
        System.out.println("Valor Diárias Extras: R$ " + valorDiariasExtras);
        System.out.println("Valor KM Rodados: R$ " + valorKmRodado);
        System.out.println("Valor Total: R$ " + valorTotal);
    }

    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

    public ArrayList<Locacao> getLocacoes() {
        return locacoes;
    }
}