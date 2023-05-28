package com.example.storifymusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Artista;
import model.Genero;

import java.io.IOException;

public class CrearCancionVistaController {

    private HelloApplication aplicacion;
    private Artista artista;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtAlbum;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtURL;

    @FXML
    private RadioButton gRock;

    @FXML
    private RadioButton gPop;

    @FXML
    private RadioButton gReggaeton;

    @FXML
    private RadioButton gPunk;

    @FXML
    private RadioButton gElectronica;


    @FXML
    public void crearCancion (ActionEvent event) throws IOException {
        String nombre = txtNombre.getText();
        String album = txtAlbum.getText();
        String duracion = txtDuracion.getText();
        String anio = txtAnio.getText();
        String url = txtURL.getText();
        Genero genero=Genero.POP;

        if(gRock.isSelected()){
            genero=Genero.ROCK;
        }else if (gPop.isSelected()){
            genero=Genero.POP;
        }else if(gPunk.isSelected()){
            genero=Genero.PUNK;
        }else if(gElectronica.isSelected()){
            genero=Genero.ELECTRONICA;
        }else if(gReggaeton.isSelected()){
            genero=Genero.REGGAETON;
        }

        aplicacion.crearCancion(nombre,album,duracion,anio,url,genero,artista);

    }


    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        System.out.println(artista.getNombre());
    }

    public void setArtista(Artista artista){
        this.artista = artista;
    }

    public Artista getArtista() {
        return artista;
    }
}
