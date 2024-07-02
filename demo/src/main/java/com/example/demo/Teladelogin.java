package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Teladelogin {
    @FXML
    private TextField email;
    @FXML
    private PasswordField senha;
    @FXML
    private Button entrar;
    @FXML
    private Hyperlink cadastro;

    @FXML
    private void botãologar() throws IOException {
        String mail = email.getText();
        String pass = senha.getText();

        if (validarLogin(mail, pass) == true){
            //redirecionar para a tela certe
            Stage stage = (Stage) email.getScene().getWindow();
            SceneSwitcher.switchScene(stage, "menu.fxml");
        }else {
            System.out.println("Credencias inválidas");
        }
    }
    @FXML
    private void teladecadastro() throws IOException {
        Stage stage = (Stage) email.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "hello-view.fxml");
    }

    private boolean validarLogin (String mail, String pass){
        String url = "jdbc:mysql://localhost:3306/app";
        String user = "root";
        String pwd = "";

        String query = "SELECT * FROM cadastro WHERE email = ? AND senha= ?";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}