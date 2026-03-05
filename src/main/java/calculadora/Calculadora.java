package calculadora;

/**
 * Classe de lógica da calculadora.
 */
public class Calculadora {

    private double firstOperand = 0;
    private double secondOperand = 0;
    private String operator = "";
    private boolean newEntry = true;
    private String currentDisplay = "0";
    private boolean hasResult = false;

    // ─── Operações Básicas ───────────────────────────────────────────

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * @throws ArithmeticException se b == 0
     */
    public double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("Divisão por zero");
        return a / b;
    }

    public double percentage(double a) {
        return a / 100.0;
    }

    public double negate(double a) {
        return -a;
    }

    // ─── Estado da Calculadora ───────────────────────────────────────

    /** Processa um dígito ou ponto decimal pressionado. */
    public void inputDigit(String digit) {
        if (newEntry || hasResult) {
            currentDisplay = digit.equals(".") ? "0." : digit;
            newEntry = false;
            hasResult = false;
        } else {
            if (digit.equals(".") && currentDisplay.contains(".")) return;
            if (currentDisplay.equals("0") && !digit.equals(".")) {
                currentDisplay = digit;
            } else {
                if (currentDisplay.length() < 15) {
                    currentDisplay += digit;
                }
            }
        }
    }

    /** Define o operador e armazena o primeiro operando. */
    public void inputOperator(String op) {
        if (!operator.isEmpty() && !newEntry) {
            calculate();
        }
        firstOperand = Double.parseDouble(currentDisplay);
        operator = op;
        newEntry = true;
        hasResult = false;
    }

    /** Executa o cálculo com o operador armazenado. */
    public void calculate() {
        if (operator.isEmpty()) return;
        secondOperand = Double.parseDouble(currentDisplay);
        double result;
        try {
            result = switch (operator) {
                case "+" -> add(firstOperand, secondOperand);
                case "-" -> subtract(firstOperand, secondOperand);
                case "×" -> multiply(firstOperand, secondOperand);
                case "÷" -> divide(firstOperand, secondOperand);
                default   -> firstOperand;
            };
        } catch (ArithmeticException e) {
            currentDisplay = "Erro: Div/0";
            operator = "";
            newEntry = true;
            hasResult = false;
            return;
        }
        currentDisplay = formatResult(result);
        operator = "";
        newEntry = true;
        hasResult = true;
    }

    /** Limpa tudo. */
    public void clear() {
        firstOperand = 0;
        secondOperand = 0;
        operator = "";
        currentDisplay = "0";
        newEntry = true;
        hasResult = false;
    }

    /** Apaga o último caractere. */
    public void backspace() {
        if (newEntry || hasResult || currentDisplay.equals("0") ||
                currentDisplay.startsWith("Erro")) {
            currentDisplay = "0";
            newEntry = false;
            hasResult = false;
            return;
        }
        currentDisplay = currentDisplay.length() > 1
                ? currentDisplay.substring(0, currentDisplay.length() - 1)
                : "0";
    }

    /** Inverte sinal. */
    public void toggleSign() {
        double val = Double.parseDouble(currentDisplay);
        currentDisplay = formatResult(negate(val));
    }

    /** Converte para porcentagem. */
    public void applyPercentage() {
        double val = Double.parseDouble(currentDisplay);
        currentDisplay = formatResult(percentage(val));
    }

    // ─── Getters ─────────────────────────────────────────────────────

    public String getCurrentDisplay() { return currentDisplay; }
    public String getOperator()       { return operator; }
    public double getFirstOperand()   { return firstOperand; }
    public boolean isNewEntry()       { return newEntry; }

    // ─── Helpers ─────────────────────────────────────────────────────

    private String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        // limita casas decimais
        String s = String.format("%.10f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
        return s;
    }
}