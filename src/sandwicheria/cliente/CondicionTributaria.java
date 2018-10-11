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
public enum CondicionTributaria {
        //TIPOS DE RESPONSABLES
    
        RESPONSABLE_INSCRIPTO("IVA RESPONSABLE INSCRIPTO", 1),
        RESPONSABLE_NO_INSCRIPTO("IVA RESPONSABLE NO INSCRIPTO", 2),
        IVA_NO_RESPONSABLE("IVA NO RESPONSABLE", 3),
        IVA_SUJETO_EXENTO("IVA SUJETO EXENTO", 4),
        CONSUMIDOR_FINAL("CONSUMIDOR FINAL", 5),
        RESPONSABLE_MONOTRIBUTO("RESPONSABLE MONOTRIBUTO", 6),
        SUJETO_NO_CATEGORIZADO("SUJETO NO CATEGORIZADO", 7),
        PROVEEDOR_DEL_EXTERIOR("PROVEEDOR DEL EXTERIOR", 8),
        CLIENTE_DEL_EXTERIOR("CLIENTE DEL EXTERIOR", 9),
        IVA_LIBERADO("IVA LIBERADO - LEY Nº 19.640", 10),
        AGENTE_DE_PERCEPCION("IVA RESPONSABLE INSCRIPTO - AGENTE DE PERCEPCION", 11),
        PEQUEÑO_CONTRIBUYENTE_EVENTUAL("PEQUEÑO CONTRIBUYENTE EVENTUAL", 12),
        MONOTRIBUTISTA_SOCIAL("MONOTRIBUTISTA SOCIAL", 13),
        PEQUEÑO_CONSTRIBUYENTE_EVENTUAL_SOCIAL("PEQUEÑO CONTRIBUYENTE EVENTUAL SOCIAL", 14);
                
        private String tipo;
        private int codigo;
               
        private CondicionTributaria(String t, int c){
           this.tipo = t;
           this.codigo = c;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }
        
        @Override
        public String toString(){
            return String.format("%d - %s", codigo, tipo);
        }
}