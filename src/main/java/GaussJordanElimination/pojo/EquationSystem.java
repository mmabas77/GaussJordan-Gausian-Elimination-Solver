/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GaussJordanElimination.pojo;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Mahmoud Abas
 */
public class EquationSystem {

    private ArrayList<Equation> equations;

    /**
     * Generate New System
     */
    public EquationSystem() {
        this.equations = new ArrayList<>();
    }

    /**
     * Clear All Equations
     */
    public void clearAllEquations() {
        equations.clear();
    }

    /**
     *
     * @throws Exception
     */
    public void solveByGauseJordan() throws Exception {
        //Get Row Echlon Form
        solveByGausian();
        //Inverse Equations & Vars(only)
        Collections.reverse(equations);
        equations.forEach((equation) -> {
            equation.inverse();
        });
        //Get Row Echlon Form For The New System
        //Will Get Reduced Row Echlon Form For The Old System But Inversed
        solveByGausian();
        //Inverse Equations & Vars(only) Again To Return To Starting Order
        Collections.reverse(equations);
        equations.forEach((equation) -> {
            equation.inverse();
        });
    }

    /**
     *
     * @throws Exception
     */
    public void solveByGausian() throws Exception {
        ArrayList<Equation> result = new ArrayList<>();
        ArrayList<Equation> allEquationToCheck = new ArrayList<>(equations);
        while (!checkReducedRowEshlonForm(equations) || equations.size() > 0) {
            System.out.println(this);
            int leadingIndex;
            leadingIndex = makeFirstNonZeroColInZeroIndexAndEqualOne();
            makeAllItemsInColEqualZeroExceptFirst(leadingIndex);
            result.add(equations.get(0));
            equations.remove(0);
            copyNewValsToCheckedList(result, equations, allEquationToCheck);
        }

        equations.forEach((equation) -> {
            result.add(equation);
        });
        equations = result;
    }

