/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria;

import sandwicheria.aplicacion.Splash;

/**
 *
 * @author Gabriel
 */
public class Sandwicheria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread (new Splash()).start();
    }
    
}
