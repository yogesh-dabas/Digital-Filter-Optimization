/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filteroptimization;

/**
 *
 * @author Yogesh Dabas
 */
public class Algorithm {

    //filter chosen
    int filter;
    public static int IIR = 0;
    public static int FIR = 1;

    //Type of Filter 0-Low Pass,1-High Pass,2-Band Pass,3-Band Stop
    int typeOfFilter;
    public static int LOW_PASS = 0;
    public static int HIGH_PASS = 1;
    public static int BAND_PASS = 2;
    public static int BAND_STOP = 3;

    //Order of Filter
    public int m;
    public int n;

    //Low Pass / High Pass Frequencies
    public double normOmega = 0.45f * 3.14f;

    //Band Pass / Band Stop Frequencies
    public double omega1 = 0.30f * 3.14f;
    public double omega2 = 0.60f * 3.14f;

    //Initial Population
    public double population[][];

    //Algo Inputs
    public int maxCycles = 1000;
    public int limit = 100;
    public int noOfCandidateSolution = 30;
    public int upperBound = 1;
    public int lowerBound = -1;

    //Resultant Magnitude response
    public double resCoeff[][];
    public double resCost[];

    public Algorithm(int filter, int typeOfFilter) {
        this.filter = filter;
        this.typeOfFilter = typeOfFilter;
        if (filter == Algorithm.FIR) {
            m = 5;
            n = 0;

        } else if (filter == Algorithm.IIR) {
            m = 5;
            n = 5;
        }

        //Initialize Initial Population and Resultant Msg Response
        population = new double[noOfCandidateSolution][m + n];
        resCoeff = new double[noOfCandidateSolution][m + n];
        resCost = new double[noOfCandidateSolution];

        System.out.println("~~~~~~~~~~~~~~~~INITIALIZING INPUT POPULATION~~~~~~~~~~~~~~~~~~~~");

        //Initialize the population of solutions xi where i .. 1, 2, ... SN;
        for (int i = 0; i < noOfCandidateSolution; i++) {
            for (int j = 0; j < n + m; j++) {
                population[i][j] = (float) Math.abs(Math.random() - 0.5);
                System.out.print(population[i][j] + " ");
            }
            System.out.println("");
        }
    }
    public Algorithm(int filter, int typeOfFilter, double population[][]) {
        this.filter = filter;
        this.typeOfFilter = typeOfFilter;
        if (filter == Algorithm.FIR) {
            m = 5;
            n = 0;

        } else if (filter == Algorithm.IIR) {
            m = 5;
            n = 5;
        }

        //Initialize Initial Population and Resultant Msg Response
        resCoeff = new double[noOfCandidateSolution][m + n];
        resCost = new double[noOfCandidateSolution];

        System.out.println("~~~~~~~~~~~~~~~~INITIALIZING INPUT POPULATION~~~~~~~~~~~~~~~~~~~~");
        this.population=population;
    }

    public void printUpdate(int cycle, double[] x, double costOfSource, double[] x_neighbour, double costOfNeighbour) {

        System.out.print("\n\n~~~~~~~~~cycle " + cycle + "~~~~~~~~~~~\nSource: ");
        for (int j = 0; j < m + n; j++) {
            System.out.print(x[j] + ",");
        }
        System.out.println("\nCostS: " + costOfSource);
        System.out.print("\n\nNeighbour: ");
        for (int i = 0; i < m + n; i++) {
            System.out.print(x_neighbour[i] + ",");
        }
        System.out.println("\nCostN: " + costOfNeighbour);
    }

    public double[] getRandomSolution(int i) {

        //Neighbour
        double x_neighbour[] = population[i].clone();

        //A candidate solution x_k (where k is a random integer in the interval  1 and No of food Source and k !=i) 
        int k;
        do {
            k = (int) (Math.random() * noOfCandidateSolution);
        } while (i == k);

        //A parameter j for the random coeff 
        int j = (int) (Math.random() * (m + n));

        //Generate new coeff's value for k food source and jth coeff
        x_neighbour[j] = Math.abs(population[i][j] + (population[i][j] - population[k][j]) * ((Math.random() - 0.5)));

        return x_neighbour;
    }

    public double getCostFunction(double x[]) {

        double costFunction = 0;

        for (double omega = 0; omega < 3.14f; omega += 0.01) {

            //Ideal Normal Mag Response wrt filter type
            double normMagnitudeResponse = 1;

            if (typeOfFilter == 0) {
                if (omega > normOmega) {
                    normMagnitudeResponse = 0;
                }
            } else if (typeOfFilter == 1) {
                if (omega < normOmega) {
                    normMagnitudeResponse = 0;
                }
            } else if (typeOfFilter == 2) {
                if (omega < omega1 || omega > omega2) {
                    normMagnitudeResponse = 0;
                }
            } else if (typeOfFilter == 3) {
                if (omega > omega1 || omega < omega2) {
                    normMagnitudeResponse = 0;
                }
            }

            costFunction += Math.abs(normMagnitudeResponse - getMagnitudeResponse(omega, x));
        }

        return 1 / costFunction;
    }

    public double getMagnitudeResponse(double omega, double x[]) {

        //Numerator
        double numTerm1 = 0;
        for (int k = 0; k < m; k++) {
            numTerm1 += (x[k] * Math.cos(k * omega));
        }

        double numTerm2 = 0;
        for (int k = 0; k < m; k++) {
            numTerm2 += (x[k] * Math.sin(k * omega));
        }

        double num = numTerm1 * numTerm1 + numTerm2 * numTerm2;

        if (filter == Algorithm.FIR) {
            return Math.sqrt(num);
        }

        //Denominator
        double denTerm1 = 0;
        for (int k = m; k < m + n; k++) {
            denTerm1 += (x[k] * Math.cos((k - m) * omega));
        }

        double denTerm2 = 0;
        for (int k = m; k < m + n; k++) {
            denTerm2 += (x[k] * Math.sin((k - m) * omega));
        }

        double den = denTerm1 * denTerm1 + denTerm2 * denTerm2;

        //Normalized Magnitude Response
        return Math.sqrt(num / den);

    }
    
    public int getGlobalBestSolutionIndex(){
        
        //Find Best Solution
        double t = 0;
        int index = 0;
        for (int k = 0; k < noOfCandidateSolution; k++) {
            if (t < resCost[k]) {
                t = resCost[k];
                index = k;
            }
        }
        return index;
    }

    public void printResults() {
        
        //Find Best Solution
        int index = getGlobalBestSolutionIndex();
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Optimizaed Coeff Cost~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\nGlobal Best Solution COST: " + resCost[index] + " at " + index);

        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~Result Coeff ~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        for (int k = 0; k < m + n; k++) {
            System.out.print(resCoeff[index][k] + "\t");
        }
        System.out.println();

        System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~Mag Resp ~~~~~~~~~~~~~~~~~~~~~~~\n");
        for (double omega = 0; omega < 3.14f; omega += 0.01) {
            System.out.println(getMagnitudeResponse(omega, resCoeff[index]));
        }
    }
    public double[][] getResCoeff(){
        return resCoeff;
    }
}
