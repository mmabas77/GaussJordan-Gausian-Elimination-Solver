/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GaussJordanElimination.pojo;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Mahmoud Abas
 */
public class Equation {

    private final double[] varsParametersAndConstant;

    /**
     *
     * @param varsParametersAndConstant
     */
    public Equation(double[] varsParametersAndConstant) {
        this.varsParametersAndConstant = varsParametersAndConstant;
    }

    /**
     *
     * @param index
     * @return
     */
    public double[] makeIndexOne(int index) {
        double paramToDev = varsParametersAndConstant[index];
        return mulWithNumber(1 / paramToDev);
    }

    /**
     *
     * @param number
     * @return
     */
    public double[] mulWithNumber(double number) {
        for (int i = 0; i < varsParametersAndConstant.length; i++) {
            varsParametersAndConstant[i] *= number;
        }
        return varsParametersAndConstant;
    }

    /**
     *
     * @param number
     * @return
     */
    public Equation mulWithNumberGetEquationCopy(double number) {
        double[] copy = new double[varsParametersAndConstant.length];
        System.arraycopy(
                varsParametersAndConstant,
                0,
                copy,
                0,
                varsParametersAndConstant.length
        );

        for (int i = 0; i < copy.length; i++) {
            copy[i] *= number;
        }

        return new Equation(copy);
    }

    /**
     *
     * @param equation
     * @return
     */
    public Equation addEquation(Equation equation) {
        for (int i = 0; i < equation.getVarsParametersAndConstant().length; i++) {
            this.varsParametersAndConstant[i] += equation.getVarsParametersAndConstant()[i];
        }
        return this;
    }

    /**
     *
     * @return
     */
    public int getFirstNonZeroIndex() {
        for (int i = 0; i < varsParametersAndConstant.length; i++) {
            if (!checkZeroInIndex(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param index
     * @return
     */
    public boolean checkZeroInIndex(int index) {
        return varsParametersAndConstant[index] == 0;
    }

    /**
     *
     * @return
     */
    public double[] getVarsParametersAndConstant() {
        return varsParametersAndConstant;
    }

    /**
     *
     * @param index
     * @return
     */
    public double getColValInIndex(int index) {
        return varsParametersAndConstant[index];
    }

    /**
     *
     * @return
     */
    public boolean isLeadingOne() {
        int firstNonZeroIndex = getFirstNonZeroIndex();
        return firstNonZeroIndex != -1
                && varsParametersAndConstant[firstNonZeroIndex] == 1;
    }

    /**
     *
     * @return
     */
    public boolean isZeroRow() {
        for (double d : varsParametersAndConstant) {
            if (d != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inverse The Equation (Result Of Equation Not Inverted)
     */
    public void inverse() {
        List<Double> list = Arrays.stream(varsParametersAndConstant).boxed().collect(Collectors.toList());
        Collections.reverse(list);
        for (int i = 0; i < list.size() - 1; i++) {
            varsParametersAndConstant[i] = (double) list.get(i + 1);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return Arrays.toString(varsParametersAndConstant);
    }

    DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Round Doubles
     */
    public void roundDoubles() {
        for (int i = 0; i < varsParametersAndConstant.length; i++) {
            varsParametersAndConstant[i] = Double.valueOf(df.format(varsParametersAndConstant[i]));
        }
    }

}
