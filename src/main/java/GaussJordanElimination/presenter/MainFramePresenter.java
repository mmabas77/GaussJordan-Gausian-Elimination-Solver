/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GaussJordanElimination.presenter;

import GaussJordanElimination.model.SystemSolver;
import GaussJordanElimination.pojo.Equation;
import GaussJordanElimination.view.MainFrame;

/**
 *
 * @author Mahmoud Abas
 */
public class MainFramePresenter {

    //----------Start Singleton----------//
    private static MainFramePresenter instance;

    private MainFramePresenter() {
        solver = SystemSolver.getInstance();
    }

    /**
     *
     * @return
     */
    public static synchronized MainFramePresenter getInstance() {
        if (instance == null) {
            instance = new MainFramePresenter();
        }
        return instance;
    }
    //----------End Singleton----------//

    //----------Start Vars----------//
    private MainFrame frame;
    private final SystemSolver solver;
    //----------End Vars----------//

    //----------Start Logic----------//

    /**
     * Clear The System
     */
    public void clearSystem() {
        solver.clearSystem();
    }

    /**
     *
     * @param equation
     */
    public void addEquationToSystem(Equation equation) {
        solver.addEquationToSystem(equation);
    }

    private void getFinalResultFromSystem() throws Exception {
        Equation equation = new Equation(solver.collectResultsFromSystem());
        equation.roundDoubles();
        frame.addResultsToTable(equation.getVarsParametersAndConstant());
    }

    /**
     *
     * @throws Exception
     */
    public void solveByGausian() throws Exception {
        solver.solveByGausian();
        getFinalResultFromSystem();
    }

    /**
     *
     * @throws Exception
     */
    public void solveByGauseJordan() throws Exception {
        solver.solveByGauseJordan();
        getFinalResultFromSystem();
    }

    /**
     * Print The System
     */
    public void printSystem() {
        solver.printSystem();
    }

    //----------End Logic----------//
    //----------Start Getters----------//

    /**
     *
     * @param frame
     */
    public void setFrame(MainFrame frame) {
        this.frame = frame;
        frame.setPresenter(instance);
    }

}
