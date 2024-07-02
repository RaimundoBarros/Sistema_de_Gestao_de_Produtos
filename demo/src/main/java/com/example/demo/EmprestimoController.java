package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.Date;

public class EmprestimoController {

    @FXML
    private TextField codigobarra;
    @FXML
    private TextField instrutor;
    @FXML
    private TextField data;
    @FXML
    private TextField quantidade;
    @FXML
    private TextField observacao;
    @FXML
    private Label mensagem;
    @FXML
    private Button emprestarButton;

    @FXML
    private void initialize() {
        emprestarButton.setOnAction(this::emprestarProduto);
    }

    @FXML
    private void emprestarProduto(ActionEvent event) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        String codBarra = codigobarra.getText();
        int quant = Integer.parseInt(quantidade.getText());

        boolean sucesso = produtoDAO.emprestarProduto(codBarra, quant, instrutor.getText(), Date.valueOf(data.getText()), observacao.getText());
        if (sucesso) {
            mensagem.setText("Produto emprestado com sucesso!");
        } else {
            mensagem.setText("Erro ao emprestar produto.");
        }
    }
}