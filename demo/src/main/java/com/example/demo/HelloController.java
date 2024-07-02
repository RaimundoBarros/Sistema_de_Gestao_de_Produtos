package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label erroCadastro;
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private PasswordField senha;
    @FXML
    private PasswordField confirmarSenha;
    @FXML
    private CheckBox termos;
    @FXML
    private Button cadastrar;
    @FXML
    private Hyperlink voltalogin;


    @FXML
    public void botaoCadastrar() {
        erroCadastro.setText("Sucesso ao cadastrar!");
        String name= nome.getText();
        String mail= email.getText();
        String pass= senha.getText();
        String passConfirm = confirmarSenha.getText();

        if (conferiSenha(pass,passConfirm) == true){
            salvarNoBanco(name, mail, pass);
            erroCadastro.setText("Sucesso ao cadastrar");
        }
        else{
            erroCadastro.setText("A senhas  não conferem");
        }

    }
    @FXML
    private void teladelogin() throws IOException {
        Stage stage = (Stage) email.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "tela de login.fxml");
    }
    private boolean conferiSenha(String pass, String passConfirm) {
        if (pass.equals(passConfirm)){
            return true;
        }else {
            return false;
        }
    }
    private void salvarNoBanco(String login,String email, String password) {
        String url = "jdbc:mysql://localhost:3306/app";
        String user = "root";
        String pwd = "";

        String query = "INSERT INTO cadastro (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}