package com.example.storifymusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginVistaController {
    HelloApplication aplicacion;

    @FXML
    private PasswordField txtPassw;

    @FXML
    private TextField txtUsuario;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label labelStorify;

    @FXML
    private Label labelMusic;

    public void setAplicacion(HelloApplication helloApplication){

        mainAnchorPane.setStyle("-fx-background-color: #f6f0ef"); //
        this.aplicacion= helloApplication;
    }

    @FXML
    void ingresar(ActionEvent event) throws IOException {

        String usuario = txtUsuario.getText();
        System.out.println(usuario);
        String contrasena = txtPassw.getText();
        System.out.println(contrasena);
        if (usuario.equals("admin") && contrasena.equals("admin")) {
           aplicacion.ingresarAdmin(usuario, contrasena);
        } else {
            System.out.println("Usuario");
            aplicacion.ingresarUsuario(usuario, contrasena);
        }

    }

    @FXML
    public void crearUsuario(ActionEvent actionEvent) throws IOException {
        aplicacion.showCrearUsuario();

    }
}
