/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.usuario;

/**
 *
 * @author Gabriel
 */
public class Usuario {
    int legajo;
    String nombreApellido;
    String usuario;
    String password;

    public Usuario(int legajo, String nombreApellido, String usuario, String password) {
        this.legajo = legajo;
        this.nombreApellido = nombreApellido;
        this.usuario = usuario;
        this.password = password;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    
    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
