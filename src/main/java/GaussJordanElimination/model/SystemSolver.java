/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GaussJordanElimination.model;

import GaussJordanElimination.pojo.Equation;
import GaussJordanElimination.pojo.EquationSystem;

/**
 *
 * @author Mahmoud Abas
 */
public class SystemSolver {

    //----------Start Singleton----------//
    private static SystemSolver instance;

    private SystemSolver() {
        system = new EquationSystem();
    }

    /**
     *
     * @return
     */
    public static synchronized SystemSolver getInstance() {
        if (instance == null) {
            instance = new SystemSolver();
        }
        return instance;
    }
    //----------End Singleton----------//

    //----------Start Vars----------//
    private final EquationSystem system;
    //----------End Vars----------//

    //----------Start Logic----------//

    /**
     * Clear System Equations
     */
    public void clearSystem() {
        system.clearAllEquations();
    }

    /**
     *
     * @param equation
     */
    public void addEquationToSystem(Equation equation) {
        system.addEquationToSystem(equation);
    }

    /**
     *
     * @throws Exception
     */
    public void solveByGausian() throws Exception {
        system.solveByGausian();
    }

    /**
     *
     * @throws Exception
     */
    public void solveByGauseJordan() throws Exception {
        system.solveByGauseJordan();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public double[] collectResultsFromSystem() throws Exception {
        double[] vars = new double[system.getEquationCount()];
        for (int i = vars.length - 1; i >= 0; i--) {
            Equation equation;
            try {
                equation = system.getEquatinInIndex(i);
            } catch (Exception e) {
                throw new Exception("Can't Get Solution !");
            }
            double lastNumInEquation = equation.getColValInIndex(vars.length);
            int firstNonZeroIndex = equation.getFirstNonZeroIndex();
            if (firstNonZeroIndex >= equation.getVarsParametersAndConstant().length - 1) {
                throw new Exception("Can't Get Solution !");
            }
            for (int j = firstNonZeroIndex + 1; j < vars.length; j++) {
                lastNumInEquation -= equation.getColValInIndex(j) * vars[j];
            }
            vars[i] = lastNumInEquation;
        }
        return vars;
    }

    /**
     * Print The System
     */
    public void printSystem() {
        System.out.println("\n\n----------Start System----------");
        System.out.println(system);
        System.out.println("----------End   System----------");
    }
    //----------End Logic----------//

}
