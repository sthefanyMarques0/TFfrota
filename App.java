import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Optional;

public class App {
    private JFrame frame;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private GerenciadorFrota gerenciador;

    public App() {
        gerenciador = new GerenciadorFrota();

        frame = new JFrame("Gerenciamento de Frota");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());
          
        modeloTabela = new DefaultTableModel(
        new Object[]{"Placa", "Marca", "Modelo", "Ano", "Km", "Tipo"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        frame.add(scroll, BorderLayout.CENTER);
    
        JPanel botoes = new JPanel(new GridLayout(1, 0, 5, 5));
        JButton btnListar = new JButton("Listar Frota");
        JButton btnAdicionar = new JButton("Adicionar Veículo");
        JButton btnRemover = new JButton("Remover Veículo");
        JButton btnMediaConsumo = new JButton("Média de Consumo");
        JButton btnManutencao = new JButton("Manutenção");
        JButton btnMaisRodado = new JButton("Mais Rodado");

        botoes.add(btnAdicionar);
        botoes.add(btnRemover);
        botoes.add(btnListar);
        botoes.add(btnMediaConsumo);
        botoes.add(btnManutencao);
        botoes.add(btnMaisRodado);

        frame.add(botoes, BorderLayout.SOUTH);

        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarVeiculos();
            }
        });

        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarVeiculo();
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerVeiculo();
            }
        });

        btnMediaConsumo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularMediaConsumo();
            }
        });

        btnManutencao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                veiculosParaManutencao();
            }
        });

        btnMaisRodado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarMaisRodado();
            }
        });

        
        frame.setVisible(true);
    }

    private void listarVeiculos() {
        modeloTabela.setRowCount(0);

        List<Veiculo> veiculos = gerenciador.getTodosVeiculos();
        for (Veiculo v : veiculos) {
            modeloTabela.addRow(new Object[]{
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAno(),
                    v.getQuilometragem(),
                    tipoVeiculo(v)
            });
        }
    }

  private void adicionarVeiculo() {
    Veiculo novo1 = new Carro("Fiat", "Argo", "NEW-" + (int) (Math.random() * 9999),  2023, 12000, 13.5, 45.0, 4, "Flex");
    Veiculo novo2 = new Carro("Chevrolet", "Onix", "XYZ-9999",2020, 60000, 12.0, 44.0, 4, "Flex");
    gerenciador.adicionarVeiculo(novo1);
    gerenciador.adicionarVeiculo(novo2);
    listarVeiculos();
    JOptionPane.showMessageDialog(frame, "Veículo de exemplo adicionado!");
}

    private void removerVeiculo() {
        String placa = JOptionPane.showInputDialog(frame, "Digite a placa do veículo a ser removido:");
        if (placa == null || placa.trim().isEmpty()) {
            return;
        }

        // O Optional serve como um verificador, tipo um if, else para ver se o veiculo existe ou não.
        Optional<Veiculo> veiculoOpt = gerenciador.buscarPorPlacaExata(placa.trim());
        if (veiculoOpt.isPresent()) {
            gerenciador.removerVeiculo(veiculoOpt.get());
            listarVeiculos();
            JOptionPane.showMessageDialog(frame, "Veículo removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Veículo não encontrado.");
        }
    }

    private void calcularMediaConsumo() {
        double media = gerenciador.getMediaConsumo();
        JOptionPane.showMessageDialog(frame,
                String.format("Média de consumo da frota: %.2f km/L", media));
    }

    private void veiculosParaManutencao() {
        List<Veiculo> manutencao = gerenciador.getVeiculosParaManutencao();

        modeloTabela.setRowCount(0);

        if (manutencao.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum veículo precisa de manutenção.");
            listarVeiculos();
            return;
        }

        for (Veiculo v : manutencao) {
            modeloTabela.addRow(new Object[]{
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAno(),
                    v.getQuilometragem(),
                    tipoVeiculo(v)
            });
        }

        JOptionPane.showMessageDialog(frame,
                manutencao.size() + " veículo(s) precisam de manutenção!");
    }

    private void mostrarMaisRodado() {
        Optional<Veiculo> maisRodado = gerenciador.getVeiculoMaisRodado();

        if (maisRodado.isPresent()) {
            Veiculo v = maisRodado.get();
            JOptionPane.showMessageDialog(frame,
                    "Veículo mais rodado:\n\n" +
                            "Marca: " + v.getMarca() +
                            "\nModelo: " + v.getModelo() +
                            "\nPlaca: " + v.getPlaca() +
                            "\nQuilometragem: " + v.getQuilometragem() + " km");
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