    /**
     *
     * @param equationsToCheck
     * @return
     */
    public boolean checkReducedRowEshlonForm(ArrayList<Equation> equationsToCheck) {
        int lastLeadingRowIndex = Integer.MIN_VALUE;
        int lastLeadingColIndex = Integer.MIN_VALUE;

        for (int i = 0; i < equationsToCheck.size(); i++) {
            Equation equation = equationsToCheck.get(i);
            boolean isLeadingOne = equation.isLeadingOne();

            //----------Todo:Make Refactor----------//
            //Check Leading = 1 & if 2 leadings after each other the secound must 
            //Be in the right of the first
            if (isLeadingOne && i == lastLeadingRowIndex + 1) {
                int leadingIndex = equation.getFirstNonZeroIndex();
                if (!(leadingIndex > lastLeadingColIndex)) {
                    return false;
                } else {
                    lastLeadingRowIndex = i;
                    lastLeadingColIndex = leadingIndex;
                }
            } else if (isLeadingOne) {
                lastLeadingRowIndex = i;
                lastLeadingColIndex = equation.getFirstNonZeroIndex();
            }

            //Check if not leading but not zero
            if (!isLeadingOne && !equation.isZeroRow()) {
                return false;
            }

            //Check all zero rows in the end of system
            if (!checkAllZeroEquationInEnd(equationsToCheck)) {
                return false;
            }

            //Check all Items With Leading = 0
            if (!checkAllItemsInLeadingColEquualZero(equationsToCheck, lastLeadingRowIndex, lastLeadingColIndex)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param equationsToCheck
     * @param leadingRowToStart
     * @param leadingColToCheck
     * @return
     */
    public boolean checkAllItemsInLeadingColEquualZero(ArrayList<Equation> equationsToCheck, int leadingRowToStart, int leadingColToCheck) {
        if (!(leadingRowToStart >= 0 && leadingColToCheck >= 0)) {
            return false;
        }
        for (int i = leadingRowToStart + 1; i < equationsToCheck.size(); i++) {
            Equation equation = equationsToCheck.get(i);
            if (equation.getColValInIndex(leadingColToCheck) != 0) {
                return false;
            }
        }
        return true;

    }

    /**
     *
     * @param equationsToCheck
     * @return
     */
    public boolean checkAllZeroEquationInEnd(ArrayList<Equation> equationsToCheck) {
        int lastZeroEquation = Integer.MAX_VALUE;
        for (int i = 0; i < equationsToCheck.size(); i++) {
            Equation equation = equationsToCheck.get(i);
            if (equation.isZeroRow()) {
                lastZeroEquation = i;
            } else {
                if (i > lastZeroEquation) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param indexOfColToChange
     */
    public void makeAllItemsInColEqualZeroExceptFirst(int indexOfColToChange) {
        Equation currentRow = equations.get(0);
        for (int i = 1; i < equations.size(); i++) {
            Equation equation = equations.get(i);
            double numToMul = -equation.getColValInIndex(indexOfColToChange);
            equation.addEquation(currentRow.mulWithNumberGetEquationCopy(numToMul));
        }
    }

    /**
     *
     * @return
     */
    public int makeFirstNonZeroColInZeroIndexAndEqualOne() {
        makeFirstNonZeroColAtZeroRowIndex();
        int firstNonZeroIndex = equations.get(0).getFirstNonZeroIndex();
        equations.get(0).makeIndexOne(firstNonZeroIndex);
        return firstNonZeroIndex;
    }

    /**
     * Make First NonZero Col At Zero Row
     */
    public void makeFirstNonZeroColAtZeroRowIndex() {
        int firstNonZeroColRowIndex = getFirstNonZeroColRowIndex();
        if (firstNonZeroColRowIndex == -1) {
            throw new NumberFormatException("First -Non Zero- Col Can't Be Zero");
        }
        if (firstNonZeroColRowIndex != 0) {
            swapEquations(firstNonZeroColRowIndex, 0);
        }
    }

    /**
     *
     * @return
     */
    public int getFirstNonZeroColRowIndex() {
        int firstNonZeroCol = Integer.MAX_VALUE;
        int row_index = -1;
        for (int i = 0; i < equations.size(); i++) {
            Equation equation = equations.get(i);
            int tempeFirstNonZeroCol = equation.getFirstNonZeroIndex();
            if (tempeFirstNonZeroCol != -1 && tempeFirstNonZeroCol < firstNonZeroCol) {
                firstNonZeroCol = tempeFirstNonZeroCol;
                row_index = i;
//                return firstNonZeroCol;
            }
        }
        return row_index;
//        return firstNonZeroCol != Integer.MAX_VALUE ? firstNonZeroCol : -1;
    }

    /**
     *
     * @param from
     * @param to
     */
    public void swapEquations(int from, int to) {
        Equation tempToEquation = equations.get(to);
        equations.set(to, equations.get(from));
        equations.set(from, tempToEquation);
    }

    /**
     *
     * @param equation
     * @return
     */
    public EquationSystem addEquationToSystem(Equation equation) {
        this.equations.add(equation);
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        equations.forEach((equation) -> {
            builder.append(equation).append("\n");
        });
        return builder.toString();
    }

    private void copyNewValsToCheckedList(ArrayList<Equation> result, ArrayList<Equation> equations, ArrayList<Equation> allEquationToCheck) {
        allEquationToCheck.clear();
        allEquationToCheck.addAll(result);
        allEquationToCheck.addAll(equations);
    }

    /**
     *
     * @param index
     * @return
     */
    public Equation getEquatinInIndex(int index) {
        return equations.get(index);
    }

    /**
     *
     * @param equations
     */
    public void setEquations(ArrayList<Equation> equations) {
        this.equations = equations;
    }

    /**
     *
     * @return
     */
    public int getEquationCount() {
        return equations.size();
    }

}
