package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController {

    @FXML
    private VBox contentArea;

    @FXML
    private void handlePesquisar(ActionEvent event) throws IOException {
        loadView("telasolicitacao.fxml");
    }

    @FXML
    private void handleInserir(ActionEvent event) throws IOException {
        loadView("cadastroProduto.fxml");
    }

    @FXML
    private void handleRetirada(ActionEvent event) throws IOException {
        loadView("retirada.fxml");
    }

    @FXML
    private void handleRelatorio(ActionEvent event) throws IOException {
        loadView("RelatorioRetiradas.fxml");
    }

    private void loadView(String fxml) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxml));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(view);
    }
}
