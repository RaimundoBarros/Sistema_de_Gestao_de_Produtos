package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProdutoController {

    @FXML
    private TextField codbarra;
    @FXML
    private TextField nomeProduto;
    @FXML
    private TextField marca;
    @FXML
    private TextField validade;
    @FXML
    private TextField lote;
    @FXML
    private TextField descricao;
    @FXML
    private TextField quantidadeField;
    @FXML
    private TableView<?> tabelaprod;
    @FXML
    private TableColumn<?, ?> product;
    @FXML
    private TableColumn<?, ?> marce;
    @FXML
    private TableColumn<?, ?> barra;
    @FXML
    private TableColumn<?, ?> valid;
    @FXML
    private TableColumn<?, ?> numlote;
    @FXML
    private TableColumn<?, ?> descri;
    @FXML
    private TableColumn<?, ?> quantidade;
    @FXML
    private Label mensagemLabel;

    @FXML
    private void initialize() {
        addUpperCaseListener(codbarra);
        addUpperCaseListener(nomeProduto);
    }

    private void addUpperCaseListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue.toUpperCase());
        });
    }

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/app";
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
    private void cadastrarProduto(ActionEvent event) {
        String sql = "INSERT INTO produto (nome, marca, cod_barra, validade, num_lote, descricao, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeProduto.getText());
            pstmt.setString(2, marca.getText());
            pstmt.setString(3, codbarra.getText());
            pstmt.setDate(4, java.sql.Date.valueOf(validade.getText())); // Certifique-se de que a data está no formato correto (yyyy-MM-dd)
            pstmt.setString(5, lote.getText());
            pstmt.setString(6, descricao.getText());
            pstmt.setInt(7, Integer.parseInt(quantidadeField.getText())); // Certifique-se de que 'quantidade' contém um número

            pstmt.executeUpdate();
            mensagemLabel.setText("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            mensagemLabel.setText("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @FXML
    private void botaopesquise(ActionEvent event) {
        String sql = "SELECT * FROM produto WHERE cod_barra = ? OR nome LIKE ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codbarra.getText());
            pstmt.setString(2, "%" + nomeProduto.getText() + "%");
            ResultSet rs = pstmt.executeQuery();

            // Adicionar código para atualizar a tabela com os resultados da pesquisa.
        } catch (Exception e) {
            mensagemLabel.setText("Erro ao pesquisar produto: " + e.getMessage());
        }
    }
}
