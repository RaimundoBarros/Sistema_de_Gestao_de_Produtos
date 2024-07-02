package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SistemController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button cadastra;
    @FXML
    private Hyperlink retirar;
    @FXML
    private Hyperlink emprestar;
    @FXML
    private Hyperlink relatorios;

    @FXML
    private void initialize() {
        // Configuração inicial, se necessário
    }

    @FXML
    private void abrirTelaCadastro() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CadastroProduto.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Produto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirTelaRetirada() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Retirada.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Retirada de Produto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirTelaEmprestimo() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Emprestimo.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Empréstimo de Produto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirTelaRelatorios() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Relatorios.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Relatórios de Produto");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
