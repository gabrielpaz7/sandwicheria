/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.persistencia;

import java.util.ArrayList;
import sandwicheria.aplicacion.usuario.Cajero;
import sandwicheria.aplicacion.usuario.Turno;

/**
 *
 * @author Gabriel
 */
public class Repositorio {
    private static ArrayList<Cajero> cajeros;
    
    public static void iniciar(){
        iniciarCajeros();
    }
    
    private static void iniciarCajeros(){
        cajeros = new ArrayList<Cajero>();
        
        Cajero cajero = new Cajero("Gabriel Paz", "admin", "admin", Turno.MAÑANA);
        cajeros.add(cajero);
        
        cajero = new Cajero("Usuario X", "user", "user", Turno.NOCHE);
        cajeros.add(cajero);
    }

    public static ArrayList<Cajero> getCajeros() {
        return cajeros;
    }
    
}
