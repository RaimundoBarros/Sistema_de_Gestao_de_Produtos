package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class TelaSolicitacaoController {

    @FXML
    private TextField nomeProdutoField;

    @FXML
    private TextField codBarraField;

    @FXML
    private TableView<Produto> produtoTable;

    @FXML
    private TableColumn<Produto, String> nomeColumn;

    @FXML
    private TableColumn<Produto, String> marcaColumn;

    @FXML
    private TableColumn<Produto, String> codBarraColumn;

    @FXML
    private TableColumn<Produto, Date> validadeColumn;

    @FXML
    private TableColumn<Produto, String> numLoteColumn;

    @FXML
    private TableColumn<Produto, String> descricaoColumn;

    @FXML
    private TableColumn<Produto, String> quantidadeColumn;

    private ProdutoDAO produtoDAO;

    public TelaSolicitacaoController() {
        produtoDAO = new ProdutoDAO();
    }

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        codBarraColumn.setCellValueFactory(new PropertyValueFactory<>("codBarra"));
        validadeColumn.setCellValueFactory(new PropertyValueFactory<>("validade"));
        numLoteColumn.setCellValueFactory(new PropertyValueFactory<>("numLote"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Adiciona EventHandler para Enter nos campos de texto
        nomeProdutoField.setOnKeyPressed(this::handleKeyPressed);
        codBarraField.setOnKeyPressed(this::handleKeyPressed);
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                handlePesquisar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handlePesquisar() throws IOException {
        String nome = nomeProdutoField.getText().trim();
        String codBarra = codBarraField.getText().trim();

        List<Produto> produtos;

        if (nome.isEmpty() && codBarra.isEmpty()) {
            produtos = produtoDAO.buscarTodos();
        } else if (!nome.isEmpty()) {
            produtos = produtoDAO.buscarPorNomeOuCodigoBarras(nome, "nome");
        } else {
            produtos = produtoDAO.buscarPorNomeOuCodigoBarras(codBarra, "cod_barra");
        }

        ObservableList<Produto> produtosObservableList = FXCollections.observableArrayList(produtos);
        produtoTable.setItems(produtosObservableList);
    }

    @FXML
    private void handleRetirada() {
        // Implementar lógica para registrar retirada de produtos
    }

    @FXML
    private void handleEmprestimo() {
        // Implementar lógica para registrar empréstimo de produtos
    }

    @FXML
    private void handleRetorno() {
        // Implementar lógica para registrar retorno de produtos emprestados
    }
}
