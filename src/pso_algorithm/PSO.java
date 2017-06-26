/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso_algorithm;

import filteroptimization.Algorithm;

/**
 *
 * @author Yogesh Dabas
 */
public class PSO extends Algorithm {

    public PSO(int filter, int typeOfFilter) {
        super(filter, typeOfFilter);
    }

    public PSO(int filter, int typeOfFilter, double population[][]) {
        super(filter, typeOfFilter, population);
    }

    public void startAlgorithm() {

        System.out.println("~~~~~~~~~~~~~~~~~~Particle Search Start~~~~~~~~~~~~~~~~~~");

        //No of Particle = no of initial population
        Particle part[] = new Particle[noOfCandidateSolution];
        Thread particle[] = new Thread[noOfCandidateSolution];

        //Initialize the resCoeff & resCost
        for (int i = 0; i < noOfCandidateSolution; i++) {
            resCoeff[i] = population[i].clone();
            resCost[i] = getCostFunction(population[i]);
        }

        //Start the Particle
        for (int i = 0; i < noOfCandidateSolution; i++) {
            part[i] = new Particle(i, this);
            particle[i] = new Thread(part[i]);
            particle[i].start();
        }

        //Waiting for completing Algorithm
        for (int i = 0; i < noOfCandidateSolution; i++) {
            try {
                particle[i].join();
            } catch (InterruptedException ex) {

            }
        }
    }

}
