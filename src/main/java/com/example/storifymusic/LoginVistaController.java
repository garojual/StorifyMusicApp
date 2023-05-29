package com.example.storifymusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controlador que se encarga de la vista de inicio de sesion
 * @author Juliana
 * @author Juan
 */
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

    /**
     * Establece una instancia de la aplicacion
     * @param helloApplication
     */
    public void setAplicacion(HelloApplication helloApplication){

        mainAnchorPane.setStyle("-fx-background-color: #f6f0ef"); //
        this.aplicacion= helloApplication;
    }

    /**
     * Añade la funcionalidad al boton de ingresar si las credenciales son correctas
     * @param event
     * @throws IOException
     */
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

    /**
     * Registra un nuevo usuario
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void crearUsuario(ActionEvent actionEvent) throws IOException {
        aplicacion.showCrearUsuario();

    }
}
