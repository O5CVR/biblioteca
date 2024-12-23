package com.biblioteca.biblioteca.vistas;

import com.biblioteca.biblioteca.controlador.ControladorEjemplares;
import com.biblioteca.biblioteca.controlador.ControladorLibros;
import com.biblioteca.biblioteca.controlador.ControladorPrestamos;
import com.biblioteca.biblioteca.controlador.ControladorUsuarios;
import com.biblioteca.biblioteca.modelos.*;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Menu {


// Menu de inicio
    public static void showMenuIndex(){
        Scanner sc = new Scanner(System.in);
          int opcion;

            System.out.println("\n-----MENU-----");
            System.out.println("1. Sign in");
            System.out.println("2. Log in");
            System.out.println("3. Ver todos los libros");
            System.out.println("4. Terminar programa");
            try{

                opcion = sc.nextInt();

                switch (opcion){
                    case 1:
                        showSignIn();
                        break;
                    case 2:
                        showLogIn();
                        break;
                    case 3:
                        List <Libro> listaLibros = ControladorLibros.mostrarLibros();

                        if (!listaLibros.isEmpty()){

                            for(Libro libro: listaLibros){
                                System.out.println("Título: " + libro.getTitulo() + "\nAutor: " + libro.getAutor()+  "\n-----");
                            }

                        } else {
                            System.out.println("-----ERROR-----");
                            System.out.println("No hay libros. Lo siento!");
                        }

                        showMenuIndex();
                        break;

                    case 4:
                        System.out.println("Cerrando programa, hasta luego!");
                        sc.close();
                        break;

                    default:
                        System.out.println("-----ERROR-----");
                        System.out.println(" La opción elegida no se encuentra disponible");
                        showMenuIndex();
                        break;
                }
            } catch (InputMismatchException e){
                System.out.println("El valor introducido por tecldo no es correcto \n");
                sc.next();
                showMenuIndex();
            }

    }

    //Menu log in
    public static void showLogIn(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese su DNI: ");
        String dni = sc.next();

        System.out.println("Ingrese su password: ");
        String password = sc.next();

        Usuario user = new Usuario();
        user.setDni(dni);
        user.setPassword(password);


        ControladorUsuarios.c_log_in(user);

    }

    //Menu sign in
    public static void showSignIn(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese su DNI: ");
        String dni = sc.next();

        System.out.println("Ingrese su nombre: ");
        String nombre = sc.next();

        System.out.println("Ingrese su email: ");
        String email = sc.next();

        System.out.println("Ingrese una password: ");
        String password = sc.next();

        System.out.println("Es normal o administrador? (N/A): ");
        String tipoUsuario = sc.next();

        Usuario user = new Usuario();
        user.setDni(dni);
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(password);
        user.setTipo(tipoUsuario.toUpperCase());

        ControladorUsuarios.c_add_user(user);

    }

    //Menu admin
    public static void showMenuAdmin(Usuario user){
        Scanner sc = new Scanner(System.in);

        System.out.println("Buenos días " + user.getNombre() + ", está en su cuenta de administrador.");
        //Desarrollar menu admin
        System.out.println("1. Registrar libro");
        System.out.println("2. Registrar ejemplar");
        System.out.println("3. Ver todos los préstamos activos");
        System.out.println("4. Control stock");
        System.out.println("5. Eliminar préstramos OK +30 días");
        System.out.println("6. Log out");
        try {

            int opcion = sc.nextInt();

            switch (opcion) {

                case 1:

                    //Crear libro por input (QUEDA HACER VALIDACIONES)
                    Libro libro = new Libro();
                    sc.nextLine();
                    System.out.println("Introduzca el isbn del libro:");
                    libro.setIsbn(sc.nextLine());
                    System.out.println("Introduzca el titulo del libro:");
                    libro.setTitulo(sc.nextLine());
                    System.out.println("Introduzca el auto del libro:");
                    libro.setAutor(sc.nextLine());

                    ControladorLibros.registrarLibro(libro, user);
                    break;

                case 2:

                    Ejemplar ejemplar = new Ejemplar();
                    System.out.println("Introduzca el isbn del ejemplar:");

                    DAOLibros daoL = new DAOLibros();
                    sc.nextLine();
                    String isbn = sc.nextLine();
                    Libro libro2 = daoL.getByISBN(isbn);

                    ejemplar.setIsbn(libro2);
                    ejemplar.setEstado("Disponible");

                    ControladorEjemplares.registrarEjemplar(ejemplar, user);
                    break;

                case 3:
                    ControladorPrestamos.mostrarPrestamosActivos(user);
                    break;

                case 4:
                    showMenuControlStock(user);
                    break;

                case 5:
                    ControladorPrestamos.borrar_mas_30(user);
                    break;

                case 6:
                    System.out.println("Cerrando sesión de " + user.getNombre());
                    showMenuIndex();
                    break;

                default:
                    System.out.println("-----ERROR-----");
                    System.out.println(" La opción elegida no se encuentra disponible");
                    showMenuUser(user);
                    break;


            }
        } catch (InputMismatchException e){
            System.out.println("El valor introducido por tecldo no es correcto \n");
            sc.next();
            showMenuAdmin(user);
        }

    }

    public static void showMenuControlStock(Usuario user){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Ver ejemplares disponibles");
        System.out.println("2. Ver ejemplares dañados");
        System.out.println("3. Ver ejemplares prestados");
        System.out.println("4. Volver al menu");
    try {
        int opcion = sc.nextInt();

        switch (opcion) {

            case 1:
                ControladorLibros.mostrarEjemplaresDisponibles(user);
                break;

            case 2:
                ControladorLibros.mostrarEjemplaresDaniado(user);
                break;

            case 3:
                ControladorLibros.mostrarEjemplaresPrestados(user);
                break;

            case 4:
                showMenuAdmin(user);
                break;


            default:
                System.out.println("-----ERROR-----");
                System.out.println(" La opción elegida no se encuentra disponible");
                showMenuUser(user);
                break;

        }
    } catch (InputMismatchException e){
        System.out.println("El valor introducido por tecldo no es correcto \n");
        sc.next();
        showMenuControlStock(user);
    }
    }

    //Menu usuario normal
    public static void showMenuUser(Usuario user){
        Scanner sc = new Scanner(System.in);
        System.out.println("Buenos días " + user.getNombre() + ", está en su cuenta de cliente normal.\n");

        //Desarrollar menu normal
        System.out.println("1. Pedir libro");
        System.out.println("2. Devolver libro");
        System.out.println("3. Ver mis prestamos");
        System.out.println("4. Lista libros disponibles");
        System.out.println("5. Log out");
    try {
        int opcion = sc.nextInt();

        switch (opcion) {

            case 1:
                System.out.println("Introduce el nombre del libro que se quiere llevar: ");

                sc.nextLine();
                String titulo = sc.nextLine();
                ControladorPrestamos.reservarEjemplar(user, titulo);
                break;

            case 2:
                System.out.println("Introduce el nombre del libro que se quiere devolver: ");

                sc.nextLine();
                String titulo_ejemplar = sc.nextLine();
                ControladorPrestamos.devolver(user, titulo_ejemplar);
                break;

            case 3:
                ControladorPrestamos.mostrarPrestamos(user);
                break;

            case 4:
                ControladorEjemplares.mostrarEjemplares(user);
                break;

            case 5:
                System.out.println("Cerrando sesión de " + user.getNombre());
                showMenuIndex();
                break;

            default:
                System.out.println("-----ERROR-----");
                System.out.println(" La opción elegida no se encuentra disponible \n");
                showMenuUser(user);
                break;


        }
    }catch (InputMismatchException e){
        System.out.println("El valor introducido por tecldo no es correcto \n");
        sc.next();
        showMenuUser(user);
    }
    }
}
