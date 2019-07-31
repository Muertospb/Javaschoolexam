package com.tsystems.javaschool.tasks.calculator;

import java.util.*;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    static String breakPoints = "() */+-<>";
    static String operationSings = "*/+-";


    public String evaluate(String statement) {
        // TODO: Implement the logic here
        List<String> parsedList = new ArrayList<String>();
        Deque<String> stack = new ArrayDeque<String>();
        StringTokenizer tokenizer;
        try {
            tokenizer = new StringTokenizer(statement, breakPoints, true);
        }
        catch (NullPointerException e){
            return null;
        }
        String currentToken;
        String prevToken = "";

        while (tokenizer.hasMoreTokens()) {
            currentToken = tokenizer.nextToken();
            if (currentToken.equals(" ")) continue;
            if (!tokenizer.hasMoreTokens() && isOperator(currentToken)) return null;
            if (isOperator(currentToken) && isOperator(prevToken)) return null;
            if (currentToken.equals(">")){
                String searchFor = "";
                while (!searchFor.equals("<")){
                    try {
                        searchFor = stack.pop();
                    }
                    catch (NoSuchElementException e){
                        return null;
                    }
                }
            }
            if (currentToken.equals(")")){
                String searchFor = "";
                while (!searchFor.equals("(")){
                    try {
                        searchFor = stack.pop();
                        if (!searchFor.equals("(")){
                            parsedList.add(searchFor);
                        }
                    }
                    catch (NoSuchElementException e){
                        return null;
                    }
                }
                continue;
            }

            if (isOperator(currentToken)) {
                if (stack.isEmpty()){
                    stack.push(currentToken);
                }
                else {
                    if (isOperator(stack.peek())){
                        if (priority(currentToken)> priority(stack.peek())){
                            stack.push(currentToken);
                        }
                        else{
                            parsedList.add(stack.pop());
                            stack.push(currentToken);
                        }
                    }
                    else {
                        stack.push(currentToken);
                    }
                }


            }
            else {
                if (currentToken.equals("(") || currentToken.equals("<")) {
                    stack.push(currentToken);
                }
                else {
                    parsedList.add(currentToken);
                }

            }




//            else if (isBreakPoint(currentToken)) {
//                if (currentToken.equals("(")) stack.push(currentToken);
//                else if (currentToken.equals(")")) {
//                    while (!stack.peek().equals("(")) {
//                        parsedList.add(stack.pop());
//                        if (stack.isEmpty()) {
//                            return null;
//                        }
//                    }
//                    stack.pop();
//                    if (!stack.isEmpty() && isOperator(stack.peek())) {
//                        parsedList.add(stack.pop());
//                    }
//                }
//                if (currentToken.equals("-") && (prevToken.equals("") || (isBreakPoint(prevToken)  && !prevToken.equals(")")))) {
//                    currentToken = "u-";
//                    while (!stack.isEmpty() && (priority(currentToken) <= priority(stack.peek()))) {
//                        parsedList.add(stack.pop());
//                    }
//                    stack.push(currentToken);
//                }
//            }

            prevToken = currentToken;
        }
        if (stack.size() == 0) return null;
        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) parsedList.add(stack.pop());
            else {
                return null;
            }
        }

        Deque<Double> evaluateStack = new ArrayDeque<Double>();
        for (String x : parsedList) {
            /*if (x.equals("sqrt")) evaluateStack.push(Math.sqrt(evaluateStack.pop()));
            else if (x.equals("cube")) {
                Double tmp = evaluateStack.pop();
                evaluateStack.push(tmp * tmp * tmp);
            } else if (x.equals("pow10")) evaluateStack.push(Math.pow(10, evaluateStack.pop()));
            else*/ if (x.equals("+")) evaluateStack.push(evaluateStack.pop() + evaluateStack.pop());
            else if (x.equals("-")) {
                Double b = evaluateStack.pop(), a = evaluateStack.pop();
                evaluateStack.push(a - b);
            } else if (x.equals("*")) evaluateStack.push(evaluateStack.pop() * evaluateStack.pop());
            else if (x.equals("/")) {
                Double b = evaluateStack.pop(), a = evaluateStack.pop();
                if (b == 0) return null;
                evaluateStack.push(a / b);
            } else if (x.equals("u-")) evaluateStack.push(-evaluateStack.pop());
            else
                try {
                    evaluateStack.push(Double.valueOf(x));
                }
                catch (NumberFormatException e){
                    return null;
                }
            }
        double roundOff = (double) Math.round(evaluateStack.pop() * 10000) / 10000;
        int checkForInteger = (int) roundOff;
        if (roundOff - checkForInteger == 0) return "" + checkForInteger;
        String result = "" + roundOff;
        return result;
    }

    //Checks if token is a BreakPoint
    static boolean isBreakPoint(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < breakPoints.length(); i++) {
            if (token.charAt(0) == breakPoints.charAt(i)) return true;
        }
        return false;
    }

    //Checks if token is an Operator
    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        if (token.equals("")) return false;
        for (int i = 0; i < operationSings.length(); i++) {
            if (token.charAt(0) == operationSings.charAt(i)) return true;
        }
        return false;
    }

    //Set priority level for operators
    private static int priority(String token) {
        if (token.equals("+") || token.equals("-")) return 1;
        if (token.equals("*")) return 2;
        if (token.equals("/")) return 3;
       return 0;
    }
}
