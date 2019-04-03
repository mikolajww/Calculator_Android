package pl.mww.calculator;

import org.mariuszgromada.math.mxparser.*;

import java.util.Arrays;
import java.util.List;

public class Calculator {

    public static List<Integer> basicButtonIds = Arrays.asList(R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
            R.id.button_add, R.id.button_multiply, R.id.button_divide,
            R.id.button_subtract, R.id.button_dot, R.id.button_open_paren, R.id.button_close_paren);
    public static List<Integer> advancedButtonIds = Arrays.asList(R.id.button_sin, R.id.button_cos,
            R.id.button_tan, R.id.button_sqrt, R.id.button_log, R.id.button_ln);

    private Calculator() {
        throw new RuntimeException("This class is not meant to be instantiated!");
    }

    public static String evaluateExpression(String expression) {
        Expression ex = new Expression(expression);
        if(!ex.checkSyntax()) {
            return "Syntax Error";
        }
        if(Double.toString(ex.calculate()).equalsIgnoreCase("NAN")) {
            return "NaN";
        }
        return Double.toString(ex.calculate());
    }
}
