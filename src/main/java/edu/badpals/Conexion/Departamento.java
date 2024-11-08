package edu.badpals.Conexion;

public class Departamento {

    private Integer num_departamento;
    private String nome_departamento;
    private String nss_dirige;
    private String data_direccion;

    public Departamento() {
    }

    public Departamento(Integer num_departamento, String nome_departamento, String nss_dirige, String data_direccion) {
        this.num_departamento = num_departamento;
        this.nome_departamento = nome_departamento;
        this.nss_dirige = nss_dirige;
        this.data_direccion = data_direccion;
    }

    public Integer getNum_departamento() {
        return num_departamento;
    }

    public void setNum_departamento(Integer num_departamento) {
        this.num_departamento = num_departamento;
    }

    public String getNome_departamento() {
        return nome_departamento;
    }

    public void setNome_departamento(String nome_departamento) {
        this.nome_departamento = nome_departamento;
    }

    public String getNss_dirige() {
        return nss_dirige;
    }

    public void setNss_dirige(String nss_dirige) {
        this.nss_dirige = nss_dirige;
    }

    public String getData_direccion() {
        return data_direccion;
    }

    public void setData_direccion(String data_direccion) {
        this.data_direccion = data_direccion;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "num_departamento=" + num_departamento +
                ", nome_departamento='" + nome_departamento + '\'' +
                ", nss_dirige='" + nss_dirige + '\'' +
                ", data_direccion='" + data_direccion + '\'' +
                '}';
    }
}
