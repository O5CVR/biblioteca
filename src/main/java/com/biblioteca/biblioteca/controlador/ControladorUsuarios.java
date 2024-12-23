package com.biblioteca.biblioteca.controlador;

import com.biblioteca.biblioteca.modelos.DAO_LogInUser;
import com.biblioteca.biblioteca.modelos.Usuario;
import com.biblioteca.biblioteca.vistas.Menu;
import org.hibernate.exception.ConstraintViolationException;

public class ControladorUsuarios {

    private  static DAO_LogInUser du = new DAO_LogInUser();

    // add user
    public static void c_add_user(Usuario user){


        //validar datos
        if(!ValidacionesUsuario.nombre_valido(user.getNombre())){
            System.out.println("-----ERROR-----");
            System.out.println("El nombre introducido no es válido");
            user.setNombre("");
        }
        if (!ValidacionesUsuario.email_valido(user.getEmail())){
            System.out.println("-----ERROR-----");
            System.out.println("El email introducido no es válido");
            user.setEmail("");
        }
        if (!ValidacionesUsuario.dni_valido(user.getDni())){
            System.out.println("-----ERROR-----");
            System.out.println("El dni introducido no es válido");
            user.setDni("");
        }
        if (!ValidacionesUsuario.password_valido(user.getPassword())){
            System.out.println("-----ERROR-----");
            System.out.println("La contraseña introducida no es válida");
            user.setPassword("");
        }
        if (!ValidacionesUsuario.tipo_valido(user.getTipo())){
            System.out.println("-----ERROR-----");
            System.out.println("El tipo de usuario no es válido");
            user.setTipo("");
        } else {
            if(user.getTipo().equals("N")){
                user.setTipo("normal");
            } else {
                user.setTipo("administrador");
            }
        }

        // Comprobación datos del usuario y si procede añadimos
        if(user.getNombre().equals("") || user.getEmail().equals("") || user.getDni().equals("") ||
                user.getPassword().equals("") || user.getTipo().equals("")){
            Menu.showSignIn();

        } else {
            try {

                du.add(user);
                System.out.println("Usuario creado correctamente");
                Menu.showMenuAdmin(user);

            } catch (ConstraintViolationException e){

                DAO_LogInUser du = new DAO_LogInUser();
                Usuario user2 = du.getByDNI2(user);

                if(user.getDni().equals(user2.getDni())){
                    System.out.println("-----ERROR-----");
                    System.out.println("El DNI ya existe en la base de datos");
                } else {
                    System.out.println("-----ERROR-----");
                    System.out.println("El EMAIL ya existe en la base de datos");
                }
                Menu.showMenuIndex();
            }

        }


    }

    // log in
    public static void c_log_in(Usuario user){

         Usuario user_BD = du.getByDNI2(user);

         if(user_BD == null){
             System.out.println("-----ERROR-----");
             System.out.println("El usuario no está en la base de datos");
             Menu.showMenuIndex();
         } else {
             if(user_BD.getPassword().equals(user.getPassword())){
                 if(user_BD.getTipo().equals("normal")){
                     Menu.showMenuUser(user_BD);
                 } else {
                     Menu.showMenuAdmin(user_BD);
                 }

             } else {
                 System.out.println("-----ERROR-----");
                 System.out.println("La contraseña no coincide con el usuario");
                 Menu.showMenuIndex();
             }

         }
    }
}
