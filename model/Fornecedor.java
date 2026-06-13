package model;

public class Fornecedor {
    private int idFornecedor;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String status;

    public Fornecedor() {}
    public Fornecedor(int idFornecedor, String nome, String cpfCnpj, String telefone, String status) {
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.status = status;
    }

    public int getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}