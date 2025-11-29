package src;

public class Moto extends Veiculo {
    private int cilindradas;
    private boolean partidaEletrica;

    public Moto(String marca, String modelo, String placa, int ano,
                double quilometragem, double consumoMedio, double capacidadeTanque,
                int cilindradas, boolean partidaEletrica) {
        super(marca, modelo, placa, ano, quilometragem, consumoMedio, capacidadeTanque);
        this.cilindradas = cilindradas;
        this.partidaEletrica = partidaEletrica;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }

    public boolean isPartidaEletrica() {
        return partidaEletrica;
    }

    public void setPartidaEletrica(boolean partidaEletrica) {
        this.partidaEletrica = partidaEletrica;
    }

    @Override
    public double calcularAutonomia() {
        return capacidadeTanque * consumoMedio;
    }

    @Override
    public String toString() {
        return String.format("Moto: %s %s - %s - %d cc - %d km",
                marca, modelo, placa, cilindradas, (int) quilometragem);
    }
}