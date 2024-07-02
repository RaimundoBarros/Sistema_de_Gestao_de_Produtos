package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class RetiradaController {
    @FXML
    private Label retirar;
    @FXML
    private TextField codigobarra;
    @FXML
    private TextField nome;
    @FXML
    private TextField date;
    @FXML
    private TextField quanti;
    @FXML
    private TextField observa;
    @FXML
    private Button retirarButton;

    @FXML
    private void initialize() {
        addUpperCaseListener(codigobarra);
        addUpperCaseListener(nome);
        addUpperCaseListener(date);
        addUpperCaseListener(quanti);
        addUpperCaseListener(observa);
    }

    private void addUpperCaseListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue.toUpperCase());
        });
    }

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/app"; // Ajustar URL conforme necessário
        String user = "root";
        String password = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @FXML
    public void botaoRetirada(ActionEvent event) {
        if (codigobarra.getText().isEmpty() || nome.getText().isEmpty() || date.getText().isEmpty() || quanti.getText().isEmpty()) {
            retirar.setText("Todos os campos obrigatórios devem ser preenchidos.");
            return;
        }

        int quantidadeRetirada = Integer.parseInt(quanti.getText());

        String transacaoSql = "INSERT INTO transacao (cod_barra, nome, quantidade, data, observacao) VALUES(?, ?, ?, ?, ?)";
        String selectProdutoSql = "SELECT quantidade FROM produto WHERE cod_barra = ?";
        String updateProdutoSql = "UPDATE produto SET quantidade = quantidade - ? WHERE cod_barra = ?";

        try (Connection conn = this.connect();
             PreparedStatement transacaoPstmt = conn.prepareStatement(transacaoSql);
             PreparedStatement selectProdutoPstmt = conn.prepareStatement(selectProdutoSql);
             PreparedStatement updateProdutoPstmt = conn.prepareStatement(updateProdutoSql)) {

            // Verifica a quantidade disponível
            selectProdutoPstmt.setString(1, codigobarra.getText());
            ResultSet rs = selectProdutoPstmt.executeQuery();

            if (rs.next()) {
                int quantidadeDisponivel = rs.getInt("quantidade");
                if (quantidadeRetirada > quantidadeDisponivel) {
                    retirar.setText("Quantidade insuficiente no estoque.");
                    return;
                }
            } else {
                retirar.setText("Produto não encontrado.");
                return;
            }

            // Registra a transação
            transacaoPstmt.setString(1, codigobarra.getText());
            transacaoPstmt.setString(2, nome.getText());
            transacaoPstmt.setInt(3, quantidadeRetirada);
            transacaoPstmt.setDate(4, Date.valueOf(date.getText()));
            transacaoPstmt.setString(5, observa.getText());
            transacaoPstmt.executeUpdate();

            // Atualiza a quantidade do produto (usando o primeiro parâmetro para quantidade e segundo para cod_barra)
            updateProdutoPstmt.setInt(1, quantidadeRetirada);
            updateProdutoPstmt.setString(2, codigobarra.getText());
            updateProdutoPstmt.executeUpdate();

            retirar.setText("Produto retirado com sucesso!");
        } catch (Exception e) {
            retirar.setText("Erro: " + e.getMessage()); // Fornece mensagem de erro mais específica
            System.out.println(e.getMessage());
        }
    }
}
