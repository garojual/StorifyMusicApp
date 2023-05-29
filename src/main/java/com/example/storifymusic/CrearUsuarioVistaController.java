package com.example.storifymusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controlador que se encarga de la vista de crear usuario
 * @author Juliana
 * @author Juan
 */
public class CrearUsuarioVistaController {

    HelloApplication aplicacion;

    @FXML
    private PasswordField passContra;

    @FXML
    private TextField txtNomCrear;

    @FXML
    private TextField txtEmailCrear;

    /**
     * Establece una instancia de la aplicacion
     * @param aplicacion
     */
    public void setAplicacion(HelloApplication aplicacion) {

        this.aplicacion = aplicacion;

    }

    /**
     * Crea un usuario
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void CrearUsuario(ActionEvent actionEvent) throws IOException {
        String nombre = txtNomCrear.getText();
        String clave = passContra.getText();
        String correo = txtEmailCrear.getText();

        if (!( nombre.equals("") && correo.equals("") && clave.equals("")  )){
            aplicacion.crearUsuario(nombre,clave,correo);
        }else {
            System.out.println("Hay campos sin completar");
        }

    }

    /**
     * Regresa a la pantalla inicial
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void Atras(ActionEvent actionEvent) throws IOException {
        aplicacion.devolverLogin();
    }
}
