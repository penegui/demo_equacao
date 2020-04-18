/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author davim
 */
public enum TipoElemento {
    
    variavel("v"),
    numero("int"),
    igual("="),
    mais("+"),
    menos("-"),
    vezis("*"),
    dividir("/"),
    sinal("ª"),
    inverteSinal("&");
 
    public String elemento;
    
    private static final Map<String, TipoElemento> tipoPorElemento = new HashMap<>();
    
    static {
        for (TipoElemento elemento: TipoElemento.values()){
            tipoPorElemento.put(elemento.getElemento(), elemento);
        }
    }
    
    TipoElemento(String elemento) {
        this.elemento = elemento;
    }
 
    public String getElemento() {
        return elemento;
    }  
    
    public static TipoElemento selecionaTipoPorElemento(String elemento){
        return tipoPorElemento.get(elemento);
    }
}
