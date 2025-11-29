package src;

public class Caminhao extends Veiculo{

    private double capacidadeCarga;

    public Caminhao(String marca, String modelo, String placa, int ano,
                    double quilometragem, double consumoMedio, double capacidadeTanque,
                    double capacidadeCarga) {
                         
        super(marca, modelo, placa, ano, quilometragem, consumoMedio, capacidadeTanque);
        this.capacidadeCarga = capacidadeCarga;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(double capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }

    @Override
    public double calcularAutonomia() {
        return capacidadeTanque * consumoMedio;
    }

    @Override
    public String toString() {
        return String.format("Caminhão: %s %s - %s - %.1f kg - %d km",
                marca, modelo, placa, capacidadeCarga, (int) quilometragem);
    }



}
