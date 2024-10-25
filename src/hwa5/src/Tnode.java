package hwa5.src;

import java.util.*;

/** Tree with two pointers.
 * @since 1.8
 */
public class Tnode {

    private String name;
    private Tnode firstChild;
    private Tnode nextSibling;


    public Tnode(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setFirstChild(Tnode firstChild){
        this.firstChild = firstChild;
    }

    public void setNextSibling(Tnode nextSibling){
        this.nextSibling = nextSibling;
    }

    public Tnode getFirstChild(){
        return firstChild;
    }

    public Tnode getNextSibling(){
        return nextSibling;
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        if(firstChild != null){
            b.append(name);
            b.append("(");
            b.append(firstChild);
            if(nextSibling != null){
                b.append(",");
                b.append(nextSibling);
            }
            b.append(")");
        }else{
            b.append(name);
        }
        return b.toString();
    }

    public static Tnode buildFromRPN(String pol) {
        if (pol.length() == 0) {
            throw new RuntimeException("Empty string " + pol);
        }
        Tnode root = null;
        Stack<Tnode> stack = new Stack<>();
        String [] itemsForLen = pol.replaceAll("SWAP", "").replaceAll("ROT", "").split("\\s+");
        String[] items = pol.split("\\s+");
        if (itemsForLen.length % 2 == 0){
            throw new RuntimeException("String" + pol + "contains too many numbers");
        }
        for (String item : items) {
            if (!isNumeric(item) && !isOperator(item) && !isSpecialOperation(item)) {
                throw new RuntimeException("String " + pol + " contains illegal symbols");
            }
            if (isSpecialOperation(item)) {
                switch (item) {
                    case "DUP":
                        if (stack.isEmpty()) {
                            throw new IllegalStateException("No subtree to duplicate");
                        }
                        Tnode toDuplicate = stack.peek();
                        stack.push(deepCopyTree(toDuplicate));
                        break;
                    case "SWAP":
                        if (stack.size() < 2) {
                            throw new IllegalStateException("Not enough subtrees to swap");
                        }
                        Tnode first = stack.pop();
                        Tnode second = stack.pop();
                        stack.push(first);
                        stack.push(second);
                        break;
                    case "ROT":
                        if (stack.size() < 3) {
                            throw new IllegalStateException("Not enough subtrees to rotate");
                        }
                        Tnode firstRot = stack.pop();
                        Tnode secondRot = stack.pop();
                        Tnode thirdRot = stack.pop();
                        stack.push(secondRot);
                        stack.push(firstRot);
                        stack.push(thirdRot);
                        break;
                }
            } else if (isOperator(item)) {
                Tnode operator = new Tnode(item);
                operator.setNextSibling(stack.pop()); // Right child
                operator.setFirstChild(stack.pop()); // Left child
                stack.push(operator);
            } else {
                stack.push(new Tnode(item));
            }
        }
        if (!stack.isEmpty()) {
            root = stack.pop();
        } else {
            throw new IllegalArgumentException("Invalid " + pol + " expression");
        }
        return root;
    }

    private static boolean isSpecialOperation(String operation) {
        return operation.equals("DUP") || operation.equals("SWAP") || operation.equals("ROT");
    }

    private static Tnode deepCopyTree(Tnode node) {
        if (node == null) {
            return null;
        }
        Tnode newNode = new Tnode(node.getName());
        newNode.setFirstChild(deepCopyTree(node.getFirstChild()));
        newNode.setNextSibling(deepCopyTree(node.getNextSibling()));
        return newNode;
    }

    private static boolean isOperator(String operator){
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/");
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main (String[] param) {
        String rpn = "2 5 SWAP -";
        Tnode str = new Tnode(rpn);
        System.out.println ("RPN: " + rpn);
        Tnode res = buildFromRPN (rpn);
        System.out.println ("Tree: " + res);
        System.out.println(res.toString());
    }
}