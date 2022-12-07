package edu.sjsu.android.mortgagecalculator;

import static java.lang.Math.pow;
//util class that performs calculations
public class CalculatorUtil {
    public static double calculatePayable(double borrowed, double rate, int term){
        if(rate == 0){
            return borrowed / (term * 12);
        }
        else{
            return borrowed * rate / (1 - pow(1 + rate, term * -12));
        }
    }
}
