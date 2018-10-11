/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.persistencia;

import sandwicheria.usuario.Usuario;

/**
 *
 * @author Gabriel
 */
public class Session {
    private static Usuario usuario;
    private static boolean isLogged;
    
    public static void iniciar(){
        Session.isLogged = false;
        usuario = null;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Session.usuario = usuario;
        Session.isLogged = true;
    }

    public static boolean isIsLogged() {
        return isLogged;
    }

    public static void setIsLogged(boolean isLogged) {
        Session.isLogged = isLogged;
    }
}
