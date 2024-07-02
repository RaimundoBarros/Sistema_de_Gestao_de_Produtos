package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final String url = "jdbc:mysql://localhost:3306/app";
    private final String user = "root";
    private final String password = "";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }

    public boolean cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produto(nome, marca, cod_barra, validade, num_lote, descricao, VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getMarca());
            pstmt.setString(3, produto.getCodBarra());
            if (produto.getValidade() != null) {
                pstmt.setDate(4, produto.getValidade());
            } else {
                pstmt.setNull(4, Types.DATE);
            }
            if (produto.getNumLote() != null && !produto.getNumLote().isEmpty()) {
                pstmt.setString(5, produto.getNumLote());
            } else {
                pstmt.setNull(5, Types.VARCHAR);
            }
            pstmt.setString(6, produto.getDescricao());
            pstmt.setInt(7, produto.getQuantidade());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        produto.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("marca"),
                        rs.getString("cod_barra"),
                        rs.getDate("validade"),
                        rs.getString("num_lote"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public List<Produto> buscarPorNomeOuCodigoBarras(String valor, String campo) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE " + campo + " LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + valor + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("marca"),
                            rs.getString("cod_barra"),
                            rs.getDate("validade"),
                            rs.getString("num_lote"),
                            rs.getString("descricao"),
                            rs.getInt("quantidade")
                    );
                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }


    public boolean retirarProduto(String codBarra, int quantidade) {
        String sql = "UPDATE produto SET quantidade = quantidade - ? WHERE cod_barra = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantidade);
            pstmt.setString(2, codBarra);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emprestarProduto(String codBarra, int quantidade, String instrutor, Date dataEmprestimo, String observacao) {
        if (retirarProduto(codBarra, quantidade)) {
            String sql = "INSERT INTO emprestimo(cod_barra, quantidade, instrutor, data_emprestimo, observacao) VALUES(?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, codBarra);
                pstmt.setInt(2, quantidade);
                pstmt.setString(3, instrutor);
                pstmt.setDate(4, dataEmprestimo);
                pstmt.setString(5, observacao);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
