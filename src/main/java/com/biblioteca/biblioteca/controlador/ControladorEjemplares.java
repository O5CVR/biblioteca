package com.biblioteca.biblioteca.controlador;

import com.biblioteca.biblioteca.modelos.DAO_Generico;
import com.biblioteca.biblioteca.modelos.Ejemplar;
import com.biblioteca.biblioteca.modelos.Libro;
import com.biblioteca.biblioteca.modelos.Usuario;
import com.biblioteca.biblioteca.vistas.Menu;

import java.util.List;

public class ControladorEjemplares {

    private static DAO_Generico daoE = new DAO_Generico<>(Ejemplar.class, Integer.class);
    public static void mostrarEjemplares(Usuario user){


        List <Ejemplar> listaEjemplares = daoE.getAll();

        if (!listaEjemplares.isEmpty()){


            for(Ejemplar ejemplar: listaEjemplares){
                if(ejemplar.getEstado().equalsIgnoreCase("Disponible")){
                    System.out.println("Id: " + ejemplar.getId() + "\nTítulo: "+ ejemplar.getIsbn().getTitulo() + "\nAutor: "+ ejemplar.getIsbn().getAutor() + "\nISBN: " + ejemplar.getIsbn().getIsbn() +  "\n-----");

                }

            }

        } else {
            System.out.println("-----ERROR-----");
            System.out.println("No hay libros. Lo siento!");
        }

        Menu.showMenuUser(user);

    }

    public static void registrarEjemplar(Ejemplar ejemplar, Usuario user){
        daoE.add(ejemplar);

        System.out.println("Se ha registrado el ejemplar con éxito, gracias " + user.getNombre() + "!");

        Menu.showMenuAdmin(user);
    }

}
