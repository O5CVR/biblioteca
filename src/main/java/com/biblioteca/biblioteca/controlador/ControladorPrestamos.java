package com.biblioteca.biblioteca.controlador;


import com.biblioteca.biblioteca.modelos.*;
import com.biblioteca.biblioteca.vistas.Menu;

import java.time.LocalDate;
import java.util.List;

public class ControladorPrestamos {

    private static  DAO_Generico daoP = new DAO_Generico<>(Prestamo.class, Integer.class);

    public static void reservarEjemplar(Usuario user, String titulo){

        //Comprobar si esta penalizado
        if(LocalDate.now().isAfter(user.getPenalizacionHasta())){

            DAOLibros daoL = new DAOLibros();

            Libro libro = daoL.getByTitulo(titulo);
            // Buscar el primer ejemplar con el estado disponible y cambiarle el estado a Prestado

            for (Ejemplar ejemplar: libro.getEjemplars()){
                if(ejemplar.getEstado().equalsIgnoreCase("Disponible")){
                    //Cambiar a Prestando
                    ejemplar.setEstado("Prestado");
                    DAO_Generico daoG = new DAO_Generico<>(Ejemplar.class, Integer.class);
                    daoG.update(ejemplar);

                    //Creamos un prestamo con el id del ejemplar, el id del usuario, las fechas y entrago en K


                    Prestamo prestamo = new Prestamo();
                    prestamo.setUsuario(user);
                    prestamo.setEjemplar(ejemplar);
                    prestamo.setFechaInicio(LocalDate.now());
                    prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
                    prestamo.setEntregado("KO");

                    daoP.add(prestamo);
                    System.out.println("Prestamo realizado con éxito");
                    Menu.showMenuUser(user);
                }

            }

            System.out.println("No hay ningún ejemplar disponible");
            Menu.showMenuUser(user);

        } else{

            System.out.println("Usted está en periodo de penalización hasta: " + user.getPenalizacionHasta());
            Menu.showMenuUser(user);

        }
    }


    public static void mostrarPrestamos(Usuario user){
        if (!user.getPrestamos().isEmpty()){


            for(Prestamo prestamo: user.getPrestamos()){
                System.out.println("ID: " + prestamo.getId() + "\nTitulo: " +
                        prestamo.getEjemplar().getIsbn().getTitulo() + "\nFecha Inicio: " + prestamo.getFechaInicio() + "\nFecha Devolución: " + prestamo.getFechaDevolucion() + "\nEntregado: " + prestamo.getEntregado()+  "\n-----");
            }

        } else {
            System.out.println("-----ERROR-----");
            System.out.println("No hay prestamos!");
        }

        Menu.showMenuUser(user);
    }

    public static void mostrarPrestamosActivos(Usuario user){
        List<Prestamo> prestamosActivos = daoP.getAll();

        if (!prestamosActivos.isEmpty()){


            for(Prestamo prestamo: prestamosActivos){
                if(prestamo.getEntregado().equalsIgnoreCase("KO")){
                    System.out.println("ID: " + prestamo.getId() + "\nTitulo: " +
                            prestamo.getEjemplar().getIsbn().getTitulo() + "\nFecha Inicio: " + prestamo.getFechaInicio() + "\nFecha Devolución: " + prestamo.getFechaDevolucion() + "\nEntregado: " + prestamo.getEntregado()+  "\n-----");
                }
            }

        } else {
            System.out.println("-----ERROR-----");
            System.out.println("No hay prestamos!");
        }

        Menu.showMenuAdmin(user);
    }

    public static void devolver(Usuario user, String titulo_ejemplar){
        //Comprobar prestamos del user
        if (!user.getPrestamos().isEmpty()){

            //Comprobar titulo en prestamos
            for(Prestamo prestamo: user.getPrestamos()){
               if(prestamo.getEjemplar().getIsbn().getTitulo().equalsIgnoreCase(titulo_ejemplar)){

                   //Cambiar prestamos a entregado OK
                   prestamo.setEntregado("OK");
//                   DAO_Generico daoP = new DAO_Generico<>(Prestamo.class, Integer.class);
                   daoP.update(prestamo);

                   //Cambiar ejemplar a Disponible
                   prestamo.getEjemplar().setEstado("Disponible");
                   DAO_Generico daoG = new DAO_Generico<>(Ejemplar.class, Integer.class);
                   daoG.update(prestamo.getEjemplar());


                   //Fuera de plazo, sancion (Usuario - penalizacion hasta...)
                   //Si la fecha de hoy es despues de la fecha de devolucion - true
                   if(LocalDate.now().isAfter(prestamo.getFechaDevolucion())){
                       //Penalizacion Usuario
                       user.setPenalizacionHasta(LocalDate.now().plusDays(15));
                       DAO_Generico daoU = new DAO_Generico<>(Usuario.class,Integer.class);
                       daoU.update(user);
                       System.out.println("Se le ha penalizado durante 15 días sin poder llevarse un libro");
                       Menu.showMenuUser(user);

                   }else {
                       System.out.println("Libro devuelto con éxito");
                       Menu.showMenuUser(user);
                   }
               }
            }


        } else {
            System.out.println("-----ERROR-----");
            System.out.println("No hay prestamos!");
            Menu.showMenuUser(user);
        }

    }

    public static void borrar_mas_30(Usuario user){

        List<Prestamo> prestamos = daoP.getAll();
        int eliminados = 0;

        for (Prestamo prestamo: prestamos){
            if(prestamo.getEntregado().equalsIgnoreCase("OK") && LocalDate.now().isAfter(prestamo.getFechaDevolucion().plusDays(30))){

                daoP.deleteUsuario(prestamo);
                eliminados++;
            }
        }

        System.out.println("Se han eliminado " + eliminados + " préstamos con más de 30 dias ya devueltos.");
        Menu.showMenuAdmin(user);

    }




}
