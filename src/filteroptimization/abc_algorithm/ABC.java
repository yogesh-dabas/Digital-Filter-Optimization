/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filteroptimization.abc_algorithm;

import filteroptimization.Algorithm;

/**
 *
 * @author Yogesh Dabas
 */
public class ABC extends Algorithm {

    public ABC(int filter, int typeOfFilter) {
        super(filter, typeOfFilter);
    }
    public ABC(int filter, int typeOfFilter,double[][] population) {
        super(filter, typeOfFilter,population);
    }

    public double[] startAlgorithm() {

        System.out.println("~~~~~~~~~~~~~~~~~~FOOD FORAGING OF BEES~~~~~~~~~~~~~~~~~~");

        //No of employed bees = no of food sources
        Bee employed[] = new Bee[noOfCandidateSolution];
        Thread employedBee[] = new Thread[noOfCandidateSolution];

        //Start the Employed Bees Foraging
        for (int i = 0; i < noOfCandidateSolution; i++) {
            employed[i] = new Bee(i,this);
            employedBee[i] = new Thread(employed[i]);
            employedBee[i].start();
        }

        //Waiting for completing Algorithm
        for (int i = 0; i < noOfCandidateSolution; i++) {
            try {
                employedBee[i].join();
            } catch (InterruptedException ex) {

            }
        }
        
        //Print Results
        return new double[]{};
    }

}
