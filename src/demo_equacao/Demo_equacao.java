/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo_equacao;


import dto.ElementoDTO;
import enums.TipoElemento;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import regras_algoritmo_santi.FormulaModeloEquacao;

/**
 *
 * @author davim
 */
public class Demo_equacao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner entrada = new Scanner(System.in);        

        //int teste = Integer. parseInt("1 + 2");
        
        //System.out.println(teste);
        
        System.out.println("Equação: ");
        
        String equacao = entrada.nextLine();
        
        //FormularModeloEquacao(equacao);
        
        FormulaModeloEquacao formulaEquacao = new FormulaModeloEquacao(equacao);
        
        List<ElementoDTO> modeloEquacao = formulaEquacao.FormularModeloEquacao();
        
        FatorarModeloEquacao(modeloEquacao);
    }
    
    public static void FatorarModeloEquacao(List<ElementoDTO> ElementosEquacao){
        
        Map<String, List<ElementoDTO>> equacaoDividida = dividindoEquacao(ElementosEquacao);
        
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
    
    public static Map<String, List<ElementoDTO>>  dividindoEquacao(List<ElementoDTO> ElementosEquacao){            
        List<ElementoDTO> antesDoIgual = new ArrayList<ElementoDTO>();
        List<ElementoDTO> depoisDoIgual = new ArrayList<ElementoDTO>();
        boolean pasouDoIgual = false;                
        
        for(ElementoDTO ElementoEquacao : ElementosEquacao){
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
    
    public static List<ElementoDTO> organizandoElementos(List<ElementoDTO> elementos, ElementoDTO elemento){
        if(elemento.tipo == TipoElemento.variavel){
            elementos.add(0, elemento);
            
            if(elementos.size() != 1){         
                elementos.add(0, elementos.get(elementos.size() - 1));
                elementos.remove(elementos.size() - 1);
            }
            else{
                ElementoDTO maisNaFrente = new ElementoDTO();
                maisNaFrente.setElementoDTO("+", TipoElemento.mais, 0);
                elementos.add(0, maisNaFrente);
            }                
        }
        else{
            elementos.add(elemento);
        }       
        
        return elementos;
    }
}
