/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.usuario;

import sandwicheria.turno.Turno;

/**
 *
 * @author Gabriel
 */
public class Cajero extends Usuario{
    
    Turno turno;

    public Cajero(int legajo, String nombreApellido, String usuario, String password, Turno turno) {
        super(legajo, nombreApellido, usuario, password);
        this.turno = turno;
    }

    
    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    
    @Override
    public String toString(){
        return String.format("Nombre: %s%nTurno: %s%n", getNombreApellido(), getTurno());
    }
    
}
