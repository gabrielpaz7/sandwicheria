/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandwicheria.cliente;

/**
 *
 * @author Gabriel
 */
public class Cliente {
    String nombreApellido;
    long cuit;
    CondicionTributaria condicionTributaria;

    public Cliente() {
    }

    public Cliente(String nombreApellido, long cuit, CondicionTributaria condicionTributaria) {
        this.nombreApellido = nombreApellido;
        this.cuit = cuit;
        this.condicionTributaria = condicionTributaria;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long cuit) {
        this.cuit = cuit;
    }

    public CondicionTributaria getCondicionTributaria() {
        return condicionTributaria;
    }

    public void setCondicionTributaria(CondicionTributaria condicionTributaria) {
        this.condicionTributaria = condicionTributaria;
    }
    
    
    @Override
    public String toString(){
        return String.format("\n- CUIT: %s \n- Razon Social: %s \n- Tipo Responsabilidad: %s", cuit, nombreApellido, condicionTributaria);
    }
    

        
}
