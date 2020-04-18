/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.ElementoDTO;
import java.util.List;

/**
 *
 * @author davim
 */
public class PrintResultadoTela {
    
    public static void printResultadoTipoElemento(List<ElementoDTO> elementos){
         for(ElementoDTO ElementoEquacao: elementos){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);            
        } 
    }
    
    public static void printResultadoElemento(List<ElementoDTO> elementos){
         for(ElementoDTO ElementoEquacao: elementos){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.elemento);            
        } 
    }
}
