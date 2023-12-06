package pl.com.wikann.springboot.utils;

import java.util.Stack;

public class ExpressionTreeBuilder {

    public float calculate(String expression) {
        if (expression == null || expression.length() == 0) return 0;
        Expression expressionObj = new Expression(expression);
        TreeNode root = buildExpressionTree(expressionObj);
        float result = calculateTree(root);
        return result;
    }

    public static TreeNode buildExpressionTree(Expression expressionObj) {
        String[] tokens = tokenizeExpression(expressionObj.getExpression());
        return buildExpressionTreeHelper(tokens, 0, tokens.length - 1);
    }

    private static String[] tokenizeExpression(String expression) {
        expression = expression.replaceAll("([()])", " $1 ");
        // Add spaces around operators to treat them as separate tokens
        expression = expression.replaceAll("([+\\-*/])", " $1 ");
        return expression.split("\\s+");
    }


    private static TreeNode buildExpressionTreeHelper(String[] tokens, int start, int end) {
        Stack<TreeNode> stack = new Stack<>();

        for (int i = start; i <= end; i++) {
            String token = tokens[i];

            if (token.equals("(")) {
                int closingIndex = findClosingParenthesis(tokens, i);
                TreeNode subtree = buildExpressionTreeHelper(tokens, i + 1, closingIndex - 1);
                stack.push(subtree);
                i = closingIndex;
            } else if (token.equals(")")) {
                continue;
            } else {
                stack.push(createNode(token));
            }

            while (stack.size() >= 3 && "+-*/".contains(stack.get(stack.size() - 2).value.toString())) {
                TreeNode right = stack.pop();
                TreeNode operator = stack.pop();
                TreeNode left = stack.pop();
                operator.left = left;
                operator.right = right;
                stack.push(operator);
            }


        }

        return stack.isEmpty() ? null : stack.pop();
    }

    private static TreeNode createNode(String token) {
        if ("+-*/".contains(token)) {
            return new TreeNode(getOperatorInstance(token));
        } else {
            return new TreeNode(new Variable(Float.parseFloat(token)));
        }
    }

    private static BiOperator getOperatorInstance(String token) {
        switch (token) {
            case "+":
                return new AddOperator();
            case "-":
                return new SubtractOperator();
            case "*":
                return new MultiplyOperator();
            case "/":
                return new DivideOperator();
            default:
                throw new IllegalArgumentException("Unknown operator: " + token);
        }
    }

    private static int findClosingParenthesis(String[] tokens, int start) {
        int count = 1;
        for (int i = start + 1; i < tokens.length; i++) {
            if (tokens[i].equals("(")) {
                count++;
            } else if (tokens[i].equals(")")) {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("No matching closing parenthesis");
    }

    public static float calculateTree(TreeNode root) {
        if (root.value instanceof Variable) {
            return ((Variable) root.value).getValue();
        } else if (root.value instanceof BiOperator) {
            BiOperator operator = (BiOperator) root.value;
            float leftValue = calculateTree(root.left);
            float rightValue = calculateTree(root.right);
            return operator.apply(leftValue, rightValue);
        } else {
            throw new IllegalArgumentException("Unknown node type: " + root.value);
        }
    }
}