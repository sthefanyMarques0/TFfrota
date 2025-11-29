package src;

public class Carro extends Veiculo {
    private int numeroPortas;
    private String tipoCombustivel;

    public Carro(String marca, String modelo, String placa, int ano,
                 double quilometragem, double consumoMedio, double capacidadeTanque,
                 int numeroPortas, String tipoCombustivel) {
        super(marca, modelo, placa, ano, quilometragem, consumoMedio, capacidadeTanque);
        this.numeroPortas = numeroPortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    public void setNumeroPortas(int numeroPortas) {
        this.numeroPortas = numeroPortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    @Override
    public double calcularAutonomia() {
        return capacidadeTanque * consumoMedio;
    }

    @Override
    public String toString() {
        return String.format("Carro: %s %s - %s - %d portas - %d km",
                marca, modelo, placa, numeroPortas, (int) quilometragem);
    }
}