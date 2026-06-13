package dao;

import factory.ConnectionFactory;
import model.ProdutoCarne;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoCarneDAO {

    public void salvar(ProdutoCarne p) {
        String sql = "INSERT INTO produto_carne (id_produto, id_lote, nome_corte, quantidade_estoque_kg, preco_por_kg) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdProduto());
            stmt.setInt(2, p.getIdLote());
            stmt.setString(3, p.getNomeCorte());
            stmt.setDouble(4, p.getQuantidadeEstoqueKg());
            stmt.setDouble(5, p.getPrecoPorKg());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<ProdutoCarne> listarTodos() {
        String sql = "SELECT * FROM produto_carne";
        List<ProdutoCarne> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new ProdutoCarne(
                        rs.getInt("id_produto"), rs.getInt("id_lote"),
                        rs.getString("nome_corte"), rs.getDouble("quantidade_estoque_kg"), rs.getDouble("preco_por_kg")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizar(ProdutoCarne p) {
        String sql = "UPDATE produto_carne SET id_lote = ?, nome_corte = ?, quantidade_estoque_kg = ?, preco_por_kg = ? WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdLote());
            stmt.setString(2, p.getNomeCorte());
            stmt.setDouble(3, p.getQuantidadeEstoqueKg());
            stmt.setDouble(4, p.getPrecoPorKg());
            stmt.setInt(5, p.getIdProduto());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto_carne WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void exibirRelatorioRastreabilidade() {
        String sql = "SELECT * FROM public.vw_rastreabilidade_estoque";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- RELATÓRIO DE RASTREABILIDADE (VIEW) ---");
            boolean temDados = false;
            while (rs.next()) {
                temDados = true;
                System.out.println("Corte: " + rs.getString("nome_corte") +
                        " | Estoque: " + rs.getBigDecimal("quantidade_estoque_kg") + "kg" +
                        " | Lote Origem: " + rs.getInt("id_lote") +
                        " | Fornecedor: " + rs.getString("nome_fornecedor"));
            }
            if (!temDados) {
                System.out.println("(Nenhum dado retornado pela View - Banco Vazio)");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao ler a View: " + e.getMessage());
        }
    }

    public double obterRendimentoLote(int idLote) {
        String sql = "SELECT public.fn_calcular_rendimento_lote(?) AS rendimento";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLote);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("rendimento");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao chamar Function: " + e.getMessage());
        }
        return 0.0;
    }


    public void executarItemVendaProcedure(int idVenda, int idProduto, double qtdKg) {
        String sql = "CALL public.sp_registrar_item_venda(?::int, ?::int, ?::numeric)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            stmt.setInt(2, idProduto);
            stmt.setBigDecimal(3, new java.math.BigDecimal(String.valueOf(qtdKg)));

            stmt.execute();
            System.out.println("Procedure executada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao executar Procedure (Validação): " + e.getMessage());
        }
    }
}
