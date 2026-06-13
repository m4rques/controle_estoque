package main;

import dao.FornecedorDAO;
import dao.ProdutoCarneDAO;
import model.Fornecedor;
import model.ProdutoCarne;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🥩 ====== BEM-VINDO AO SISTEMA FRIGOCONTROL ====== 🥩");

        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        ProdutoCarneDAO produtoCarneDAO = new ProdutoCarneDAO();

        System.out.println("\nVamos começar cadastrando um novo fornecedor no sistema...");
        int idDinamico = (int) (System.currentTimeMillis() % 100000);
        String cnpjDinamico = String.format("12.%03d.678/0001-99", idDinamico % 1000);
        Fornecedor fornecedor = new Fornecedor(
                idDinamico,
                "Fazenda Bela Vista " + idDinamico,
                cnpjDinamico,
                "(11) 99999-1111",
                "ATIVO"
        );
        fornecedorDAO.salvar(fornecedor);
        System.out.println("-> Maravilha! Fornecedor cadastrado com sucesso. O ID gerado foi: " + idDinamico);

        System.out.println("\nAgora, vamos dar entrada em um novo lote de carne bovina...");
        int idProdutoDinamico = (int) (System.currentTimeMillis() % 100000);
        ProdutoCarne p1 = new ProdutoCarne(
                idProdutoDinamico,
                1,
                "Picanha Premium (Lote Especial " + idProdutoDinamico + ")",
                150.0,
                50.0
        );
        produtoCarneDAO.salvar(p1);
        System.out.println("-> Deu certo! A carne já está no estoque físico com o ID: " + idProdutoDinamico);

        System.out.println("\nDando uma olhada no que já temos guardado na câmara fria:");
        List<ProdutoCarne> produtos = produtoCarneDAO.listarTodos();
        for (ProdutoCarne prod : produtos) {
            System.out.println("   🥩 ID: " + prod.getIdProduto() + " | Corte: " + prod.getNomeCorte() + " | Disponível: " + prod.getQuantidadeEstoqueKg() + " kg");
        }

        System.out.println("\nA inflação chegou! Ajustando o preço de venda da nossa Picanha para R$ 72,50/kg...");
        p1.setPrecoPorKg(72.50);
        produtoCarneDAO.atualizar(p1);

        System.out.println("\nPuxando a ficha completa da carne (Testando a VIEW de Rastreabilidade):");
        produtoCarneDAO.exibirRelatorioRastreabilidade();

        System.out.println("\nPedindo para o banco de dados calcular o rendimento de carcaça do Lote 1...");
        double rendimento = produtoCarneDAO.obterRendimentoLote(1);
        System.out.println("-> O banco fez as contas e o rendimento foi de: " + rendimento + "%");

        System.out.println("\nHora de vender! Registrando a saída de 50kg para um cliente...");
        System.out.println("-> Separando 50kg do produto ID " + p1.getIdProduto() + " da nossa câmara fria (Estoque original: 150kg).");
        produtoCarneDAO.executarItemVendaProcedure(1, p1.getIdProduto(), 50.00);

        System.out.println("\nSerá que o banco deu baixa na carne corretamente? Vamos conferir a rastreabilidade novamente:");
        produtoCarneDAO.exibirRelatorioRastreabilidade();

        System.out.println("\nTeste de segurança! Vamos tentar vender 500kg, mas o estoque não aguenta. O banco tem que bloquear isso:");
        produtoCarneDAO.executarItemVendaProcedure(1, p1.getIdProduto(), 500.00);

        System.out.println("\nO dia de trabalho acabou. Encerrando as operações...");

        System.out.println("\n✅ ====== SISTEMA DESLIGADO COM SUCESSO! ====== ✅");
    }
}