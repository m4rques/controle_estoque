package model;

public class ProdutoCarne {
    private int idProduto;
    private int idLote;
    private String nomeCorte;
    private double quantidadeEstoqueKg;
    private double precoPorKg;

    public ProdutoCarne() {}
    public ProdutoCarne(int idProduto, int idLote, String nomeCorte, double quantidadeEstoqueKg, double precoPorKg) {
        this.idProduto = idProduto;
        this.idLote = idLote;
        this.nomeCorte = nomeCorte;
        this.quantidadeEstoqueKg = quantidadeEstoqueKg;
        this.precoPorKg = precoPorKg;
    }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }
    public String getNomeCorte() { return nomeCorte; }
    public void setNomeCorte(String nomeCorte) { this.nomeCorte = nomeCorte; }
    public double getQuantidadeEstoqueKg() { return quantidadeEstoqueKg; }
    public void setQuantidadeEstoqueKg(double quantidadeEstoqueKg) { this.quantidadeEstoqueKg = quantidadeEstoqueKg; }
    public double getPrecoPorKg() { return precoPorKg; }
    public void setPrecoPorKg(double precoPorKg) { this.precoPorKg = precoPorKg; }
}