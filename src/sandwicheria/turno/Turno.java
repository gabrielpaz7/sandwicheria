/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.turno;

/**
 *
 * @author Gabriel
 */
public enum Turno {
    MAÑANA("Mañana"), NOCHE("Noche");
    
    String descripcion;
    
    private Turno(String d){
       descripcion = d;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
}
