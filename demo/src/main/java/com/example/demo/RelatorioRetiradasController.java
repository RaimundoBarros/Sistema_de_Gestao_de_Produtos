package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RelatorioRetiradasController {

    @FXML
    private TextField filterCodBarra;
    @FXML
    private TextField filterNome;
    @FXML
    private TableView<Retirada> tabelaRetiradas;
    @FXML
    private TableColumn<Retirada, String> colCodBarra;
    @FXML
    private TableColumn<Retirada, String> colNome;
    @FXML
    private TableColumn<Retirada, Integer> colQuantidade;
    @FXML
    private TableColumn<Retirada, String> colData;
    @FXML
    private TableColumn<Retirada, String> colObservacao;

    @FXML
    public void initialize() {
        colCodBarra.setCellValueFactory(new PropertyValueFactory<>("codBarra"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));

        // Adicionando listeners para transformar texto em maiúsculas RAIMUNDO
        addUpperCaseListener(filterCodBarra);
        addUpperCaseListener(filterNome);
    }

    private void addUpperCaseListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue.toUpperCase());
        });
    }

    @FXML
    public void filtrarRetiradas() {
        String sql = "SELECT * FROM transacao WHERE 1=1";
        if (!filterCodBarra.getText().isEmpty()) {
            sql += " AND cod_barra LIKE ?";
        }
        if (!filterNome.getText().isEmpty()) {
            sql += " AND nome LIKE ?";
        }

        ObservableList<Retirada> data = FXCollections.observableArrayList();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (!filterCodBarra.getText().isEmpty()) {
                pstmt.setString(paramIndex++, "%" + filterCodBarra.getText() + "%");
            }
            if (!filterNome.getText().isEmpty()) {
                pstmt.setString(paramIndex++, "%" + filterNome.getText() + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Retirada retirada = new Retirada();
                retirada.setCodBarra(rs.getString("cod_barra"));
                retirada.setNome(rs.getString("nome"));
                retirada.setQuantidade(rs.getInt("quantidade"));
                retirada.setData(rs.getDate("data").toString());
                retirada.setObservacao(rs.getString("observacao"));
                data.add(retirada);
            }
        } catch (Exception e) {
            System.out.println("Erro ao filtrar retiradas: " + e.getMessage());
        }

        tabelaRetiradas.setItems(data);
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
}
