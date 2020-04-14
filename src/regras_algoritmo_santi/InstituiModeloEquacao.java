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
    private ElementoDTO ultimoElementoController;
    private ElementoDTO penultimoElementoController;
    
    public InstituiModeloEquacao(List<ElementoDTO> elementosEquacao){
        this.elementosEquacao = elementosEquacao;
    }
    
    private void controllerElemento(ElementoDTO elemento){
        penultimoElementoController = ultimoElementoController;
        ultimoElementoController = elemento;
    }
    
    public void instituirModeloEquacao(){
        
        Map<String, List<ElementoDTO>> equacaoDividida = dividindoEquacao(elementosEquacao);
        
        List<ElementoDTO> antes = equacaoDividida.get("antesDaIgualdade");
        List<ElementoDTO> igualdade = equacaoDividida.get("igualdade");
        List<ElementoDTO> depois = equacaoDividida.get("depoisDaIgualdade");  
        
        System.out.println("");
        
        for(ElementoDTO ElementoEquacao: antes){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        }  
        
        for(ElementoDTO ElementoEquacao: igualdade){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        } 
        
        for(ElementoDTO ElementoEquacao: depois){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        }  
        
        System.out.println("");
        
        for(ElementoDTO ElementoEquacao: equacaoDividida.get("equacaoVariavelDepoisDaIgualdade")){
            String posicao = (ElementoEquacao.posicao != 0) ? Integer.toString(ElementoEquacao.posicao) : "";
            System.out.print(ElementoEquacao.tipo.getElemento() + posicao);
            //leitura += ElementoEquacao.tipo.getElemento()+ posicao;
        }  
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
            if(penultimoElementoController.tipo == TipoElemento.numero){
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
            
            if(!elementos.isEmpty() && (elementos.get(elementos.size() - 1).tipo == TipoElemento.dividir || elementos.get(elementos.size() - 1).tipo == TipoElemento.vezis) && penultimoElementoController.tipo == TipoElemento.variavel){
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
                if(parteEquascaoVariavel.get(parteEquascaoVariavel.size() - 1).tipo == TipoElemento.dividir || parteEquascaoVariavel.get(parteEquascaoVariavel.size() - 1).tipo == TipoElemento.vezis){
                    parteEquascaoVariavel.add(elementos.get(i));                                      
                }else if(parteEquascaoVariavel.get(parteEquascaoVariavel.size() - 1).tipo == TipoElemento.mais || parteEquascaoVariavel.get(parteEquascaoVariavel.size() - 1).tipo == TipoElemento.menos){
                    parteEquascaoVariavel.remove(parteEquascaoVariavel.size() - 1);
                }
                break;
            }
        }
        
        return parteEquascaoVariavel;
    }
}
