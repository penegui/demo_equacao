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
import regras_algoritmo_santi.InstituiModeloEquacao;

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
        
        InstituiModeloEquacao instituiEquacao = new InstituiModeloEquacao(modeloEquacao);     
        
        instituiEquacao.InstituirModeloEquacao();
    }      
}
