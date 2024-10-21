import java.util.LinkedList;

public class InfixCalculator {

    public int evaluateInfix(String expression) {
        LinkedList<Integer> numbers = new LinkedList<>();
        LinkedList<Character> operators = new LinkedList<>();
        StringBuilder numberBuilder = new StringBuilder();

        for (char token : expression.toCharArray()) {
            if (token == ' ') continue;

            if (Character.isDigit(token)) {
                numberBuilder.append(token);
            } else {
                if (numberBuilder.length() > 0) {
                    numbers.add(Integer.parseInt(numberBuilder.toString()));
                    numberBuilder.setLength(0);
                }

                if (token == '(') {
                    operators.add(token);
                } else if (token == ')') {
                    while (!operators.isEmpty() && operators.peekLast() != '(') {
                        compute(numbers, operators);
                    }
                    if (operators.isEmpty()) {
                        throw new IllegalArgumentException("Error: Invalid infix expression");
                    }
                    operators.removeLast(); // Remove '('
                } else if (isOperator(token)) {
                    while (!operators.isEmpty() && hasPrecedence(token, operators.peekLast())) {
                        compute(numbers, operators);
                    }
                    operators.add(token);
                } else {
                    throw new IllegalArgumentException("Error: Invalid infix expression");
                }
            }
        }

        if (numberBuilder.length() > 0) {
            numbers.add(Integer.parseInt(numberBuilder.toString()));
        }

        while (!operators.isEmpty()) {
            compute(numbers, operators);
        }

        return numbers.removeLast();
    }

    private void compute(LinkedList<Integer> numbers, LinkedList<Character> operators) {
        if (numbers.size() < 2 || operators.isEmpty()) {
            throw new IllegalArgumentException("Error: Invalid operation");
        }
        int b = numbers.removeLast();
        int a = numbers.removeLast();
        char operator = operators.removeLast();
        numbers.add(applyOperation(operator, a, b));
    }

    private boolean isOperator(char c) {
        return "+-*/%".indexOf(c) != -1;
    }

    private boolean hasPrecedence(char currentOp, char stackOp) {
        if (stackOp == '(' || stackOp == ')') return false;
        return (currentOp == '*' || currentOp == '/') ? (stackOp == '+' || stackOp == '-') : true;
    }

    private int applyOperation(char op, int a, int b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Error: Division by zero");
                return a / b;
            case '%':
                if (b == 0) throw new ArithmeticException("Error: Modulus by zero");
                return a % b;
            default:
                throw new IllegalArgumentException("Error: Invalid operator");
        }
    }

    public static void main(String[] args) {
        InfixCalculator calculator = new InfixCalculator();

        String expression1 = "3 + 5 * 2"; // 13
        String expression2 = "(4 + 3) * 2"; // 14
        String expression3 = "10 / 2 - 1"; // 4
        String expression4 = "5 3 +"; // Invalid infix notation

        try {
            System.out.println("Result 1: " + calculator.evaluateInfix(expression1));
            System.out.println("Result 2: " + calculator.evaluateInfix(expression2));
            System.out.println("Result 3: " + calculator.evaluateInfix(expression3));
            System.out.println("Result 4: " + calculator.evaluateInfix(expression4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}