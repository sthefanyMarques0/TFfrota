import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorFrota {
    private List<Veiculo> frota;

    public GerenciadorFrota() {
        this.frota = new ArrayList<>();
        inicializarDadosExemplo();
    }

    // Dados de ex
    private void inicializarDadosExemplo() {
        frota.add(new Carro("Toyota", "Hilux", "ABC-1234", 2020, 45000, 12.5, 50.0, 4, "Flex"));
        frota.add(new Carro("Honda", "Fit", "DEF-5678", 2021, 30000, 11.8, 47.0, 4, "Gasolina"));
        frota.add(new Moto("Honda", "MT 07", "GHI-9012", 2022, 15000, 40.0, 15.0, 160, true));
        frota.add(new Moto("Yamaha", "CG 160", "JKL-3456", 2021, 8000, 20.5, 14.0, 689, true));
        // frota.add(new Caminhao("Volvo", "FH 540", "MNO-7890", 2019, 120000, 3.2, 600.0, 25.0, 3));
        // frota.add(new Caminhao("Mercedes", "Actros", "PQR-1234", 2020, 80000, 3.5, 550.0, 20.0, 2));
    }

    // parte CRUD do codigo aqui:
    public void adicionarVeiculo(Veiculo veiculo) {
        frota.add(veiculo);
    }

     public void removerVeiculo(Veiculo veiculo) {
        frota.remove(veiculo);
    }

    public List<Veiculo> getTodosVeiculos() {
        return new ArrayList<>(frota);
    }

    public List<Veiculo> buscarPorMarca(String marca) {
        return frota.stream()
                .filter(v -> v.getMarca().toLowerCase().contains(marca.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Veiculo> buscarPorPlaca(String placa) {
        return frota.stream()
                .filter(v -> v.getPlaca().toLowerCase().contains(placa.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Veiculo> buscarPorPlacaExata(String placa) {
        return frota.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    public void removerVeiculo(Veiculo veiculo) {
        frota.remove(veiculo);
    }

    // aqui começa a programação funcional
    public long getTotalVeiculos() {
        return frota.size();
    }

    public long getTotalCarros() {
        return frota.stream().filter(v -> v instanceof Carro).count();
    }

    public long getTotalMotos() {
        return frota.stream().filter(v -> v instanceof Moto).count();
    }

  //  public long getTotalCaminhoes() {
  //      return frota.stream().filter(v -> v instanceof Caminhao).count();
  //  }

    public double getSomaQuilometragem() {
        return frota.stream().mapToDouble(Veiculo::getQuilometragem).sum();
    }

    public double getMediaQuilometragem() {
        return frota.stream().mapToDouble(Veiculo::getQuilometragem).average().orElse(0.0);
    }

    public double getSomaAutonomia() {
        return frota.stream().mapToDouble(Veiculo::calcularAutonomia).sum();
    }

    public double getMediaConsumo() {
        return frota.stream().mapToDouble(Veiculo::getConsumoMedio).average().orElse(0.0);
    }

    // funcionalidade extra: Relatório de Manutenção
       public List<Veiculo> getVeiculosParaManutencao() {
        List<Veiculo> resultado = new ArrayList<>();
        final double LIMIAR_MANUTENCAO = 50000.0; 
        for (Veiculo veiculo : frota) {
            if (veiculo.getQuilometragem() >= LIMIAR_MANUTENCAO) {
                resultado.add(veiculo);
            }
        }

        return resultado;
    }

    // funcionalidade extra: Veiculo com maior quilometragem
    public Optional<Veiculo> getVeiculoMaisRodado() {
        return frota.stream()
                .max((v1, v2) -> Double.compare(v1.getQuilometragem(), v2.getQuilometragem()));
    }
}
