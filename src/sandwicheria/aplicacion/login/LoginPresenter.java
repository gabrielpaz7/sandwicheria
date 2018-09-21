/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.aplicacion.login;

import sandwicheria.aplicacion.usuario.Cajero;
import javax.swing.JOptionPane;
import sandwicheria.aplicacion.AplicacionView;
import sandwicheria.persistencia.Repositorio;
import sandwicheria.persistencia.Session;

/**
 *
 * @author Gabriel
 */
public class LoginPresenter {
    private LoginView view;
    
    public LoginPresenter(LoginView view){
        this.view = view;
    }
    
    public void login(String usuario, String password){
        
        for(Cajero c : Repositorio.getCajeros()){   
            if(c.getUsuario().equals(usuario) && c.getPassword().equals(password)){
                System.out.printf("%s ha iniciado sesion %n", c.getNombreApellido());
                Session.setUsuario(c);
                openPrincipal();
                view.dispose();
            }
        }
        
        if(!Session.isIsLogged()){
            JOptionPane.showMessageDialog(view, "Usuario/Contraseña incorrecta");
        }
    }
    
    public void openPrincipal(){
        AplicacionView app = new AplicacionView();
        app.setVisible(true);
    }
    
}
