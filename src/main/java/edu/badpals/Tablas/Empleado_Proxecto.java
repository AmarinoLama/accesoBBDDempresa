package edu.badpals.Tablas;

public class Empleado_Proxecto {

    private String nss_empregado;
    private Integer num_proxecto;
    private Integer horas_semanais;

    public Empleado_Proxecto() {
    }

    public Empleado_Proxecto(String nss_empregado, Integer num_proxecto, Integer horas_semanais) {
        this.nss_empregado = nss_empregado;
        this.num_proxecto = num_proxecto;
        this.horas_semanais = horas_semanais;
    }

    public String getNss_empregado() {
        return nss_empregado;
    }

    public void setNss_empregado(String nss_empregado) {
        this.nss_empregado = nss_empregado;
    }

    public Integer getNum_proxecto() {
        return num_proxecto;
    }

    public void setNum_proxecto(Integer num_proxecto) {
        this.num_proxecto = num_proxecto;
    }

    public Integer getHoras_semanais() {
        return horas_semanais;
    }

    public void setHoras_semanais(Integer horas_semanais) {
        this.horas_semanais = horas_semanais;
    }

    @Override
    public String toString() {
        return "Empleado_Proxecto{" +
                "nss_empregado='" + nss_empregado + '\'' +
                ", num_proxecto=" + num_proxecto +
                ", horas_semanais=" + horas_semanais +
                '}';
    }
}
