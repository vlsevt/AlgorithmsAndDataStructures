package hwa1.src;

import java.util.Stack;

//source: https://stackoverflow.com/questions/7919836/how-do-i-copy-a-stack-in-java


public class DoubleStack {

    private final Stack<Double> stack;

    public static void main(String[] args) {
        try {
            System.out.println(DoubleStack.interpret("-3 -5 -7 ROT - SWAP DUP * +")); // Output: -13.0
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public DoubleStack() {
        stack = new Stack<>();
    }

    public Object clone() throws CloneNotSupportedException {
        DoubleStack newStack = new DoubleStack();
        newStack.stack.addAll(this.stack);
        return newStack;
    }

    public boolean stEmpty() {
        return stack.isEmpty();
    }

    public void push(double a) {
        stack.push(a);
    }

    public double pop() {
        if (stEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return stack.pop();
    }

    public void op(String s) {
        switch (s) {
            case "+" -> {
                if (stack.size() < 2) {
                    throw new RuntimeException("Not enough operands for the operation: " + s);
                }
                double b = pop();
                double a = pop();
                double result;
                push(a + b);
            }
            case "-" -> {
                if (stack.size() < 2) {
                    throw new RuntimeException("Not enough operands for the operation: " + s);
                }
                double b = pop();
                double a = pop();
                double result;
                push(a - b);
            }
            case "*" -> {
                if (stack.size() < 2) {
                    throw new RuntimeException("Not enough operands for the operation: " + s);
                }
                double b = pop();
                double a = pop();
                double result;
                push(a * b);
            }
            case "/" -> {
                if (stack.size() < 2) {
                    throw new RuntimeException("Not enough operands for the operation: " + s);
                }
                double b = pop();
                double a = pop();
                double result;
                if (b == 0) {
                    throw new RuntimeException("Division by zero: " + s);
                }
                push(a / b);
            }
            case "ROT" -> { // ROT operation
                if (stack.size() < 3) {
                    throw new RuntimeException("Not enough elements in the stack for the operation: " + s);
                }
                double c = pop();
                double b = pop();
                double a = pop();
                double result;
                push(b);
                push(c);
                push(a);
                return; // Return after performing ROT
            }
            case "SWAP" -> { // SWAP operation
                if (stack.size() < 2) {
                    throw new RuntimeException("Not enough elements in the stack for the operation: " + s);
                }
                double b = pop();
                double a = pop();
                push(b);
                push(a);
            }
            case "DUP" -> {
                if (stack.isEmpty()) {
                    throw new RuntimeException("Not enough elements in the stack for the operation: " + s);
                }
                double a = pop();
                push(a);
                push(a);
            }
            default -> throw new RuntimeException("Invalid operation: " + s);
        }
    }



    public double tos() {
        if (stEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack.peek();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleStack other = (DoubleStack) o;
        return this.stack.equals(other.stack);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Double item : stack) {
            result.append(item).append(" ");
        }
        return result.toString().trim();
    }

    public static double interpret(String pol) {
        if (pol == null || pol.trim().isEmpty()){
            throw new RuntimeException("Expression is missing - its value is null, an empty string, or a string composed of whitespace characters: " + pol);
        }
        String[] tokens = pol.trim().split("\\s+");
        DoubleStack stack = new DoubleStack();

        for (String token : tokens) {
            try {
                double number = Double.parseDouble(token);
                stack.push(number);
            } catch (NumberFormatException e) {
                if ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)) {
                    stack.op(token);
                } else if ("ROT".equals(token) || "SWAP".equals(token) || "DUP".equals(token)) {
                    stack.op(token);  // Handle the new operations
                } else {
                    throw new RuntimeException("An incorrect operator is present in the expression: " + pol);
                }
            }
        }

        if (stack.stack.size() > 1) {
            throw new RuntimeException("There are too many numbers in the expression: " + pol);
        }

        if (stack.stack.size() < 1) {
            throw new RuntimeException("There are not enough numbers in the expression: " + pol);
        }

        return stack.pop();
    }
}