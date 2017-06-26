/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filteroptimization;

import filteroptimization.abc_algorithm.ABC;
import filteroptimization.cuckoo_algorithm.Cuckoo;
import pso_algorithm.PSO;

/**
 *
 * @author Yogesh Dabas
 */
public class FilterOptimization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //PSO p=new PSO(Algorithm.IIR,Algorithm.HIGH_PASS);
        //p.startAlgorithm();
        //p.printResults();
        
        //Cuckoo c=new Cuckoo(Algorithm.IIR,Algorithm.HIGH_PASS,p.getResCoeff());
       //c.maxCycles=200000;
      //c.startAlgorithm();
        //c.printResults();
        
        ABC a = new ABC(Algorithm.FIR, Algorithm.LOW_PASS);
        a.startAlgorithm();
        a.printResults();

        
    }

}
