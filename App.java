import src.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class App {
    private JFrame frame;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private GerenciadorFrota gerenciador;

    // exibir números com virgula como separador decimal
    private static final DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public App() {
        gerenciador = new GerenciadorFrota();

        frame = new JFrame("Gerenciamento de Frota");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLayout(new BorderLayout());

        // Configuração da Tabela
        modeloTabela = new DefaultTableModel(
                new Object[]{"Placa", "Marca", "Modelo", "Ano", "Km", "Consumo (km/L)", "Autonomia (km)", "Tipo"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        frame.add(scroll, BorderLayout.CENTER);

        // Configuração dos Botoes
        JPanel botoes = new JPanel(new GridLayout(1, 0, 5, 5));
        JButton btnListar = new JButton("Listar Frota");
        JButton btnAdicionar = new JButton("Cadastrar Veículo");
        JButton btnRemover = new JButton("Remover Veículo");
        JButton btnBuscar = new JButton("Procurar Veículo");
        JButton btnEstatisticas = new JButton("Estatísticas");
        JButton btnManutencao = new JButton("Manutenção");
        JButton btnMaisRodado = new JButton("Mais Rodado");

        botoes.add(btnAdicionar);
        botoes.add(btnRemover);
        botoes.add(btnListar);
        botoes.add(btnBuscar);
        botoes.add(btnEstatisticas);
        botoes.add(btnManutencao);
        botoes.add(btnMaisRodado);

        frame.add(botoes, BorderLayout.SOUTH);

        // Listeners
        btnListar.addActionListener(e -> listarVeiculos());
        btnAdicionar.addActionListener(e -> adicionarVeiculo());
        btnRemover.addActionListener(e -> removerVeiculo());
        btnBuscar.addActionListener(e -> buscarVeiculo());
        btnEstatisticas.addActionListener(e -> mostrarEstatisticas());
        btnManutencao.addActionListener(e -> mostrarManutencao());
        btnMaisRodado.addActionListener(e -> mostrarMaisRodado());

        // Inicia a aplicação listando os dados de exemplo
        listarVeiculos();

        frame.setVisible(true);
    }

    private void listarVeiculos() { // Item 8.a
        modeloTabela.setRowCount(0); // Limpa a tabela
        for (Veiculo v : gerenciador.getFrota()) {
            modeloTabela.addRow(new Object[]{
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAno(),
                    df.format(v.getQuilometragem()),
                    df.format(v.getConsumoMedio()),
                    df.format(v.calcularAutonomia()),
                    tipoVeiculo(v)
            });
        }
    }

    private void adicionarVeiculo() {
        // 1. Selecionar o tipo de veículo
        String[] tipos = {"Carro", "Moto", "Caminhão"};
        String tipoSelecionado = (String) JOptionPane.showInputDialog(
                frame,
                "Selecione o tipo de veículo para cadastrar:",
                "Cadastro de Veículo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );

        if (tipoSelecionado == null) return;

        // 2. Coletar dados comuns
        try {
            String marca = JOptionPane.showInputDialog(frame, "Marca:");
            String modelo = JOptionPane.showInputDialog(frame, "Modelo:");
            String placa = JOptionPane.showInputDialog(frame, "Placa (exata):");

            if (marca == null || modelo == null || placa == null) return;

            int ano = Integer.parseInt(JOptionPane.showInputDialog(frame, "Ano:"));
            // Note: O uso de String.replace para lidar com usuarios que usam vírgula (,)
            // ao inves de ponto (.) para decimais.
            double km = Double.parseDouble(JOptionPane.showInputDialog(frame, "Quilometragem:").replace(',', '.'));
            double consumo = Double.parseDouble(JOptionPane.showInputDialog(frame, "Consumo Médio (km/L):").replace(',', '.'));
            double capacidadeTanque = Double.parseDouble(JOptionPane.showInputDialog(frame, "Capacidade Tanque (L):").replace(',', '.'));

            Veiculo novoVeiculo = null;

            // 3. Coletar dados específicos e criar o objeto
            if (tipoSelecionado.equals("Carro")) {
                int portas = Integer.parseInt(JOptionPane.showInputDialog(frame, "Número de Portas:"));
                String combustivel = JOptionPane.showInputDialog(frame, "Tipo de Combustível:");
                novoVeiculo = new Carro(marca, modelo, placa, ano, km, consumo, capacidadeTanque, portas, combustivel);
            } else if (tipoSelecionado.equals("Moto")) {
                int cilindradas = Integer.parseInt(JOptionPane.showInputDialog(frame, "Cilindradas (cc):"));
                int partida = JOptionPane.showConfirmDialog(frame, "Possui partida elétrica?", "Partida", JOptionPane.YES_NO_OPTION);
                boolean partidaEletrica = (partida == JOptionPane.YES_OPTION);
                novoVeiculo = new Moto(marca, modelo, placa, ano, km, consumo, capacidadeTanque, cilindradas, partidaEletrica);
            } else if (tipoSelecionado.equals("Caminhão")) {
                double capacidadeCarga = Double.parseDouble(JOptionPane.showInputDialog(frame, "Capacidade de Carga (kg):").replace(',', '.'));
                novoVeiculo = new Caminhao(marca, modelo, placa, ano, km, consumo, capacidadeTanque, capacidadeCarga);
            }

            // 4. Adicionar ao Gerenciador e Atualizar
            if (novoVeiculo != null) {
                gerenciador.adicionarVeiculo(novoVeiculo);
                listarVeiculos();
                JOptionPane.showMessageDialog(frame, tipoSelecionado + " cadastrado com sucesso!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Erro: Entrada de dado numérico inválida. Verifique o uso de pontos ou números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Ocorreu um erro no cadastro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerVeiculo() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a PLACA do veículo a ser removido:");
        if (placa != null && !placa.trim().isEmpty()) {
            if (gerenciador.removerVeiculo(placa.trim())) {
                JOptionPane.showMessageDialog(frame, "Veículo com placa " + placa + " removido com sucesso.");
                listarVeiculos();
            } else {
                JOptionPane.showMessageDialog(frame, "Veículo com placa " + placa + " não encontrado.");
            }
        }
    }

    // Implementação da Busca/Procura
    private void buscarVeiculo() {
        String busca = JOptionPane.showInputDialog(frame, "Digite a PLACA ou parte da MARCA para buscar:");
        if (busca == null || busca.trim().isEmpty()) {
            return;
        }

        List<Veiculo> resultados = new ArrayList<>();

        // Tenta busca exata por placa primeiro
        Optional<Veiculo> veiculoOpt = gerenciador.buscarPorPlacaExata(busca.trim());
        if (veiculoOpt.isPresent()) {
            resultados.add(veiculoOpt.get());
        } else {
            // Se não encontrar, busca por marca ou placa parcial
            resultados.addAll(gerenciador.buscarPorMarca(busca.trim()));
            resultados.addAll(gerenciador.buscarPorPlaca(busca.trim()));
        }

        modeloTabela.setRowCount(0);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum veículo encontrado com a busca '" + busca + "'.");
            listarVeiculos(); // Volta a listar todos
            return;
        }

        // Exibe os resultados na tabela
        for (Veiculo v : resultados) {
            modeloTabela.addRow(new Object[]{
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAno(),
                    df.format(v.getQuilometragem()),
                    df.format(v.getConsumoMedio()),
                    df.format(v.calcularAutonomia()),
                    tipoVeiculo(v)
            });
        }

        JOptionPane.showMessageDialog(frame,
                resultados.size() + " veículo(s) encontrado(s)!");
    }

    private void mostrarEstatisticas() {
        long totalVeiculos = gerenciador.getTotalVeiculos(); // Item i - Total
        long totalCarros = gerenciador.getTotalCarros();     // Item i - Por tipo
        long totalMotos = gerenciador.getTotalMotos();       // Item i - Por tipo
        long totalCaminhoes = gerenciador.getTotalCaminhoes(); // Item i - Por tipo

        double somaKm = gerenciador.getSomaQuilometragem(); // Item ii - Somatório numérico
        double mediaConsumo = gerenciador.getMediaConsumo(); // Item iii - Média numérica

        String estatisticas = String.format(
                "<html><font size='+1'>Estatísticas da Frota</font><br><br>" +
                        "<b>Número de Instâncias (Total e por Tipo):</b><br>" +
                        "- Total de Veículos: %d<br>" +
                        "- Carros: %d<br>" +
                        "- Motos: %d<br>" +
                        "- Caminhões: %d<br><br>" +
                        "<b>Somatório Numérico (Quilometragem):</b><br>" +
                        "- Total Rodado na Frota: <b>%s km</b><br><br>" +
                        "<b>Média Numérica (Consumo):</b><br>" +
                        "- Média de Consumo da Frota: <b>%s km/L</b></html>",
                totalVeiculos, totalCarros, totalMotos, totalCaminhoes,
                df.format(somaKm), df.format(mediaConsumo)
        );

        JOptionPane.showMessageDialog(frame, estatisticas, "Relatório de Estatísticas", JOptionPane.INFORMATION_MESSAGE);
    }


    private void mostrarManutencao() {
        List<Veiculo> manutencao = gerenciador.getVeiculosParaManutencao();

        modeloTabela.setRowCount(0); // Limpa a tabela para mostrar o resultado

        if (manutencao.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum veículo precisa de manutenção no momento (abaixo de 50.000 km).");
            listarVeiculos(); // Volta a listar todos
            return;
        }

        for (Veiculo v : manutencao) {
            modeloTabela.addRow(new Object[]{
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAno(),
                    df.format(v.getQuilometragem()),
                    df.format(v.getConsumoMedio()),
                    df.format(v.calcularAutonomia()),
                    tipoVeiculo(v)
            });
        }

        JOptionPane.showMessageDialog(frame,
                manutencao.size() + " veículo(s) precisam de manutenção (acima de 50.000 km)!");
    }

    private void mostrarMaisRodado() {
        Optional<Veiculo> maisRodado = gerenciador.getVeiculoMaisRodado();

        if (maisRodado.isPresent()) {
            Veiculo v = maisRodado.get();
            String info = String.format(
                    "Veículo mais rodado:\n\n" +
                            "Marca: %s\n" +
                            "Modelo: %s\n" +
                            "Placa: %s\n" +
                            "Quilometragem: %s km\n" +
                            "Tipo: %s",
                    v.getMarca(), v.getModelo(), v.getPlaca(),
                    df.format(v.getQuilometragem()), tipoVeiculo(v)
            );
            JOptionPane.showMessageDialog(frame, info);
        } else {
            JOptionPane.showMessageDialog(frame, "Nenhum veículo encontrado.");
        }
    }

    private String tipoVeiculo(Veiculo v) {
        if (v instanceof Carro) return "Carro";
        if (v instanceof Moto) return "Moto";
        if (v instanceof Caminhao) return "Caminhão";
        return "Outro";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}