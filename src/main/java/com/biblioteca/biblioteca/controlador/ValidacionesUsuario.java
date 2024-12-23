package com.biblioteca.biblioteca.controlador;

public class ValidacionesUsuario {

    public static boolean nombre_valido(String nombre){
        return nombre.matches("[a-zA-Z]+");
    }

    public static boolean email_valido(String email){
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    public static boolean dni_valido(String dni){
        return dni.matches("^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$");
    }

    public static boolean password_valido(String password){
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,20}$");
    }

    public static boolean tipo_valido(String tipo){
        return tipo.matches("^[AN]$");
    }
}
