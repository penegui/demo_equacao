/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regras_algoritmo_santi;

import dto.ElementoDTO;
import enums.TipoElemento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davim
 */
public class FormulaModeloEquacao {
    private String equacao;
    
    public FormulaModeloEquacao(String equacao){
        this.equacao = equacao;
    }
    
    public List<ElementoDTO> FormularModeloEquacao(){        
        equacao = equacao.trim().replace(" ", "");
        
        char[] elementos = equacao.toCharArray();
        
        return lendoEquacao(elementos);         
    }
    
    public List<ElementoDTO> lendoEquacao(char[] elementos){          
        List<ElementoDTO> ElementosEquacao = lendoElemento(elementos);
        
        //String leitura = new String();        
        for(ElementoDTO ElementoEquacao: ElementosEquacao){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        }    
        
        return ElementosEquacao;
    }
    
    public List<ElementoDTO> lendoElemento(char[] elementos){
        
        List<ElementoDTO> elementosDTO = new ArrayList<ElementoDTO>();
        int numero = 0;        
        int variavel = 0;
        
        for(int i = 0; i <= elementos.length - 1; i++){           
            if(isInt(elementos[i])){
                int elementosIguasAINTNaSeguencia = encontraElementosIguasNaSequencia(elementos, i, TipoElemento.numero);
                
                String elemento = elementosIguasAINTNaSeguencia != 0 ? new String(elementos).substring(i, elementosIguasAINTNaSeguencia + 1) : Character.toString(elementos[i]);
                
                ElementoDTO elementoDTO = montaElementoDTO(elemento, TipoElemento.numero, ++numero);
                                  
                elementosDTO.add(elementoDTO);
                
                i = elementosIguasAINTNaSeguencia != 0 ? elementosIguasAINTNaSeguencia : i;                 
            }else if(isMathOperations(elementos[i])){
                ElementoDTO elementoDTO = montaElementoDTO(Character.toString(elementos[i]), TipoElemento.selecionaTipoPorElemento(Character.toString(elementos[i])), 0);
              
                elementosDTO.add(elementoDTO);
            }
            else if(isString(elementos[i])){
                int elementosIguasASTRINGNaSeguencia = encontraElementosIguasNaSequencia(elementos, i, TipoElemento.variavel);
                
                String elemento = elementosIguasASTRINGNaSeguencia != 0 ? new String(elementos).substring(i, elementosIguasASTRINGNaSeguencia + 1) : Character.toString(elementos[i]);
                
                ElementoDTO elementoDTO = montaElementoDTO(elemento , TipoElemento.variavel, ++variavel);               
                
                elementosDTO.add(elementoDTO);
                
                i = elementosIguasASTRINGNaSeguencia != 0 ? elementosIguasASTRINGNaSeguencia : i;
            }
        }
        return elementosDTO;
    }
    
    public int encontraElementosIguasNaSequencia(char[] elementos, int posicaoElemento, TipoElemento tipo){
        
        if(tipo == TipoElemento.numero)
            for(int i = ++posicaoElemento; i <= elementos.length - 1; i++){  
                if(isInt(elementos[i])){
                    int resultadoSenquencial = encontraElementosIguasNaSequencia(elementos, i, TipoElemento.numero);
                    return resultadoSenquencial != 0 ? resultadoSenquencial : i;
                }else{
                    return 0;
                }
            }
        else if(tipo == TipoElemento.variavel)
            for(int i = ++posicaoElemento; i <= elementos.length - 1; i++){  
                if(isString(elementos[i])){ 
                    int resultadoSenquencial = encontraElementosIguasNaSequencia(elementos, i, TipoElemento.variavel);
                    return resultadoSenquencial != 0 ? resultadoSenquencial : i;
                }else{
                    return 0;
                }
            }
        
        return 0;
    }
    
    public ElementoDTO montaElementoDTO(String elemento, TipoElemento tipo, int posicao){
        ElementoDTO elementoDTO = new ElementoDTO();
                 
        elementoDTO.elemento = elemento;
        elementoDTO.tipo = tipo;
        elementoDTO.posicao = posicao;
        
        return elementoDTO;
    }
    
    public boolean isInt(char elemento){
        boolean teste = Character.isDigit(elemento);
        return Character.isDigit(elemento);
    }
    
    public boolean isMathOperations(char elemento){      
        boolean teste = "+".equals(Character.toString(elemento)) || "-".equals(Character.toString(elemento)) || "*".equals(Character.toString(elemento)) || "/".equals(Character.toString(elemento)) || "=".equals(Character.toString(elemento));
        return "+".equals(Character.toString(elemento)) || "-".equals(Character.toString(elemento)) || "*".equals(Character.toString(elemento)) || "/".equals(Character.toString(elemento)) || "=".equals(Character.toString(elemento));
    }
    
    public boolean isString(char elemento){
        boolean teste = Character.isLetter(elemento);
        return Character.isLetter(elemento);
    }
}
