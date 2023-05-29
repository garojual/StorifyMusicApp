package model;


import serializacion.Persistencia;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Representa el reproductor de musica completo, con sus usuarios, artistas y su respectivo admin
 */
public class Reproductor implements Serializable {

    private static volatile Reproductor instance;

    private HashMap<String,Usuario> tablaUsuarios = new HashMap<>();
    private HashMap<String,Administrador> tablaAdmin = new HashMap<>();
    private ArbolBinario<Artista> arbolArtista = new ArbolBinario<>();

    private ListaSimple<Artista> artistaInterfaz= new ListaSimple<>();
    private ListaSimple<Cancion> cancionesArtista = new ListaSimple<>();


    private static final long serialVersionUID = 1L;

    /**
     * Sigue el patron de diseño singleton para verificar que solo haya una instancia de la clase
     * @return
     */
    public static Reproductor getInstance(){
        if (instance == null) {
            synchronized (Reproductor.class) {
                if (instance == null) {
                    instance = Persistencia.deserializar();
                    if (instance == null) {
                        instance = new Reproductor();
                    }
                }
            }
        }
        return instance;
    }

    public Reproductor() {
        quemarDatosAdmin();
    }

    /**
     * Se asignan los respectivos datos al administrador
     */
    private void quemarDatosAdmin() {
        // TODO Auto-generated method stub
        Administrador admin1 = new Administrador("admin", "admin");
        tablaAdmin.put("admin", admin1);


    }

    /**
     * se crea el objeto a guardar
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Memento crearMemento() throws IOException, ClassNotFoundException {
        return new Memento(tablaUsuarios);
    }

    public void restaurarDesdeMemento(Memento memento) {
        tablaUsuarios = new HashMap<>(memento.getTablaUsuarios());
    }

    /**
     * se crea un nuevo usuario
     * @param nombre
     * @param clave
     * @param correo
     * @return
     */
    public boolean crearUser(String nombre, String clave, String correo) {

        Usuario newUser = new Usuario(nombre,clave,correo);

        if (!tablaUsuarios.containsKey(newUser.getUserName())){

            tablaUsuarios.put(newUser.getUserName(),newUser);
            return true;
        }else {
            System.out.println("Usuario ya existe");
            return false;
        }

    }

    /**
     * Se ingresa con los datos del usuario
     * @param userName
     * @param contrasenia
     * @return
     */
    public boolean ingresarUser(String userName, String contrasenia) {

        Usuario user = tablaUsuarios.get(userName);
        if (user != null && user.getContrasenia().equals(contrasenia)) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("False");
            return false;
        }
    }

    /**
     * se agrega una nueva cancion a la lista de las canciones del usuario
     * @param usuario
     * @param cancionSeleccionadaTodas
     */
    public void agregarCancionListaUser(Usuario usuario, Cancion cancionSeleccionadaTodas) {
        usuario.agregarCancionLista(cancionSeleccionadaTodas);
    }


    /**
     * se ingresa con los datos del admin
     * @param userName
     * @param contrasenia
     * @return
     */
    public boolean ingresarAdmin(String userName, String contrasenia) {
        // TODO Auto-generated method stub
        Administrador admin = tablaAdmin.get(userName);
        if (admin != null && admin.getContrasenia().equals(contrasenia)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Se crea un nuevo artista
     * @param nombre
     * @param nacionalidad
     * @param codigo
     * @param duo
     * @return
     */
    public boolean crearArtista(String nombre, String nacionalidad, String codigo, boolean duo) {

        Artista art = new Artista(nombre,codigo,nacionalidad,duo);

        if (arbolArtista.estaVacio()) {
            arbolArtista.insertar(art);

            return true;
        } else if (!(arbolArtista.existe(arbolArtista.getRaiz(), art)) && !existeNombreArtista(arbolArtista,nombre) ) {
            arbolArtista.insertar(art);
            System.out.println("Se inserto el artista");

            return true;
        } else {
            System.out.println("Artista ya existe");
            return false;
        }

    }

    /**
     * Se crea una nueva cancion
     * @param artista
     * @param codigo
     * @param nombreCancion
     * @param nombreAlbum
     * @param anio
     * @param genero
     * @param URl
     * @return
     */
    public boolean crearCancion(Artista artista,String codigo,String nombreCancion, String nombreAlbum,String anio, Genero genero, String URl) {

        Cancion cancion = new Cancion(codigo,nombreCancion,nombreAlbum,anio,genero,URl,artista);
        ListaDoble<Cancion> cancionesArtista = artista.getCancionesArtista();

        if (!cancionesArtista.existe(codigo)){
            System.out.println("Cancion agregada a la lista");
            cancionesArtista.agregarfinal(cancion);
            return true;

        }else {
            System.out.println("Cancion ya existente");
            return false;
        }

    }

    /**
     * Se elimina una cancion de la lista de canciones del usuario
     * @param usuario
     * @param cancionSeleccionadaMias
     */
    public void eliminarCancionListaUser(Usuario usuario, Cancion cancionSeleccionadaMias) {
        usuario.eliminarCancionLista(cancionSeleccionadaMias);
    }


    public  boolean existeNombreArtista(ArbolBinario<Artista> arbol,String nombre) {
        return existeNombreArtista(arbol.getRaiz(),nombre);
    }

    /**
     * Se verifica la existencia del artista
     * @param raiz
     * @param nombre
     * @return
     */
    private boolean existeNombreArtista(NodoArbol<Artista> raiz, String nombre){
        if (raiz == null) {
            return false;
        }

        Artista artista = raiz.getElemento();
        if (artista.getNombre().equals(nombre)) {
            return true;
        }

        boolean izquierda = existeNombreArtista(raiz.getIzquierdo(), nombre);
        boolean derecha = existeNombreArtista(raiz.getDerecho(), nombre);

        return izquierda || derecha;
    }


    public HashMap<String, Usuario> getTablaUsuarios() {
        return tablaUsuarios;
    }

    public void setTablaUsuarios(HashMap<String, Usuario> tablaUsuarios) {
        this.tablaUsuarios = tablaUsuarios;
    }

    public HashMap<String, Administrador> getTablaAdmin() {
        return tablaAdmin;
    }

    public void setTablaAdmin(HashMap<String, Administrador> tablaAdmin) {
        this.tablaAdmin = tablaAdmin;
    }

    public ArbolBinario<Artista> getArbolArtista() {
        return arbolArtista;
    }

    public void setArbolArtista(ArbolBinario<Artista> arbolArtista) {
        this.arbolArtista = arbolArtista;
    }
}
