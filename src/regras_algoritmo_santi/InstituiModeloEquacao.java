/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regras_algoritmo_santi;

import dto.ElementoDTO;
import enums.TipoElemento;
import util.PrintResultadoTela;
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
    private ElementoDTO ultimoElementoController;
    private ElementoDTO penultimoElementoController;
    private ElementoDTO antiPenultimoElementoController;
    
    public InstituiModeloEquacao(List<ElementoDTO> elementosEquacao){
        this.elementosEquacao = elementosEquacao;
    }
    
    private void controllerElemento(ElementoDTO elemento){
        antiPenultimoElementoController = penultimoElementoController;
        penultimoElementoController = ultimoElementoController;
        ultimoElementoController = elemento;
    }
    
    public void instituirModeloEquacao(){
        
        Map<String, List<ElementoDTO>> equacaoDividida = dividindoEquacao(elementosEquacao);
        
        System.out.println("");
        
        PrintResultadoTela.printResultadoTipoElemento(equacaoDividida.get("antesDaIgualdade"));         
        
        PrintResultadoTela.printResultadoTipoElemento(equacaoDividida.get("igualdade")); 
                
        PrintResultadoTela.printResultadoTipoElemento(equacaoDividida.get("depoisDaIgualdade")); 
        
        System.out.println(""); 
        System.out.println("Numeros Antes da Igualdade: ");        
        PrintResultadoTela.printResultadoElemento(equacaoDividida.get("equacaoNumerosAntesDaIgualdade"));
        System.out.println(""); 
        System.out.println("Variaveis Antes da Igualdade: ");        
        PrintResultadoTela.printResultadoElemento(equacaoDividida.get("equacaoVariavelAntesDaIgualdade"));
        System.out.println("");
        System.out.println("Numeros Depois da Igualdade: ");        
        PrintResultadoTela.printResultadoElemento(equacaoDividida.get("equacaoNumerosDepoisDaIgualdade"));
        System.out.println(""); 
        System.out.println("Variaveis Depois da Igualdade: ");        
        PrintResultadoTela.printResultadoElemento(equacaoDividida.get("equacaoVariavelDepoisDaIgualdade"));
        System.out.println(""); 
        
        formulaModeloPesquisa(equacaoDividida);
         
    }
    
    public Map<String, List<ElementoDTO>> dividindoEquacao(List<ElementoDTO> elementosEquacao){   
        
        List<ElementoDTO> antesDaIgualdade = new ArrayList<ElementoDTO>();
        List<ElementoDTO> igualdade = new ArrayList<ElementoDTO>();
        List<ElementoDTO> depoisDaIgualdade = new ArrayList<ElementoDTO>();                   
        
        for(ElementoDTO ElementoEquacao : elementosEquacao){
            //quarda os ultimos elemento mechidos
            controllerElemento(ElementoEquacao);
            
            if(ElementoEquacao.tipo == TipoElemento.igual)
                igualdade.add(ElementoEquacao);                
            else if(igualdade.isEmpty()){                
                antesDaIgualdade = organizandoElementos(antesDaIgualdade, ElementoEquacao);
            }
            else
                depoisDaIgualdade = organizandoElementos(depoisDaIgualdade, ElementoEquacao);     
        }      
        
        Map<String, List<ElementoDTO>> equacaoDividida = new HashMap<>();        
        equacaoDividida.put("antesDaIgualdade", antesDaIgualdade);
        equacaoDividida.put("equacaoNumerosAntesDaIgualdade", buscaSoNumeroEquacao(antesDaIgualdade));
        equacaoDividida.put("equacaoVariavelAntesDaIgualdade", buscaSoVariaveisEquacao(antesDaIgualdade));        
        equacaoDividida.put("igualdade", igualdade);
        equacaoDividida.put("depoisDaIgualdade", depoisDaIgualdade);
        equacaoDividida.put("equacaoNumerosDepoisDaIgualdade", buscaSoNumeroEquacao(depoisDaIgualdade));
        equacaoDividida.put("equacaoVariavelDepoisDaIgualdade", buscaSoVariaveisEquacao(depoisDaIgualdade)); 
        
        return equacaoDividida;
    }
    
    public List<ElementoDTO> organizandoElementos(List<ElementoDTO> elementos, ElementoDTO elemento){
        if(elemento.tipo == TipoElemento.variavel){            
            elementos.add(0, elemento);            
            jogaElementoVariavelParaFrenteDaEquacao(elementos, false);           
        }
        else if(elemento.tipo == TipoElemento.numero){
            jogaElementoNumeroParaOFinalDaEquacao(elementos, elemento);                       
        }
        else{
            elementos.add(elemento);
        }       
        
        return elementos;
    }
    
    public List<ElementoDTO> jogaElementoVariavelParaFrenteDaEquacao(List<ElementoDTO> elementos, boolean sequencia){
        ElementoDTO elemento = elementos.get(elementos.size() - 1);        
        if((elemento.tipo == TipoElemento.numero || elemento.tipo == TipoElemento.variavel) && !sequencia){
            
            if((elementos.get(0).tipo == TipoElemento.numero || elementos.get(0).tipo == TipoElemento.variavel)){
                ElementoDTO maisNaFrente = new ElementoDTO();
                maisNaFrente.setElementoDTO("+", TipoElemento.mais, 0);
                elementos.add(0, maisNaFrente);
            }
            
            elementos.add(0, elementos.get(elementos.size() - 1));
            elementos.remove(elementos.size() - 1);
            
            List<ElementoDTO> resultadoseguencia =  jogaElementoVariavelParaFrenteDaEquacao(elementos, true);
            return resultadoseguencia != null ? resultadoseguencia : elementos;
            
        }else if((elemento.tipo == TipoElemento.mais || elemento.tipo == TipoElemento.menos)){
            elementos.add(0, elementos.get(elementos.size() - 1));
            elementos.remove(elementos.size() - 1);
            
            List<ElementoDTO> resultadoseguencia =  jogaElementoVariavelParaFrenteDaEquacao(elementos, true);
            return resultadoseguencia != null ? resultadoseguencia : elementos;
        }
        else if(elemento.tipo == TipoElemento.dividir || elemento.tipo == TipoElemento.vezis){
            if(antiPenultimoElementoController.tipo == TipoElemento.numero){
                elementos.add(0, elementos.get(elementos.size() - 1));
                elementos.remove(elementos.size() - 1);
            
            
                List<ElementoDTO> resultadoseguencia =  jogaElementoVariavelParaFrenteDaEquacao(elementos, false);
                return resultadoseguencia != null ? resultadoseguencia : elementos;
            }else{
                elementos.add(3, elementos.get(0));
                elementos.remove(0);
                elementos.add(2,elemento);
                elementos.remove(elementos.size() - 1);
                return  null;
            }
        }else{
            return null;
        }      
    }
    
    public void jogaElementoNumeroParaOFinalDaEquacao(List<ElementoDTO> elementos, ElementoDTO elemento){
        if(elementos.isEmpty() || (elementos.get(0).tipo == TipoElemento.numero || elementos.get(0).tipo == TipoElemento.variavel)){
                ElementoDTO maisNaFrente = new ElementoDTO();
                maisNaFrente.setElementoDTO("+", TipoElemento.mais, 0);
                elementos.add(0, maisNaFrente);
            }
            
            if(!elementos.isEmpty() && (elementos.get(elementos.size() - 1).tipo == TipoElemento.dividir || elementos.get(elementos.size() - 1).tipo == TipoElemento.vezis) && antiPenultimoElementoController.tipo == TipoElemento.variavel){
                elementos.add(2, elementos.get(elementos.size() - 1));
                elementos.remove(elementos.size() - 1);
                elementos.add(3,elemento);
            }else
                elementos.add(elemento); 
    }
       
    public List<ElementoDTO> buscaSoNumeroEquacao(List<ElementoDTO> elementos){
        List<ElementoDTO> parteEquacaoNumero = new ArrayList<>();
        
        for(int i = elementos.size() - 1; i >= 0; i--){            
            if(elementos.get(i).tipo != TipoElemento.variavel)
                parteEquacaoNumero.add(0, elementos.get(i));            
            else{
                if(!parteEquacaoNumero.isEmpty() && (parteEquacaoNumero.get(0).tipo == TipoElemento.dividir || parteEquacaoNumero.get(0).tipo == TipoElemento.vezis)){
                    parteEquacaoNumero.remove(0);
                    parteEquacaoNumero.remove(0);                   
                }
                break;
            }
        }
        
        return parteEquacaoNumero;
    }
    
    public List<ElementoDTO> buscaSoVariaveisEquacao(List<ElementoDTO> elementos){
        List<ElementoDTO> parteEquascaoVariavel = new ArrayList<>();
        
        for(int i = 0; i <= elementos.size() - 1; i++){            
            if(elementos.get(i).tipo != TipoElemento.numero)
                parteEquascaoVariavel.add(elementos.get(i));            
            else{                
                if(elementos.get(i - 1).tipo == TipoElemento.dividir ||
                        elementos.get(i - 1).tipo == TipoElemento.vezis ||
                        ((i + 2) <= (elementos.size() - 1) && elementos.get(i + 1).tipo == TipoElemento.dividir && elementos.get(i + 2).tipo == TipoElemento.variavel) ||
                        ((i + 2) <= (elementos.size() - 1) && elementos.get(i + 1).tipo == TipoElemento.vezis && elementos.get(i + 2).tipo == TipoElemento.variavel))
                {
                    parteEquascaoVariavel.add(elementos.get(i));                                      
                }else if(parteEquascaoVariavel.get(parteEquascaoVariavel.size() - 1).tipo == TipoElemento.mais || parteEquascaoVariavel.get(parteEquascaoVariavel.size() - 1).tipo == TipoElemento.menos){
                    parteEquascaoVariavel.remove(parteEquascaoVariavel.size() - 1);
                    break;
                }else
                    break; 
            }
        }
        
        return parteEquascaoVariavel;
    }
    
    public void formulaModeloPesquisa(Map<String, List<ElementoDTO>> elementosEquacao){
        List<ElementoDTO> modeloPesquisa = new ArrayList<>(); 
        int posicao = 0;
        ElementoDTO sinal = new ElementoDTO();
        sinal.setElementoDTO("ª", TipoElemento.sinal, 0);
        
        List<ElementoDTO> variaveisAntesDaIgualdade = elementosEquacao.get("equacaoVariavelAntesDaIgualdade");        
        if(!variaveisAntesDaIgualdade.isEmpty()){
            modeloPesquisa.add(sinal);
            ElementoDTO elementoDTO = montaModeloPesquisa(variaveisAntesDaIgualdade, TipoElemento.variavel, ++posicao);
            
            modeloPesquisa.add(elementoDTO);
        }       
        
        List<ElementoDTO> numerosAntesDaIgualdade = elementosEquacao.get("equacaoNumerosAntesDaIgualdade");
        if(!numerosAntesDaIgualdade.isEmpty()){
            modeloPesquisa.add(sinal);
            ElementoDTO elementoDTO = montaModeloPesquisa(numerosAntesDaIgualdade, TipoElemento.numero, ++posicao);
            
            modeloPesquisa.add(elementoDTO);
        }
        
        List<ElementoDTO> Igualdade = elementosEquacao.get("igualdade");
        if(!Igualdade.isEmpty()){            
            ElementoDTO elementoDTO = montaModeloPesquisa(Igualdade, TipoElemento.igual, 0);
            
            modeloPesquisa.add(elementoDTO);
        }
        
        List<ElementoDTO> variaveisDepoisDaIgualdade = elementosEquacao.get("equacaoVariavelDepoisDaIgualdade");
        if(!variaveisDepoisDaIgualdade.isEmpty()){
            modeloPesquisa.add(sinal);
            ElementoDTO elementoDTO = montaModeloPesquisa(variaveisDepoisDaIgualdade, TipoElemento.variavel, ++posicao);
                       
            modeloPesquisa.add(elementoDTO);
        }   
        
        List<ElementoDTO> nuemrosDepoisDaIgualdade = elementosEquacao.get("equacaoNumerosDepoisDaIgualdade");
        if(!nuemrosDepoisDaIgualdade.isEmpty()){
            modeloPesquisa.add(sinal);
            ElementoDTO elementoDTO = montaModeloPesquisa(nuemrosDepoisDaIgualdade, TipoElemento.numero, ++posicao);
                       
            modeloPesquisa.add(elementoDTO);
        }
        
        PrintResultadoTela.printResultadoTipoElemento(modeloPesquisa);
    }
    
    public ElementoDTO montaModeloPesquisa(List<ElementoDTO> elementos, TipoElemento tipoElemento, int posicao){
        String leitura = ""; 
        
        for(ElementoDTO ElementoEquacao: elementos){            
                leitura += ElementoEquacao.elemento;
        }  
            
        ElementoDTO elementoDTO = new ElementoDTO();
        elementoDTO.setElementoDTO(leitura, tipoElemento, posicao);   
        
        return elementoDTO;
    }
}
