package com.biblioteca.biblioteca.controlador;

import com.biblioteca.biblioteca.modelos.DAO_Generico;
import com.biblioteca.biblioteca.modelos.Ejemplar;
import com.biblioteca.biblioteca.modelos.Libro;
import com.biblioteca.biblioteca.modelos.Usuario;
import com.biblioteca.biblioteca.vistas.Menu;

import java.util.List;

public class ControladorLibros {

    private static DAO_Generico daoL = new DAO_Generico<>(Libro.class, String.class);;

    public static List<Libro> mostrarLibros(){

        return daoL.getAll();
    }

    public static void registrarLibro(Libro libro, Usuario user){
        daoL.add(libro);

        System.out.println("Se ha registrado el libro con éxito, gracias " + user.getNombre() + "!");

        Menu.showMenuAdmin(user);
    }

    public static void mostrarEjemplaresDisponibles(Usuario user){

        for (Libro libro: mostrarLibros()){
            for (Ejemplar ejemplar: libro.getEjemplars()){
                if(ejemplar.getEstado().equalsIgnoreCase("Disponible")){
                    System.out.println("ISBN: " + ejemplar.getIsbn().getIsbn() + " - Titulo: " + ejemplar.getIsbn().getTitulo() + " - Autor: " + ejemplar.getIsbn().getAutor() +  " - ID: " + ejemplar.getId()+  "\n");
                }
            }
        }

        Menu.showMenuAdmin(user);
    }

    public static void mostrarEjemplaresPrestados(Usuario user){

        for (Libro libro: mostrarLibros()){
            for (Ejemplar ejemplar: libro.getEjemplars()){
                if(ejemplar.getEstado().equalsIgnoreCase("Prestado")){
                    System.out.println("ISBN: " + ejemplar.getIsbn().getIsbn() + " - Titulo: " + ejemplar.getIsbn().getTitulo() + " - Autor: " + ejemplar.getIsbn().getAutor() +  " - ID: " + ejemplar.getId()+  "\n");
                }
            }
        }

        Menu.showMenuAdmin(user);
    }

    public static void mostrarEjemplaresDaniado(Usuario user){

        for (Libro libro: mostrarLibros()){
            for (Ejemplar ejemplar: libro.getEjemplars()){
                if(ejemplar.getEstado().equalsIgnoreCase("Dañado")){
                    System.out.println("ISBN: " + ejemplar.getIsbn().getIsbn() + " - Titulo: " + ejemplar.getIsbn().getTitulo() + " - Autor: " + ejemplar.getIsbn().getAutor() +  " - ID: " + ejemplar.getId()+  "\n");
                }
            }
        }

        Menu.showMenuAdmin(user);
    }





}
