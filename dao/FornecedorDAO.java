package dao;

import factory.ConnectionFactory;
import model.Fornecedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    public void salvar(Fornecedor f) {
        String sql = "INSERT INTO fornecedor (id_fornecedor, nome, cpf_cnpj, telefone, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getIdFornecedor());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCpfCnpj());
            stmt.setString(4, f.getTelefone());
            stmt.setString(5, f.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Fornecedor> listarTodos() {
        String sql = "SELECT * FROM fornecedor";
        List<Fornecedor> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Fornecedor(
                        rs.getInt("id_fornecedor"), rs.getString("nome"),
                        rs.getString("cpf_cnpj"), rs.getString("telefone"), rs.getString("status")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizar(Fornecedor f) {
        String sql = "UPDATE fornecedor SET nome = ?, cpf_cnpj = ?, telefone = ?, status = ? WHERE id_fornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCpfCnpj());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getStatus());
            stmt.setInt(5, f.getIdFornecedor());
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}