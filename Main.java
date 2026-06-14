import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Locadora locadora = new Locadora();
        locadora.carregaDados();

        int opcao = 0;

        while (opcao != 8) {

            System.out.println("\n===== LOCADORA =====");
            System.out.println("1 - Consultar veículos");
            System.out.println("2 - Realizar locação");
            System.out.println("3 - Realizar devolução");
            System.out.println("4 - Consultar locações");
            System.out.println("5 - Resumo");
            System.out.println("6 - Salvar");
            System.out.println("7 - Integrantes");
            System.out.println("8 - Sair");

            opcao = Teclado.leInt("Opção:");

            switch (opcao) {
                case 1: {
                    String atributo = Teclado.leString("Informe o atributo (modelo, cor, ano ou cidade): ");
                    String valor = Teclado.leString("Informe o valor buscado: ");

                    ArrayList<Veiculo> encontrados = locadora.consultaVeiculo(atributo, valor);

                    if (encontrados.isEmpty()) {
                        System.out.println("Nenhum veículo encontrado com essas especificações.");
                    } else {
                        System.out.println("\n--- VEÍCULOS ENCONTRADOS ---");
                        for (Veiculo v : encontrados) {
                            System.out.println(v.toString());
                            System.out.println("----------------------------");
                        }
                    }
                    break;
                }
                case 2: {
                    String cidadeOrigem = Teclado.leString("Informe a cidade de origem: ");
                    
                    ArrayList<Veiculo> veiculosNaCidade = locadora.consultaVeiculo("cidade", cidadeOrigem);
                    boolean temDisponivel = false;
                    
                    System.out.println("\n--- VEÍCULOS DISPONÍVEIS ---");
                    for (Veiculo v : veiculosNaCidade) {
                        if (v.isDisponivel()) {
                            System.out.println(v.toString());
                            System.out.println("----------------------------");
                            temDisponivel = true;
                        }
                    }

                    if (!temDisponivel) {
                        System.out.println("Não há veículos disponíveis nesta cidade no momento.");
                        break;
                    }

                    int codigo = Teclado.leInt("Informe o código do veículo que deseja alugar: ");
                    String nomeCliente = Teclado.leString("Informe seu nome: ");
                    int dias = Teclado.leInt("Quantidade de diárias: ");

                    Veiculo veiculoEscolhido = null;
                    for (Veiculo v : locadora.getVeiculos()) {
                        if (v.getCodigo() == codigo && v.isDisponivel()) {
                            veiculoEscolhido = v;
                            break;
                        }
                    }

                    if (veiculoEscolhido != null) {
                        double estimativa = veiculoEscolhido.getValorDiaria() * dias;
                        System.out.printf("Valor total estimado das diárias: R$ %.2f\n", estimativa);
                        
                        char confirma = Teclado.leChar("Deseja confirmar a reserva? (S/N): ");
                        if (Character.toUpperCase(confirma) == 'S') {
                            boolean sucesso = locadora.realizaLocacao(codigo, nomeCliente, dias);
                            if (sucesso) {
                                System.out.println("Locação realizada com sucesso!");
                            } else {
                                System.out.println("Falha na locação. Cliente pode já ter locação ativa.");
                            }
                        } else {
                            System.out.println("Locação cancelada.");
                        }
                    } else {
                        System.out.println("Veículo inválido ou indisponível.");
                    }
                    break;
                }
                case 3: {
                    String nomeCliente = Teclado.leString("Informe seu nome: ");
                    
                    Locacao locacaoAtiva = null;
                    for (Locacao l : locadora.getLocacoes()) {
                        if (l.getCliente().equalsIgnoreCase(nomeCliente) && l.isAtiva()) {
                            locacaoAtiva = l;
                            break;
                        }
                    }

                    if (locacaoAtiva == null) {
                        System.out.println("Nenhuma locação ativa encontrada para este cliente.");
                        break;
                    }

                    String cidadeDevolucao = Teclado.leString("Informe a cidade de devolução: ");
                    int kmPercorrida = Teclado.leInt("Informe a quilometragem percorrida: ");

                    System.out.println("Quantidade de diárias contratadas: " + locacaoAtiva.getQt_dias_reserva());
                    int diasRealizados = Teclado.leInt("Confirme ou informe a quantidade real de dias utilizados: ");

                    double valorFinal = locadora.realizaDevolucao(nomeCliente, cidadeDevolucao, kmPercorrida, diasRealizados);

                    if (valorFinal != -1) {
                        System.out.println("\nDevolução concluída com sucesso!");
                        System.out.printf("Valor final a pagar: R$ %.2f\n", valorFinal);
                    } else {
                        System.out.println("Erro ao realizar a devolução.");
                    }
                    break;
                }
                case 4: {
                    String pesquisa = Teclado.leString("Informe o nome do cliente ou o modelo do veículo: ");
                    ArrayList<Locacao> encontradas = locadora.consultaLocacao(pesquisa);

                    if (encontradas.isEmpty()) {
                        System.out.println("Nenhuma locação encontrada.");
                    } else {
                        System.out.println("\n--- LOCAÇÕES ENCONTRADAS ---");
                        for (Locacao l : encontradas) {
                            System.out.println(l.toString());
                            
                            if (l.isAtiva()) {
                                System.out.println("Status: Ativa");
                            } else {
                                System.out.println("Status: Finalizada");
                            }
                            
                            System.out.println("----------------------------");
                        }
                    }
                    break;
                }
                case 5: {
                    locadora.relatorioResumo();
                    break;
                }
                case 6: {
                    locadora.salvaDados();
                    System.out.println("Dados salvos com sucesso nos arquivos .txt!");
                    break;
                }
                case 7: {
                    System.out.println("Integrante 1: Érico Nilson");
                    System.out.println("Integrante 2: Helam Aristimuño");
                    System.out.println("Integrante 3: Lucas Klauck");
                    System.out.println("Integrante 4: Vitório Barbosa\n");
                    break;
                }
                case 8: {
                    locadora.salvaDados();
                    System.out.println("Saindo...");
                    break;
                }
                default: {
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
                }
            }
        }
    }
}