/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filteroptimization.abc_algorithm;

/**
 *
 * @author Yogesh Dabas
 */
class Bee implements Runnable {

    int beeNumber;
    ABC abc;
    
    public Bee(int beeNumber,ABC abc){
        this.beeNumber=beeNumber;
        this.abc=abc;
    }
    public void run() {

        /**
         * ************Employed Bee Phase********************
         */
        //Initialize the resCoeff & resCost
        abc.resCoeff[beeNumber] = abc.population[beeNumber].clone();
        abc.resCost[beeNumber] = abc.getCostFunction(abc.population[beeNumber]);

        int count = 0;

        for (int cycle = 0; cycle < abc.maxCycles; cycle++) {

            //Step 3: Calculate Cost Function CF(xi)
            double costOfSource = abc.resCost[beeNumber];

            //Generate a neighbour
            double x_neighbour[] = abc.getRandomSolution(beeNumber);
            double costOfNeighbour = abc.getCostFunction(x_neighbour);

            /**
             * ************Onlooker Bee Phase: Greedy
             * Search********************
             */
            if (costOfNeighbour > costOfSource) {
                //Testing
                abc.printUpdate(cycle, abc.population[beeNumber], costOfSource, x_neighbour, costOfNeighbour);

                //Replace old candidate value with its neighbour
                abc.population[beeNumber] = x_neighbour;

                abc.resCoeff[beeNumber] = x_neighbour.clone();
                abc.resCost[beeNumber] = costOfNeighbour;

                //Reset the count 
                count = 0;

            } else {
                count++;
                /**
                 * ************Scout Bee Phase********************
                 */
                if (count > abc.limit) {

                    //Replace bee source with random coefficients
                    for (int j = 0; j < abc.n + abc.m; j++) {
                        abc.population[beeNumber][j] = (float) Math.abs(Math.random() - 0.5);
                    }

                    //If selected global solution is better than existing solution
                    double costOfRandomSource = abc.getCostFunction(abc.population[beeNumber]);
                    if (costOfRandomSource > costOfSource) {

                        //Update the resutant Coeff & Cost with scout's content
                        abc.resCoeff[beeNumber] = abc.population[beeNumber];
                        abc.resCost[beeNumber] = costOfRandomSource;

                    }
                }
            }

        }
    }
}
