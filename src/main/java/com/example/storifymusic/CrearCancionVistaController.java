package com.example.storifymusic;

import model.Artista;

public class CrearCancionVistaController {

    private HelloApplication aplicacion;
    private Artista artista;

    public void setAplicacion(HelloApplication aplicacion) {
        this.aplicacion = aplicacion;
        System.out.println(artista.getNombre());
    }

    public void setArtista(Artista artista){
        this.artista = artista;
    }
}
