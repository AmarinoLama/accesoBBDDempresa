package edu.badpals.Conexion;

public class Empregado {

    private String nome;
    private String apelido_1;
    private String apelido_2;
    private String nss;
    private String rua;
    private Integer numero_rua;
    private String piso;
    private String cp;
    private String localidade;
    private String data_nacemento;
    private Double salario;
    private String sexo;
    private String nss_supervisa;
    private Integer num_departamento_pertenece;

    public Empregado() {
    }

    public Empregado(String nome, String apelido_1, String apelido_2, String nss, String rua, Integer numero_rua, String piso, String cp, String localidade, String data_nacemento, Double salario, String sexo, String nss_supervisa, Integer num_departamento_pertenece) {
        this.nome = nome;
        this.apelido_1 = apelido_1;
        this.apelido_2 = apelido_2;
        this.nss = nss;
        this.rua = rua;
        this.numero_rua = numero_rua;
        this.piso = piso;
        this.cp = cp;
        this.localidade = localidade;
        this.data_nacemento = data_nacemento;
        this.salario = salario;
        this.sexo = sexo;
        this.nss_supervisa = nss_supervisa;
        this.num_departamento_pertenece = num_departamento_pertenece;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido_1() {
        return apelido_1;
    }

    public void setApelido_1(String apelido_1) {
        this.apelido_1 = apelido_1;
    }

    public String getApelido_2() {
        return apelido_2;
    }

    public void setApelido_2(String apelido_2) {
        this.apelido_2 = apelido_2;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Integer getNumero_rua() {
        return numero_rua;
    }

    public void setNumero_rua(Integer numero_rua) {
        this.numero_rua = numero_rua;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getData_nacemento() {
        return data_nacemento;
    }

    public void setData_nacemento(String data_nacemento) {
        this.data_nacemento = data_nacemento;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNss_supervisa() {
        return nss_supervisa;
    }

    public void setNss_supervisa(String nss_supervisa) {
        this.nss_supervisa = nss_supervisa;
    }

    public Integer getNum_departamento_pertenece() {
        return num_departamento_pertenece;
    }

    public void setNum_departamento_pertenece(Integer num_departamento_pertenece) {
        this.num_departamento_pertenece = num_departamento_pertenece;
    }

    @Override
    public String toString() {
        return "Empregado{" +
                "nome='" + nome + '\'' +
                ", apelido_1='" + apelido_1 + '\'' +
                ", apelido_2='" + apelido_2 + '\'' +
                ", nss='" + nss + '\'' +
                ", rua='" + rua + '\'' +
                ", numero_rua=" + numero_rua +
                ", piso='" + piso + '\'' +
                ", cp='" + cp + '\'' +
                ", localidade='" + localidade + '\'' +
                ", data_nacemento='" + data_nacemento + '\'' +
                ", salario=" + salario +
                ", sexo='" + sexo + '\'' +
                ", nss_supervisa='" + nss_supervisa + '\'' +
                ", num_departamento_pertenece='" + num_departamento_pertenece + '\'' +
                '}';
    }
}
