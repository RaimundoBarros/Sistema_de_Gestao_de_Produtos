package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.Date;

public class CadastroProdutoController {

    @FXML
    private TextField nomeProduto;
    @FXML
    private TextField marca;
    @FXML
    private TextField codbarra;
    @FXML
    private TextField validade;
    @FXML
    private TextField lote;
    @FXML
    private TextField quantidadeField;
    @FXML
    private TextField descricao;
    @FXML
    private Label mensagemLabel;
    @FXML
    private Button cadastraButton;

    @FXML
    private void initialize() {
        cadastraButton.setOnAction(this::cadastrarProduto);

        // Adicionando listeners para transformar texto em maiúsculas RAIMUNDO BARROS
        addUpperCaseListener(nomeProduto);
        addUpperCaseListener(marca);
        addUpperCaseListener(codbarra);
        addUpperCaseListener(validade);
        addUpperCaseListener(lote);
        addUpperCaseListener(quantidadeField);
        addUpperCaseListener(descricao);
    }

    private void addUpperCaseListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue.toUpperCase());
        });
    }

    @FXML
    private void cadastrarProduto(ActionEvent event) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Tenta converter a quantidade para inteiro   RAIMUNDO BARROS
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeField.getText());
        } catch (NumberFormatException e) {
            // Quantidade inválida, exibe mensagem de erro RAIMUNDO BARROS
            mensagemLabel.setText("Quantidade inválida. Digite um número inteiro.");
            return;
        }

        // Verifica a data de validade RAIMUNDO BARROS
        Date validadeDate = null;
        if (!validade.getText().isEmpty()) {
            try {
                validadeDate = Date.valueOf(validade.getText());
            } catch (IllegalArgumentException e) {
                mensagemLabel.setText("Data de validade inválida. Use o formato AAAA-MM-DD.");
                return;
            }
        }

        // Verifica o número do lote RAIMUNDO BARROS
        String loteText = lote.getText().isEmpty() ? null : lote.getText();

        Produto produto = new Produto(
                nomeProduto.getText(),
                marca.getText(),
                codbarra.getText(),
                validadeDate,
                loteText,
                descricao.getText(),
                quantidade
        );

        boolean sucesso = produtoDAO.cadastrarProduto(produto);
        if (sucesso) {
            mensagemLabel.setText("Produto cadastrado com sucesso!");
        } else {
            mensagemLabel.setText("Erro ao cadastrar produto.");
        }
    }


    public TextField getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(TextField nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public TextField getMarca() {
        return marca;
    }

    public void setMarca(TextField marca) {
        this.marca = marca;
    }

    public TextField getCodbarra() {
        return codbarra;
    }

    public void setCodbarra(TextField codbarra) {
        this.codbarra = codbarra;
    }

    public TextField getValidade() {
        return validade;
    }

    public void setValidade(TextField validade) {
        this.validade = validade;
    }

    public TextField getLote() {
        return lote;
    }

    public void setLote(TextField lote) {
        this.lote = lote;
    }

    public TextField getDescricao() {
        return descricao;
    }

    public void setDescricao(TextField descricao) {
        this.descricao = descricao;
    }

    public Label getMensagemLabel() {
        return mensagemLabel;
    }

    public void setMensagemLabel(Label mensagemLabel) {
        this.mensagemLabel = mensagemLabel;
    }

    public Button getCadastraButton() {
        return cadastraButton;
    }

    public void setCadastraButton(Button cadastraButton) {
        this.cadastraButton = cadastraButton;
    }
}
