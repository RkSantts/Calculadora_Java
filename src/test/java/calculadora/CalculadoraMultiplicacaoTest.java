package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    public class CalculadoraMultiplicacaoTest {

        private Calculadora calc;

        @Before
        public void setUp() {
            calc = new Calculadora();
        }

        @After
        public void tearDown() {
            calc.clear();
            calc = null;
        }

        @Test
        public void testMultiplicacaoSimples() {
            double resultado = calc.multiply(6, 7);
            Assert.assertThat(resultado, CoreMatchers.is(42.0));
        }

        @Test
        public void testMultiplicacaoPorZeroResultaZero() {
            double resultado = calc.multiply(999, 0);
            Assert.assertThat(resultado, CoreMatchers.is(0.0));
        }

        @Test
        public void testMultiplicacaoPorUmNaoAlteraValor() {
            double resultado = calc.multiply(99, 1);
            Assert.assertThat(resultado, CoreMatchers.is(99.0));
        }

        @Test
        public void testMultiplicacaoDoisNegativos() {
            double resultado = calc.multiply(-4, -5);
            Assert.assertThat(resultado, CoreMatchers.is(20.0));
        }

        @Test
        public void testMultiplicacaoPositivoPorNegativo() {
            double resultado = calc.multiply(3, -4);
            Assert.assertThat(resultado, CoreMatchers.is(-12.0));
        }

        @Test
        public void testMultiplicacaoDecimais() {
            double resultado = calc.multiply(2.5, 4.0);
            Assert.assertThat(resultado, CoreMatchers.is(10.0));
        }

        @Test
        public void testMultiplicacaoFluxoCompleto() {
            calc.inputDigit("6");
            calc.inputOperator("×");
            calc.inputDigit("7");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("42"));
        }

        @Test
        public void testMultiplicacaoPorZeroNoDisplay() {
            calc.inputDigit("5");
            calc.inputDigit("0");
            calc.inputOperator("×");
            calc.inputDigit("0");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testMultiplicacaoOperadorRegistrado() {
            calc.inputDigit("5");
            calc.inputOperator("×");
            Assert.assertThat(calc.getOperator(), CoreMatchers.is("×"));
        }

        @Test
        public void testMultiplicacaoLimpaOperadorAposCalcular() {
            calc.inputDigit("3");
            calc.inputOperator("×");
            calc.inputDigit("3");
            calc.calculate();
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }
        @Test
        public void testMultiplicacaoComOutrasOperacoes() {
            calc.inputDigit("5");
            calc.inputOperator("×");
            calc.inputDigit("5");
            calc.inputOperator("+");
            calc.inputDigit("5");
            calc.inputOperator("÷");
            calc.inputDigit("3");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("10"));
        }
    }