package src;

public abstract class Veiculo {
    protected String marca;
    protected String modelo;
    protected String placa;
    protected int ano;
    protected double quilometragem;
    protected double consumoMedio; // km/l
    protected double capacidadeTanque; // litros

    public Veiculo(String marca, String modelo, String placa, int ano, double quilometragem, double consumoMedio, double capacidadeTanque) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.consumoMedio = consumoMedio;
        this.capacidadeTanque = capacidadeTanque;
    }

    // Getters e Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public double getCapacidadeTanque() {
        return capacidadeTanque;
    }

    public void setCapacidadeTanque(double capacidadeTanque) {
        this.capacidadeTanque = capacidadeTanque;
    }

    public abstract double calcularAutonomia();

    public void realizarViagem(double distancia) {
        this.quilometragem += distancia;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s) - %d km", marca, modelo, placa, (int) quilometragem);
    }
}