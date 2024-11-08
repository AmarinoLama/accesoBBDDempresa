package edu.badpals.Conexion;

public class Proxecto {

    private Integer num_proxecto;
    private String nome_proxecto;
    private String lugar;
    private Integer num_departamento;

    public Proxecto() {
    }

    public Proxecto(Integer num_proxecto, String nome_proxecto, String lugar, Integer num_departamento) {
        this.num_proxecto = num_proxecto;
        this.nome_proxecto = nome_proxecto;
        this.lugar = lugar;
        this.num_departamento = num_departamento;
    }

    public Integer getNum_proxecto() {
        return num_proxecto;
    }

    public void setNum_proxecto(Integer num_proxecto) {
        this.num_proxecto = num_proxecto;
    }

    public Integer getNum_departamento() {
        return num_departamento;
    }

    public void setNum_departamento(Integer num_departamento) {
        this.num_departamento = num_departamento;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getNome_proxecto() {
        return nome_proxecto;
    }

    public void setNome_proxecto(String nome_proxecto) {
        this.nome_proxecto = nome_proxecto;
    }

    @Override
    public String toString() {
        return "Proxecto{" +
                "num_proxecto=" + num_proxecto +
                ", nome_proxecto='" + nome_proxecto + '\'' +
                ", lugar='" + lugar + '\'' +
                ", num_departamento=" + num_departamento +
                '}';
    }
}
