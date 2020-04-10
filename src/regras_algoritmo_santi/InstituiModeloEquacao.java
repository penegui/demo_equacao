/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regras_algoritmo_santi;

import dto.ElementoDTO;
import enums.TipoElemento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author davim
 */
public class InstituiModeloEquacao {
    private List<ElementoDTO> elementosEquacao;
    
    public InstituiModeloEquacao(List<ElementoDTO> elementosEquacao){
        this.elementosEquacao = elementosEquacao;
    }
    
    public void InstituirModeloEquacao(){
        
        Map<String, List<ElementoDTO>> equacaoDividida = dividindoEquacao(elementosEquacao);
        
        List<ElementoDTO> antes = equacaoDividida.get("antesDoIgual");
        List<ElementoDTO> depois = equacaoDividida.get("depoisDoIgual");  
        
        System.out.println("");
        
        for(ElementoDTO ElementoEquacao: antes){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        }  
        System.out.print("=");
        
        for(ElementoDTO ElementoEquacao: depois){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        }  
        
        System.out.println("");
    }
    
    public Map<String, List<ElementoDTO>>  dividindoEquacao(List<ElementoDTO> elementosEquacao){            
        List<ElementoDTO> antesDoIgual = new ArrayList<ElementoDTO>();
        List<ElementoDTO> depoisDoIgual = new ArrayList<ElementoDTO>();
        boolean pasouDoIgual = false;                
        
        for(ElementoDTO ElementoEquacao : elementosEquacao){
            if(ElementoEquacao.tipo == TipoElemento.igual)
                pasouDoIgual = true;
            else if(!pasouDoIgual){                
                antesDoIgual = organizandoElementos(antesDoIgual, ElementoEquacao);
            }
            else
                depoisDoIgual = organizandoElementos(depoisDoIgual, ElementoEquacao);     
        }      
        
        Map<String, List<ElementoDTO>> equacaoDividida = new HashMap<>();        
        equacaoDividida.put("antesDoIgual", antesDoIgual);
        equacaoDividida.put("depoisDoIgual", depoisDoIgual);
        
        return equacaoDividida;
    }
    
    public List<ElementoDTO> organizandoElementos(List<ElementoDTO> elementos, ElementoDTO elemento){
        if(elemento.tipo == TipoElemento.variavel){            
            elementos.add(0, elemento);            
            jogaElementoParaFrenteDaEquacao(elementos, false);           
        }
        else if(elemento.tipo == TipoElemento.numero){
            if(elementos.get(0).tipo == TipoElemento.numero || elementos.get(0).tipo == TipoElemento.variavel){
                ElementoDTO maisNaFrente = new ElementoDTO();
                maisNaFrente.setElementoDTO("+", TipoElemento.mais, 0);
                elementos.add(0, maisNaFrente);
            }
            
            if(elementos.get(elementos.size() - 1).tipo == TipoElemento.dividir || elementos.get(elementos.size() - 1).tipo == TipoElemento.vezis){
                elementos.add(2, elementos.get(elementos.size() - 1));
                elementos.remove(elementos.size() - 1);
                elementos.add(3,elemento);
            }else
                elementos.add(elemento);            
        }
        else{
            elementos.add(elemento);
        }       
        
        return elementos;
    }
    
    public List<ElementoDTO> jogaElementoParaFrenteDaEquacao(List<ElementoDTO> elementos, boolean sequencia){
        ElementoDTO elemento = elementos.get(elementos.size() - 1);        
        if((elemento.tipo == TipoElemento.numero || elemento.tipo == TipoElemento.variavel) && !sequencia){
            
            if(elementos.get(0).tipo == TipoElemento.numero || elementos.get(0).tipo == TipoElemento.variavel){
                ElementoDTO maisNaFrente = new ElementoDTO();
                maisNaFrente.setElementoDTO("+", TipoElemento.mais, 0);
                elementos.add(0, maisNaFrente);
            }
            
            elementos.add(0, elementos.get(elementos.size() - 1));
            elementos.remove(elementos.size() - 1);
            
            List<ElementoDTO> resultadoseguencia =  jogaElementoParaFrenteDaEquacao(elementos, true);
            return resultadoseguencia != null ? resultadoseguencia : elementos;
            
        }else if((elemento.tipo == TipoElemento.mais || elemento.tipo == TipoElemento.menos)){
            elementos.add(0, elementos.get(elementos.size() - 1));
            elementos.remove(elementos.size() - 1);
            
            List<ElementoDTO> resultadoseguencia =  jogaElementoParaFrenteDaEquacao(elementos, true);
            return resultadoseguencia != null ? resultadoseguencia : elementos;
        }
        else if(elemento.tipo == TipoElemento.dividir || elemento.tipo == TipoElemento.vezis){
            elementos.add(0, elementos.get(elementos.size() - 1));
            elementos.remove(elementos.size() - 1);
            
            List<ElementoDTO> resultadoseguencia =  jogaElementoParaFrenteDaEquacao(elementos, false);
            return resultadoseguencia != null ? resultadoseguencia : elementos;
        }else{
            return null;
        }      
    }
}
