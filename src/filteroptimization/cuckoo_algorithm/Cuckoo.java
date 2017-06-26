/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filteroptimization.cuckoo_algorithm;

import filteroptimization.Algorithm;

/**
 *
 * @author Yogesh Dabas
 */
public class Cuckoo extends Algorithm{
    
    public Cuckoo(int filter, int typeOfFilter) {
        super(filter, typeOfFilter);
    }
    public Cuckoo(int filter, int typeOfFilter, double population[][]) {
        super(filter, typeOfFilter,population);
    }

    public void startAlgorithm() {

        System.out.println("~~~~~~~~~~~~~~~~~~CUCKOO WORK STARTED~~~~~~~~~~~~~~~~~~");

        
        /**
         * ************Employed Bee Phase********************
         */
        //Initialize the resCoeff & resCost
        resCoeff = population.clone();
        for (int i = 0; i < noOfCandidateSolution; i++) {
            resCost[i] = getCostFunction(population[i]);
        }

        for (int cycle = 0; cycle < maxCycles; cycle++) {

            //Get a random Cuckoo
            int t=(int)(Math.random()*noOfCandidateSolution);
            double cuckooEgg[] = getRandomSolution(t);
            double costOfCuckooEgg = getCostFunction(cuckooEgg);
            
            
            //Get a random Nest
            t=(int)(Math.random()*noOfCandidateSolution);
            double costOfNestEgg = resCost[t];
            
            //Cuckoo Going to nest
            if (costOfCuckooEgg > costOfNestEgg) {
                //Testing
                printUpdate(cycle, population[t], costOfNestEgg, cuckooEgg, costOfCuckooEgg);

                //Replace old candidate value with its neighbour
                population[t] = cuckooEgg;

                resCoeff[t] = cuckooEgg.clone();
                resCost[t] = costOfCuckooEgg;

            } 
        }
    }
}
