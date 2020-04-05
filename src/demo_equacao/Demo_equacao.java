/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo_equacao;


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
        
        System.out.println("Equação: ");
        
        String equacao = entrada.nextLine();
        
        //FormularModeloEquacao(equacao);
        
        FormulaModeloEquacao formulaEquacao = new FormulaModeloEquacao(equacao);
        
        String modeloEquacao = formulaEquacao.FormularModeloEquacao();
        
        System.out.println(modeloEquacao);
    }
}
