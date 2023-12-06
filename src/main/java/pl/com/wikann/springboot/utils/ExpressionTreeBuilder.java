package pl.com.wikann.springboot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionTreeBuilder {

    public float calculate(String expression) {
        Expression expressionObj = buildExpression(expression);
        float result = expressionObj.evaluate();
        return result;
    }

    // Metoda budująca drzewo wyrażenia na podstawie podanego ciągu znaków
    public static Expression buildExpression(String expression) {
        String[] tokens = tokenizeExpression(expression);
        TreeNode root = buildExpressionTreeHelper(tokens, 0, tokens.length - 1);
        return new Expression(root);
    }

    private static String[] tokenizeExpression(String expression) {
        // Lista przechowująca znalezione tokeny
        List<String> tokensList = new ArrayList<>();
        // Aktualny token, który jest budowany podczas iteracji po znakach wyrażenia
        StringBuilder currentToken = new StringBuilder();

        // Iteracja po znakach w wyrażeniu
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                currentToken.append(c);
            }
            // Jeśli znak to minus
            else if (c == '-') {
                // Sprawdź, czy minus jest na początku lub poprzedzony nawiasem otwierającym lub operatorem +, -, *, /
                if (i == 0 || expression.charAt(i - 1) == '(' || isOperator (String.valueOf(expression.charAt(i - 1)))) {
                    // Traktuj minus jako początek liczby ujemnej, dodaj go do aktualnego tokenu
                    currentToken.append(c);
                } else {
                    // W przeciwnym przypadku, traktuj minus jako operator odejmowania
                    if (currentToken.length() > 0) {
                        tokensList.add(currentToken.toString());
                        currentToken.setLength(0);
                    }
                    tokensList.add(String.valueOf(c));
                }
            }


            // Jeśli znak to operator lub nawias
            else if (isOperator(String.valueOf(c)) || c == '(' || c == ')') {
                // Jeśli aktualny token nie jest pusty, dodaj go do listy tokenów
                if (currentToken.length() > 0) {
                    tokensList.add(currentToken.toString());
                    // Zresetuj aktualny token
                    currentToken.setLength(0);
                }
                // Dodaj operator lub nawias jako oddzielny token
                tokensList.add(String.valueOf(c));
            }


            // Jeśli znak to cyfra lub kropka (część liczby)
        }

        // Dodaj ostatni niepusty token, jeśli istnieje
        if (currentToken.length() > 0) {
            tokensList.add(currentToken.toString());
        }

        // Zamień listę tokenów na tablicę i zwróć
        return tokensList.toArray(new String[0]);
    }


    // Metoda pomocnicza budująca drzewo wyrażenia rekurencyjnie
    private static TreeNode buildExpressionTreeHelper(String[] tokens, int start, int end) {
        // Stos węzłów drzewa
        Stack<TreeNode> stack = new Stack<>();
        // Stos operatorów
        Stack<BiOperator> operatorStack = new Stack<>();

        // Iteracja po tokenach w zakresie od 'start' do 'end'
        for (int i = start; i <= end; i++) {
            String token = tokens[i].trim();

            // Jeśli napotkano nawias otwierający
            if (token.equals("(")) {
                // Znajdź indeks odpowiadającego nawiasu zamykającego
                int closingIndex = findClosingParenthesis(tokens, i);
                // Rekurencyjnie zbuduj drzewo dla wyrażenia w nawiasie
                TreeNode subexpression = buildExpressionTreeHelper(tokens, i + 1, closingIndex - 1);
                // Dodaj poddrzewo do stosu
                stack.push(subexpression);
                // Przeskocz do indeksu po nawiasie zamykającym
                i = closingIndex;
            }
            // Jeśli napotkano liczbę
            else if (isNumeric(token)) {
                // Stwórz węzeł zmienną i dodaj go do stosu
                stack.push(new TreeNode(new Variable(Float.parseFloat(token))));
            }
            // Jeśli napotkano operator
            else if (isOperator(token)) {
                // Pobierz instancję operatora
                BiOperator operator = getOperatorInstance(token);
                // Dopóki na stosie operatorów są operatory o wyższym priorytecie,
                // zdejmij je i zbuduj drzewo z operatorem i dwoma ostatnimi operandami,
                // a następnie dodaj to poddrzewo na stos
                while (!operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), token)) {
                    TreeNode right = stack.pop();
                    TreeNode left = stack.pop();
                    BiOperator prevOperator = operatorStack.pop();
                    TreeNode subTree = new TreeNode(prevOperator);
                    subTree.left = left;
                    subTree.right = right;
                    stack.push(subTree);
                }
                // Dodaj bieżący operator na stos operatorów
                operatorStack.push(operator);
            }
        }

        // Zbuduj drzewo z pozostałych operatorów na stosie
        while (!operatorStack.isEmpty()) {
            TreeNode right = stack.pop();
            TreeNode left = stack.pop();
            BiOperator operator = operatorStack.pop();
            TreeNode subTree = new TreeNode(operator);
            subTree.left = left;
            subTree.right = right;
            stack.push(subTree);
        }

        // Zwróć korzeń drzewa, jeśli istnieje
        return stack.isEmpty() ? null : stack.pop();
    }

    // Metoda znajdująca indeks zamykającego nawiasu odpowiadającego otwierającemu nawiasowi
    private static int findClosingParenthesis(String[] tokens, int openingIndex) {
        // Poziom zagnieżdżenia nawiasów, rozpoczynamy od 1,
        // ponieważ napotkany nawias otwierający już został uwzględniony
        int nestedLevel = 1;

        // Iteracja po tokenach począwszy od indeksu po nawiasie otwierającym
        for (int i = openingIndex + 1; i < tokens.length; i++) {
            // Jeśli napotkano nawias otwierający, zwiększ poziom zagnieżdżenia
            if (tokens[i].equals("(")) {
                nestedLevel++;
            }
            // Jeśli napotkano nawias zamykający, zmniejsz poziom zagnieżdżenia
            else if (tokens[i].equals(")")) {
                nestedLevel--;
                // Jeśli poziom zagnieżdżenia osiągnął 0, to znaleźliśmy odpowiadający nawias zamykający
                if (nestedLevel == 0) {
                    return i;
                }
            }
        }
        // Jeśli nie znaleziono odpowiadającego nawiasu zamykającego, zgłoś wyjątek
        throw new IllegalArgumentException("No matching closing parenthesis");
    }


    // Metoda sprawdzająca, czy operator1 ma wyższy lub równy priorytet niż operator2
    private static boolean hasPrecedence(BiOperator op1, String op2) {
        return getPrecedence(op1) >= getPrecedence(getOperatorInstance(op2));
    }

    // Metoda zwracająca priorytet operatora
    private static int getPrecedence(BiOperator operator) {
        // Jeśli operator to dodawanie lub odejmowanie, zwróć priorytet 1
        if (operator instanceof AddOperator || operator instanceof SubtractOperator) {
            return 1;
        }
        // Jeśli operator to mnożenie lub dzielenie, zwróć priorytet 2
        else if (operator instanceof MultiplyOperator || operator instanceof DivideOperator) {
            return 2;
        }
        // Dla innych operatorów lub obiektów zwróć priorytet 0
        return 0;
    }


    // Metoda sprawdzająca, czy podany ciąg znaków jest liczbą
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    // Metoda sprawdzająca, czy podany ciąg znaków jest operatorem
    private static boolean isOperator(String str) {
        return str.matches("[+\\-*/]");
    }

    // Metoda zwracająca instancję operatora na podstawie podanego ciągu znaków
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

    // Metoda do drukowania drzewa wyrażenia
    public static void printExpressionTree(TreeNode root) {
        if (root != null) {
            if (root.value instanceof BiOperator) {
                System.out.print("(");
            }
            printExpressionTree(root.left);
            if (root.value instanceof BiOperator) {
                System.out.print(((BiOperator) root.value).toString() + " ");
            } else if (root.value instanceof Variable) {
                System.out.print(((Variable) root.value).toString() + " ");
            }
            printExpressionTree(root.right);
            if (root.value instanceof BiOperator) {
                System.out.print(")");
            }
        }
    }

    // Metoda do obliczania wyniku z drzewa wyrażenia
    public static float calculateTree(TreeNode root) {
        if (root.value instanceof Variable) {
            return ((Variable) root.value).value;
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
