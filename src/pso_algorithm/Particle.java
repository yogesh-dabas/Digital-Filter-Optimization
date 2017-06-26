/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso_algorithm;

/**
 *
 * @author Yogesh Dabas
 */
public class Particle implements Runnable {

    int particleNumber;
    //double pBest[];
    double velocity[];

    PSO pso;

    public Particle(int particleNumber, PSO pso) {
        this.particleNumber = particleNumber;
        this.pso = pso;
        //pBest=new double[pso.m+pso.n];
        velocity = new double[pso.m + pso.n];
        for (int i = 0; i < pso.m + pso.n; i++) {
            velocity[i] = Math.random();
        }
    }

    public void run() {

        for (int cycle = 0; cycle < pso.maxCycles; cycle++) {

            //Update Velocity & thus position of particle
            double c1 = 1.5, c2 = 1.5;
            for (int i = 0; i < pso.m + pso.n; i++) {

                //Updating Velocity
                velocity[i] += c1 * Math.random() * (pso.resCoeff[particleNumber][i] - pso.population[particleNumber][i]);
                velocity[i] += c2 * Math.random() * (pso.resCoeff[pso.getGlobalBestSolutionIndex()][i] - pso.population[particleNumber][i]);
                
                //Updating position
                pso.population[particleNumber][i] += velocity[i];
            }

            //Comparing particle cost with best cost for that particle
            double costOfPresent = pso.getCostFunction(pso.population[particleNumber]);
            double costOfpBest = pso.getCostFunction(pso.resCoeff[particleNumber]);

            if (costOfPresent > costOfpBest) {
                //Testing
                pso.printUpdate(cycle, pso.resCoeff[particleNumber], costOfpBest, pso.population[particleNumber], costOfPresent);

                //Replace old candidate value with its neighbour
                pso.resCoeff[particleNumber] = pso.population[particleNumber].clone();
                pso.resCost[particleNumber] = costOfPresent;
            }
            if(pso.resCoeff[particleNumber]==pso.population[particleNumber]){
                System.out.print("true");
            }
        }
    }
}
