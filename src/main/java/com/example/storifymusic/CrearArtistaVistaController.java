package com.example.storifymusic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.*;

import java.io.IOException;
import java.util.EnumSet;
import java.util.function.Consumer;

/**
 * Controlador que se encarga de la vista de crear ucanciones
 * @author Juliana
 * @author Juan
 */
public class CrearArtistaVistaController {

    private HelloApplication aplicacion;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextField txtNacionalidad;

    @FXML
    private TextField txtCodigo;

    @FXML
    private RadioButton opSolista;

    @FXML
    private RadioButton opGrupo;

    @FXML
    private ToggleGroup tipoArtista;

    //private Reproductor reproductor = Reproductor.getReproductor();


    /**
     * Establece una instancia de la aplicacion
     * @param aplicacion
     */
    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        aplicacion.getArtistas().inorden();

    }

    /**
     * Crea un nuevo artista en la interfaz
     * @param event
     * @throws IOException
     */
    @FXML
    public void crearArtista(ActionEvent event) throws IOException {
        String nombre = txtArtista.getText();
        String nacionalidad = txtNacionalidad.getText();
        String codigo = txtCodigo.getText();
        Boolean isGrupo;

        if (opSolista.isSelected()){
            isGrupo= false;
        }else {
            isGrupo = true;
        }


        if ( (tipoArtista.getSelectedToggle() != null) && !nombre.equals("") && !nacionalidad.equals("") && !codigo.equals("") ){
            aplicacion.crearArtista(nombre, nacionalidad,codigo,isGrupo);
            aplicacion.getArtistas().inorden();
        }else {
            System.out.println("Llene todos los campos");
        }



    }







}
